package run.hxtia.workbd.service.usermanagement.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.httpclient.HttpClient;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.*;
import run.hxtia.workbd.mapper.StudentMapper;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAvatarReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentReqVo;
import run.hxtia.workbd.pojo.vo.common.response.WxAccessTokenVo;
import run.hxtia.workbd.pojo.vo.common.response.WxCodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.WxTokenVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentPageReqVo;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;
import run.hxtia.workbd.service.notificationwork.StudentHomeworkService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final Redises redises;
    private final StudentCourseService studentCourseService;
    private final StudentHomeworkService studentHomeworkService;
    private final HomeworkService homeworkService;

    /**
     * 根据 code验证码换取 session_key + openId
     * @param code：验证码
     * @return ：session_key + openId
     */
    @Override
    public String getToken(String code) throws Exception {

        String url = MiniApps.buildGetTokenUrl(code);
        String resStr = HttpClient.httpGetRequest(url);
        WxTokenVo wxTokenVo = WxTokenVo.parseFromJson(resStr);
        if (wxTokenVo == null){
            JsonVos.raise("得到的结果为 Null, URL = " + url);
        }

        // TODO：增加默认值，还是使用 errCode 判断
        if (!StringUtils.hasLength(wxTokenVo.getOpenid())) {
            JsonVos.raise("请求出错：errCode = " + wxTokenVo.getErrcode() + ", msg = " + wxTokenVo.getErrmsg());
        }

        // 来到这里说明得到了结果
        String token = Strings.getUUID();
        redises.set(Constants.WxMiniApp.WX_PREFIX, token, wxTokenVo, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        return  token;
    }

    /**
     * 验证 Token 是否有效
     * @param token token
     * @return 是否有效
     */
    public Boolean checkToken(String token) throws Exception {

        // 获取 Token 的信息
        WxTokenVo wxTokenVo = MiniApps.getWxTokenVo(token);
        if (wxTokenVo == null) {
            JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        // 先从缓存获取 Access Token
        WxAccessTokenVo wxAccessTokenVo = (WxAccessTokenVo) redises.get(Constants.WxMiniApp.WX_AT_PREFIX, token);

        if (wxAccessTokenVo == null) {
            // 说明缓存没有，发请求获取
            String atUrl = MiniApps.buildGetWXAccessTokenUrl();
            String atRespStr = HttpClient.httpGetRequest(atUrl);
            wxAccessTokenVo = WxAccessTokenVo.parseFromJson(atRespStr);
            if (!StringUtils.hasLength(wxAccessTokenVo.getAccessToken())) {
                JsonVos.raise(CodeMsg.GET_ACCESS_TOKEN_ERR);
            }

            // 存入缓存中
            redises.set(Constants.WxMiniApp.WX_AT_PREFIX, token, wxAccessTokenVo, wxAccessTokenVo.getExpiresIn(), TimeUnit.SECONDS);
        }

        // 构建 TCheck Token 的 URL
        String url = buildCheckSessionUrl(wxTokenVo, wxAccessTokenVo.getAccessToken());
        String resp = HttpClient.httpGetRequest(url);
        WxCodeMsg wxCodeMsg = WxCodeMsg.parseFromJson(resp);
        if (wxCodeMsg == null || wxCodeMsg.getErrcode() != 0) {
            JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        return  true;
    }

    /**
     * 获取用户信息
     * @param token：获取的 Token
     * @return ：用户信息
     */
    @Override
    public StudentInfoDto getStudentByToken(String token) {


        String openId = MiniApps.getOpenId(token);

        // 从redis中读取 user 信息，这里的 Key 是动态的。
        StudentInfoDto studentInfoDto = (StudentInfoDto) redises.get(Constants.WxMiniApp.WX_USER, openId + token);

        // 检查 redis 中有无用户
        if (studentInfoDto != null) return studentInfoDto;

        // 先去数据库查询
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getWechatId, openId);
        Student studentPo = baseMapper.selectOne(wrapper);
        studentInfoDto = new StudentInfoDto();

        // TODO：调整为并行处理
        if (studentPo != null) {
            studentInfoDto.setStudentVo(MapStructs.INSTANCE.po2vo(studentPo));
        } else {
            // 来到这里说明用户是第一次授权，需要注册
            studentInfoDto.setStudentVo(MapStructs.INSTANCE.po2vo(registerUser(openId)));
        }
        // 构建其他信息
        //  组织信息
        studentInfoDto.setOrganizationVo(baseMapper.getOrganizationDetailsByStudentId(openId));
        //  课程信息
        studentInfoDto.setCourseVos(studentCourseService.getStudentCoursesByStudentId(openId));

        // 将最新消息缓存到 redis
        redises.set(Constants.WxMiniApp.WX_USER, openId + token, studentInfoDto, Constants.Date.WX_STUDENT_EXPIRE_DATS, TimeUnit.DAYS);
        return studentInfoDto;
    }

    /**
     * 注册用户【存开发者服务器】
     * @param wechatId：
     * @return ：是否成功。
     */
    private Student registerUser(String wechatId) {
        if (wechatId == null) {
            return JsonVos.raise(CodeMsg.AUTHORIZED_ERROR);
        }

        Student po = new Student();
        po.setWechatId(wechatId);
        po.setNickname(Strings.getUUID(10));
        // TODO：改成可配置项，目前硬编码了
        po.setAvatarUrl("https://hx.404fwf.cn/notifyBoard/img/default-avatar.png");
        if (baseMapper.insert(po) <= 0) {
            return JsonVos.raise(CodeMsg.AUTHORIZED_ERROR);
        }
        return po;
    }

    /**
     * 完善用户信息
     * @param reqVo：用户信息
     * @return ：是否成功
     */
    @Override
    public boolean update(StudentReqVo reqVo, String token) {
        Student po = MapStructs.INSTANCE.reqVo2po(reqVo);
        if (baseMapper.updateById(po) <= 0) {
            return false;
        }

        // TODO：健壮性考虑，如果删除失败了怎么处理
        redises.del(Constants.WxMiniApp.WX_USER+reqVo.getWechatId()+token);
        return true;
    }

    /**
     * 用户上传头像
     * @param reqVo：头像数据
     * @return ：是否成功
     */
    @Override
    public boolean update(StudentAvatarReqVo reqVo, String token) throws Exception {
        Student po = new Student();
        po.setWechatId(reqVo.getWechatId());
        boolean res = Uploads.uploadOneWithPo(po,
            new UploadReqParam(reqVo.getAvatarUrl(),
                reqVo.getAvatarFile()),
            baseMapper, Student::setAvatarUrl);

        if (res) {
            // TODO：健壮性考虑，如果删除失败了怎么处理
            redises.del(Constants.WxMiniApp.WX_USER, reqVo.getWechatId() + token);
        }

        return res;
    }

    @Override
    public List<StudentVo> getStudentByClassIds(Set<Integer> classIds) {
        if (classIds == null || classIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 使用 MpLambdaQueryWrapper 查询
        MpLambdaQueryWrapper<Student> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(Student::getClassId, classIds);

        // 查询学生信息
        List<Student> students = baseMapper.selectList(wrapper);
        if (students == null || students.isEmpty()) {
            return Collections.emptyList();
        }

        // 将 Student 对象转换为 StudentVo 对象
        return students.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }

    @Override
    public List<StudentVo> getStudentsByClassIdsString(String classIdsStr) {
        Set<Integer> classIdSet = Arrays.stream(classIdsStr.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toSet());
        return getStudentByClassIds(classIdSet);
    }

    @Override
    public List<StudentVo> getStudentsByStudentIds(List<String> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Student::getWechatId, studentIds);

        List<Student> students = baseMapper.selectList(wrapper);
        return students.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }


    // TODO 分页加一个按时间排序
    // TODO 返回的StudentVo中，不应该只返回年级班级id。应该返回对应的Vo
    @Override
    public PageVo<StudentVo> getStudentsByCollegeId(StudentPageReqVo reqVo) {
        // 构建分页查询条件
        MpLambdaQueryWrapper<Student> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), Student::getNickname).
            eq(Student::getCollegeId, Redises.getClgIdByToken(reqVo.getToken()));

        return baseMapper.
            selectPage(new MpPage<>(reqVo), wrapper).
            buildVo(MapStructs.INSTANCE::po2vo);
   }


    /**
     * 根据 wxTokenVo 构建验证登录状态的 URL
     * @param wxTokenVo Token 缓存的信息
     * @return 请求 url
     */
    private String buildCheckSessionUrl(WxTokenVo wxTokenVo, String accessToken) {

        // 创建 HmacUtils 实例，指定算法和密钥
        HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, wxTokenVo.getSessionKey());
        // 对空字符串进行签名，并返回十六进制字符串形式的结果
        String signature = hmacUtils.hmacHex("");


        StringBuilder url = new StringBuilder(Constants.WxApp.PREFIX);
        url.append(Constants.WxApp.CHECK_TOKEN);
        url.append("?access_token=").append(accessToken);
        url.append("&openid=").append(wxTokenVo.getOpenid());
        url.append("&signature=").append(signature);
        url.append("&sig_method=").append("hmac_sha256");

        return url.toString();
    }

}

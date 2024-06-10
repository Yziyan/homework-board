package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.exception.CommonException;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.MiniApps;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.mapper.AuthorizationMapper;
import run.hxtia.workbd.pojo.dto.AdminUserPermissionDto;
import run.hxtia.workbd.pojo.dto.CodesInfoDto;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.*;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.*;
import run.hxtia.workbd.pojo.vo.usermanagement.response.AuthorizationVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationSetVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.organization.ClassService;
import run.hxtia.workbd.service.organization.GradeService;
import run.hxtia.workbd.service.usermanagement.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/14
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, Authorization> implements AuthorizationService {
    // Redis
    private final Redises redises;

    // 课程
    private final CourseService courseService;

    // 年级
    private final GradeService gradeService;
    // 班级
    private final ClassService classService;

    // 授权码
    private final CodesService codesService;

    // 学生
    private final StudentService studentService;

    // 学生授权表
    private final StudentAuthorizationService studentAuthorizationService;

    // 学生code
    private final StudentCodeService studentCodeService;




    @Override
    public boolean save(AuthorizationReqVo authorizationReqVo) {
        // 保存
        Authorization po = MapStructs.INSTANCE.reqVo2po(authorizationReqVo);
        return save(po);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorizationVo getAuthorizationByPermission(String permission) {
        LambdaQueryWrapper<Authorization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Authorization::getPermissions, permission);

        Authorization studentPermission = getOne(wrapper);
        if (studentPermission != null) {
            return MapStructs.INSTANCE.po2vo(studentPermission);
        }
        return null;
    }

    /**
     * 认证登录信息
     * @param token：认证参数
     * @return ：用户信息
     */
    @Override
    public CourseAndClassVo getCourseAndClasslistByAuth(String token) {
        // 定义返回
        CourseAndClassVo courseAndClassVo = new CourseAndClassVo();

        // 学生用户根据 token 获取用户信息
        StudentInfoDto studentInfoDto = studentService.getStudentByToken(token);
        // 从studentInfoDto 中取出学生基本信息
        final String studentId = studentInfoDto.getStudentVo().getWechatId();

        // 从 student_authorizations 表中获取课程和班级 ID
        StudentAuthorizationSetVo studentAuth = studentAuthorizationService.getStudentAuthorizationSetById(studentId);
        if (studentAuth == null) {
            return courseAndClassVo;
        }

        // 获取课程和班级 ID 列表
        List<Integer> courseIds = studentAuth.getCourseId().stream().map(Integer::valueOf).collect(Collectors.toList());
        List<Integer> classIds = studentAuth.getClassId().stream().map(Integer::valueOf).collect(Collectors.toList());

        // 查询课程信息
        List<CourseVo> courseList = courseService.getCoursesByIds(courseIds);
        // 判断courseList是否为空
        if (courseList.isEmpty()) {
            // 如果courseList没有，应该放入一个空的courseList
            courseList = new ArrayList<>();
        }
        // 设置返回值
        courseAndClassVo.setCourseList(courseList);

        // 查询班级信息
        List<ClassVo> classList = classService.getClassesByIds(classIds);
        // 判断classList是否为空
        if (classList.isEmpty()) {
            return courseAndClassVo;
        }

        // 构造年级及其班级列表的映射
        Map<String, List<ClassVo>> gradList = new HashMap<>();
        for (ClassVo classVo : classList) {
            GradeVo grade = gradeService.getGradeInfoById(classVo.getGradeId());
            gradList.computeIfAbsent(grade.getName(), k -> new ArrayList<>()).add(classVo);
        }


        courseAndClassVo.setGradList(gradList);

        return courseAndClassVo;
    }

    /**
     * 根据用户选择的授权的集合(课程列表和班级列表)生成code
     *
     * @param req   ：AuthCourseAndClassIdReqVo 包含课程id列表和班级id列表的请求体
     * @param token
     * @return ：生成的 code
     */
    @Override
    public String generateSelectionCode(AuthCourseAndClassIdReqVo req, String token) {
        // 从 Redis 中获取用户权限信息
        AdminUserPermissionDto userPermissionDto = redises.getT(Constants.Web.ADMIN_PREFIX + token);

        // 获取用户信息
        AdminUsers users = userPermissionDto.getUsers();

        // 生成code
        String code = Strings.getUUID(10);

        // 创建 CodesInfoDto 对象，包含生成者ID和课程+班级信息
        CodesInfoDto codesInfo = new CodesInfoDto();
        codesInfo.setPublisherId(users.getId());
        codesInfo.setCourseAndClassVo(req);

        // 将课程ID和班级ID与code关联并存储在Redis中
        redises.set(Constants.Auth.AUTH_CODE,code, req,Constants.Date.EXPIRE_DATS, TimeUnit.DAYS); // 设置一个合适的过期时间

        // 存到数据库
        codesService.saveCodesInfo(new CodeSaveReqVo(code, Math.toIntExact(users.getId()), req.getCourseIds(), req.getClassIds(),1));
        return code;
    }


    @Override
    @Transactional(readOnly = false)
    public CourseAndClassVo verificationCode(String code, String token) {
        // 检查code的状态
        Short codeStatus = codesService.checkCodeStatus(code);
        if (Objects.equals(codeStatus, Constants.Status.Code_EXIT)) {
            // 如果code在数据库中不存在，抛出异常或者返回错误信息
            JsonVos.raise(CodeMsg.AUTH_CODE_NOT_EXIT);
        }

        if (Objects.equals(codeStatus, Constants.Status.Code_USED)) {
            // 如果code已经被使用，直接返回
            JsonVos.raise(CodeMsg.AUTH_CODE_USED);
        }

        // 从redis中获取code key对应的value
        AuthCourseAndClassIdReqVo reqVo = redises.getT(Constants.Auth.AUTH_CODE + code);
        if (reqVo == null) {
            // 如果redis中没有对应的code key，检查数据库中的code
            reqVo = codesService.getCodeFromDatabase(code);
            if (reqVo == null) {
                // 如果数据库中也没有对应的code key，抛出异常或者返回错误信息
                JsonVos.raise(CodeMsg.AUTH_CODE_NOT_EXIT);
            }
        }

        // 课程 和 班级
        final String courseIdsStr = reqVo.getCourseIds();
        final String classIdsStr = reqVo.getClassIds();

        // 判断空字符串并处理
        final List<Integer> courseIds = new ArrayList<>();
        final List<Integer> classIds = new ArrayList<>();
        if (courseIdsStr != null && !courseIdsStr.trim().isEmpty()) {
            courseIds.addAll(Arrays.stream(courseIdsStr.split(","))
                .filter(id -> !id.trim().isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
        }
        if (classIdsStr != null && !classIdsStr.trim().isEmpty()) {
            classIds.addAll(Arrays.stream(classIdsStr.split(","))
                .filter(id -> !id.trim().isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
        }

        // 学生用户根据 token 获取用户信息
        StudentInfoDto studentInfoDto = studentService.getStudentByToken(token);
        // 从studentInfoDto 中取出学生基本信息
        final String wechatId = studentInfoDto.getStudentVo().getWechatId();

        // 获取课程信息
        final List<CourseVo> courseList = new ArrayList<>();
        if (!courseIds.isEmpty()) {
            courseList.addAll(courseService.getCoursesByIds(courseIds));
        }

        // 获取班级信息
        final List<ClassVo> classList = new ArrayList<>();
        if (!classIds.isEmpty()) {
            classList.addAll(classService.getClassesByIds(classIds));
        }

        // 获取所有班级的年级ID列表
        List<Integer> gradeIds = classList.stream().map(ClassVo::getGradeId).collect(Collectors.toList());

        // 获取年级ID与名称的映射
        final Map<Integer, String> gradeNames = new HashMap<>();
        if (!gradeIds.isEmpty()) {
            gradeNames.putAll(gradeService.getGradeNamesByIds(gradeIds));
        }

        // 将班级按年级名称分组
        Map<String, List<ClassVo>> gradList = classList.stream()
            .collect(Collectors.groupingBy(classVo -> gradeNames.get(classVo.getGradeId())));

        // 获取学生授权表中的现有数据
        StudentAuthorizationVo studentAuthVo = studentAuthorizationService.getStudentAuthorizationById(wechatId);
        StudentAuthorizationReqVo studentAuthReq;
        if (studentAuthVo == null) {
            // 如果学生授权表中没有记录，则插入新记录
            studentAuthReq = new StudentAuthorizationReqVo(wechatId, courseIdsStr, classIdsStr);
            studentAuthorizationService.saveStudentAuthorization(studentAuthReq);
        } else {
            // 如果学生授权表中有记录，则合并新的课程和班级ID
            String existingCourseIdsStr = studentAuthVo.getCourseId();
            String existingClassIdsStr = studentAuthVo.getClassId();

            // 合并课程ID
            Set<String> courseIdSet = new HashSet<>();
            if (existingCourseIdsStr != null && !existingCourseIdsStr.trim().isEmpty()) {
                courseIdSet.addAll(Arrays.asList(existingCourseIdsStr.split(",")));
            }
            if (courseIdsStr != null && !courseIdsStr.trim().isEmpty()) {
                courseIdSet.addAll(Arrays.asList(courseIdsStr.split(",")));
            }
            String mergedCourseIdsStr = String.join(",", courseIdSet);

            // 合并班级ID
            Set<String> classIdSet = new HashSet<>();
            if (existingClassIdsStr != null && !existingClassIdsStr.trim().isEmpty()) {
                classIdSet.addAll(Arrays.asList(existingClassIdsStr.split(",")));
            }
            if (classIdsStr != null && !classIdsStr.trim().isEmpty()) {
                classIdSet.addAll(Arrays.asList(classIdsStr.split(",")));
            }
            String mergedClassIdsStr = String.join(",", classIdSet);

            // 更新学生授权表
            studentAuthReq = new StudentAuthorizationReqVo(wechatId, mergedCourseIdsStr, mergedClassIdsStr);
            studentAuthorizationService.updateStudentAuthorization(studentAuthReq);
        }

        // 更新code状态为已使用
        codesService.updateCodeStatus(code, Integer.valueOf(Constants.Status.Code_USED));
        // 保存到studentCode记录
        studentCodeService.save(new StudentCodeReqVo(wechatId, code));

        // 构建返回对象
        CourseAndClassVo courseAndClassVo = new CourseAndClassVo();
        courseAndClassVo.setCourseList(courseList);
        courseAndClassVo.setGradList(gradList);

        // 将学生的 author字段设置为 1
        studentService.setAuthor(wechatId);

        return courseAndClassVo;
    }


    @Override
    public boolean deleteCode(String code) {
        // 逻辑删除，将传入的code的状态设置为 3（已吊销）
        return codesService.updateCodeStatus(code, Integer.valueOf(Constants.Status.Code_REVOKE));
    }

}

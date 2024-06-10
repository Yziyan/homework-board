package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.mapper.CodesMapper;
import run.hxtia.workbd.pojo.dto.AdminUserPermissionDto;
import run.hxtia.workbd.pojo.dto.CodesInfoDto;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.Codes;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassIdReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CodeSavaBatchReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CodeSaveReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CodeAndCourseAndClassInfoVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CodesVo;
import run.hxtia.workbd.service.usermanagement.CodesService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Xiaojin
 * @date 2024/5/16
 */

@Service
@RequiredArgsConstructor
public class CodesServiceImpl  extends ServiceImpl<CodesMapper, Codes> implements CodesService {

    // redis
    private final Redises redises;

    private final CodesMapper codesMapper;

    @Override
    @Transactional(readOnly = false)
    public boolean saveBatch(CodeSavaBatchReqVo reqVo) {
        List<Codes> codesList = new ArrayList<>();

        for (String courseId : reqVo.getCourseIds()) {
            for (String classId : reqVo.getClassIds()) {
                Codes codes = new Codes();
                codes.setCourseId(courseId);
                codes.setClassId(classId);
                codes.setCode(reqVo.getCode());
                codes.setPublishId(Integer.parseInt(reqVo.getUserId()));
                codes.setStatus(Constants.Status.Code_UNUSE); // Assuming 1 is the default status

                codesList.add(codes);
            }
        }

        return saveBatch(codesList);
    }

    @Override
    public List<CodeAndCourseAndClassInfoVo> getCodelistByUserId(String token) {
        // 根据token获取用户id
        // 从 Redis 中获取用户权限信息
        AdminUserPermissionDto userPermissionDto = redises.getT(Constants.Web.ADMIN_PREFIX + token);

        // 获取用户信息
        AdminUsers users = userPermissionDto.getUsers();
        Integer userId = Math.toIntExact(users.getId());

        // 从数据库获取用户的授权码和相关信息
        List<CodeAndCourseAndClassInfoVo> codeList = baseMapper.getCodeListByUserId(userId);

        // 处理返回结果，确保没有course_id或class_id的情况也能正确返回
        for (CodeAndCourseAndClassInfoVo codeInfo : codeList) {
            if (codeInfo.getCourseName() == null) {
                codeInfo.setCourseName(""); // 或者设置为一个合适的默认值
            }
            if (codeInfo.getClassName() == null) {
                codeInfo.setClassName(""); // 或者设置为一个合适的默认值
            }
            if (codeInfo.getGradeName() == null) {
                codeInfo.setGradeName(""); // 或者设置为一个合适的默认值
            }
            if (codeInfo.getCollegeName() == null) {
                codeInfo.setCollegeName(""); // 或者设置为一个合适的默认值
            }
        }

        return codeList;
    }

    @Override
    public boolean saveCodesInfo(CodeSaveReqVo codeSaveReqVo) {

       Codes po = MapStructs.INSTANCE.reqVo2po(codeSaveReqVo);
       return save(po);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteCode(String code) {
        if (code == null || code.isEmpty()) return false;

        MpLambdaQueryWrapper<Codes> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Codes::getCode, code);
        return remove(wrapper);
    }

    @Override
    public boolean updateCodeStatus(String code, Integer status) {
        UpdateWrapper<Codes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", code).set("status", status);
        return update(updateWrapper);
    }

    @Override
    public Short checkCodeStatus(String code) {
        MpLambdaQueryWrapper<Codes> queryWrapper = new MpLambdaQueryWrapper<>();
        queryWrapper.select(Codes::getStatus).eq(Codes::getCode, code);
        Codes codes = getOne(queryWrapper);
        return codes != null ? codes.getStatus() : Constants.Status.Code_EXIT;
    }

    @Override
    public AuthCourseAndClassIdReqVo getCodeFromDatabase(String code) {
        Codes codes = codesMapper.selectByCode(code);
        if (codes == null) {
            return null;
        }
        AuthCourseAndClassIdReqVo reqVo = new AuthCourseAndClassIdReqVo();
        reqVo.setCourseIds(codes.getCourseId());
        reqVo.setClassIds(codes.getClassId());
        return reqVo;
    }


}

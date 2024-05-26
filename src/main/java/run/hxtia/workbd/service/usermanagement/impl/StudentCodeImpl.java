package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.StudentCodeMapper;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.StudentCode;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentCodeReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentCodeVo;
import run.hxtia.workbd.service.usermanagement.StudentCodeService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/20
 */

@Service
@RequiredArgsConstructor
public class StudentCodeImpl extends ServiceImpl<StudentCodeMapper, StudentCode> implements StudentCodeService {

    private final StudentService studentService;


    @Override
    public boolean save(StudentCodeReqVo studentCodeReqVo) {
        // 创建一条新的数据
        StudentCode studentCode = MapStructs.INSTANCE.reqVo2po(studentCodeReqVo);
        return save(studentCode);
    }

    @Override
    public List<StudentCodeVo> studentCodelist(String token) {

        // 学生用户根据 token 获取用户信息
        StudentInfoDto studentInfoDto = studentService.getStudentByToken(token);
        // 从studentInfoDto 中取出学生基本信息
        String studentId = studentInfoDto.getStudentVo().getWechatId();


        // 创建查询条件
        MpLambdaQueryWrapper<StudentCode> queryWrapper = new MpLambdaQueryWrapper<>();
        queryWrapper.eq(StudentCode::getStudentId, studentId);

        // 执行查询
        List<StudentCode> studentCodeList = baseMapper.selectList(queryWrapper);

        // 将查询结果转换为StudentCodeVo列表
        return studentCodeList.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }
}

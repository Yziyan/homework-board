package run.hxtia.workbd.service.usermanagement;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAvatarReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentPageReqVo;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public interface StudentService extends IService<Student> {

    /**
     * 根据 code验证码换取 session_key + openId
     * @param code：验证码
     * @return ：session_key + openId
     */
    String getToken(String code) throws Exception;

    /**
     * 验证 Token 是否有效
     * @param token token
     * @return 是否有效
     */
    Boolean checkToken(String token) throws Exception;

    /**
     * 认证登录信息
     * @param token：认证参数
     * @return ：用户信息
     */
    @Transactional(readOnly = false)
    StudentInfoDto getStudentByToken(String token);

    /**
     * 完善用户信息
     * @param reqVo：用户信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(StudentReqVo reqVo, String token);

    /**
     * 用户上传头像
     * @param reqVo：头像数据
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(StudentAvatarReqVo reqVo, String token) throws Exception;

    /**
     * 获取班级下的所有学生信息
     * @param classIds：班级IDs
     * @return ：学生信息
     */
    List<StudentVo> getStudentByClassIds(Set<Integer> classIds);

    /**
     * 通过字符串形式的班级ID获取学生信息
     * @param classIdsStr：逗号分隔的班级IDs
     * @return ：学生信息
     */
    List<StudentVo> getStudentsByClassIdsString(String classIdsStr);

    /**
     * 通过学生ID获取学生信息
     * @param studentIds：学生IDs
     * @return ：学生信息
     */
    List<StudentVo> getStudentsByStudentIds(List<String> studentIds);

    /**
     * 根据学院ID分页获取学生信息
     *
     * @param reqVo@return 分页后的学生信息列表
     */
    PageVo<StudentVo> getStudentsByCollegeId(StudentPageReqVo reqVo);

    /**
     * 根据学生id获取学生详细信息
     */

}

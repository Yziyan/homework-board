package run.hxtia.workbd.service.usermanagement;

import com.baomidou.mybatisplus.extension.service.IService;
import run.hxtia.workbd.pojo.po.StudentCode;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentCodeReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentCodeVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/20
 */
public interface StudentCodeService extends IService<StudentCode> {

    /**
     * 保存学生code 信息
     * @param studentCodeReqVo ：授权信息
     * @return ：是否成功
     */
    boolean save(StudentCodeReqVo studentCodeReqVo);

    /**
     * 删除学生code信息
     * @param studentId ：学生ID
     * @return ：是否成功
     */
//    boolean delete(String studentId);

    /**
     * 根据学生id，查询学生code列表
     * @param token ：学生ID
     * @return ：学生code
     */
    List<StudentCodeVo> studentCodelist(String token);

}

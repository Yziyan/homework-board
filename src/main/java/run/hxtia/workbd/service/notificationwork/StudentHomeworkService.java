package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.StudentHomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;

import java.util.List;

/**
 * 后台管理用户作业模块 业务层
 */
@Transactional(readOnly = true)
public interface StudentHomeworkService extends IService<StudentHomework> {

    /**
     * 根据作业ID删除 用户作业
     * @param workIds：作业ID
     * @return ：是否成功
     */
    boolean removeByWorkId(List<String> workIds);
    /**
     * 根据学生ID查询学生的作业信息列表
     * @param stuId 学生ID
     * @return 学生的作业信息列表
     */
    List<StudentHomework> listByStuId(String stuId);

    /**
     * 批量添加学生作业关联
     * @param workIds 作业IDs
     * @param stuId 学生ID
     * @return 是否成功
     */
    @Transactional(readOnly = false)
    boolean addStudentHomeworks(List<Long> workIds, String stuId);

    /**
     * 根据学生ID分页获取学生作业
     * @param reqVo 分页和学生ID信息
    * @return 分页后的学生作业列表
    */
    PageVo<HomeworkVo> getHomeworksByStudentId(StudentHomeworkPageReqVo reqVo);

}

package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.vo.request.page.WorkPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkUploadReqVo;
import run.hxtia.workbd.pojo.vo.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;


/**
 * 作业模块 业务层
 */
@Transactional(readOnly = true)
public interface HomeworkService extends IService<Homework> {



    /**
     * 保存 or 编辑作业
     * @param reqVo：作业信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(HomeworkReqVo reqVo) throws Exception;


    /**
     * 根据作业ID获取作业信息
     * @param HomeworkId ：作业ID
     * @return ：作业数据
     */
    HomeworkReqVo getByHomeworkId(Long HomeworkId);

    /**
     * 删除一条or多条作业【逻辑删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByIds(String ids);

    boolean removeHistory(String ids);
}

package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotificationPublishReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotifyReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
/**
 * 通知模块 业务层
 */
@Transactional(readOnly = true)
public interface NotificationService extends IService<Notification> {

    /**
     * 分页查询通知
     *
     * @param pageReqVo ：分页信息
     * @return 分页后的数据
     */
    PageVo<NotificationVo> listPage(NotificationPageReqVo pageReqVo);

    /**
     * 保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(NotificationReqVo reqVo);

    /**
     * 删除一条or多条通知【逻辑删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByIds(String ids);

    /**
     * 根据通知ID获取通知信息
     * @param notificationId ：通知ID
     * @return ：通知数据
     */
    NotificationVo getByNotificationId(Long notificationId);

    /**
     * 删除一条or多条通知【彻底删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeHistory(String ids);

    /**
     * 保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(NotifyReqVo reqVo);


    // TODO 发布通知接口定义
    /**
     * 从微信：保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdateFromWx(NotificationPublishReqVo reqVo) throws Exception;

    /**
     * 根据UUID删除通知（逻辑删除）
     * @param notificationUuid 通知UUID
     * @return 是否成功
     */
    boolean deleteByUuid(String notificationUuid);

    /**
     * 根据UUID获取通知
     * @param notificationUuid 通知UUID
     * @return 通知信息
     */
    NotificationVo getByUuid(String notificationUuid);



}

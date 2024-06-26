package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.*;
import run.hxtia.workbd.mapper.NotificationMapper;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.po.NotificationClass;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotificationPublishReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotifyReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentNotificationReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationSetVo;
import run.hxtia.workbd.service.notificationwork.NotificationClassesService;
import run.hxtia.workbd.service.notificationwork.NotificationService;
import run.hxtia.workbd.service.notificationwork.StudentNotificationService;
import run.hxtia.workbd.service.usermanagement.StudentAuthorizationService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    private final StudentNotificationService studentNotificationService;

    private final StudentAuthorizationService studentAuthorizationService;

    private final StudentService studentService;
    // redis
    private final Redises redises;

    private final NotificationMapper notificationMapper;

    private final NotificationClassesService notificationClassesService;

    /**
     * 分页查询通知
     * @param pageReqVo ：分页信息
     * @return 分页后的数据
     */
    @Override
    public PageVo<NotificationVo> listPage(NotificationPageReqVo pageReqVo) {
        return null;
    }

    @Override
    public PageVo<NotificationVo> getNotificationsByToken(NotificationPageReqVo pageReqVo) {
        Integer collegeId = Redises.getClgIdByToken(pageReqVo.getToken());
        IPage<Notification> page = new MpPage<>(pageReqVo);
        IPage<Notification> notificationPage = notificationMapper.selectNotificationsByCollegeId(collegeId, page);

        List<NotificationVo> notificationVos = notificationPage.getRecords().stream()
            .map(notification -> {
                NotificationVo vo = new NotificationVo();
                BeanUtils.copyProperties(notification, vo);
                return vo;
            }).collect(Collectors.toList());

        PageVo<NotificationVo> pageVo = new PageVo<>();
        pageVo.setData(notificationVos);
        pageVo.setCount(notificationPage.getTotal());
        pageVo.setPages(notificationPage.getPages());
        pageVo.setPageSize(notificationPage.getSize());
        pageVo.setCurrentPage(notificationPage.getCurrent());

        return pageVo;
    }

    /**
     * 保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(NotificationReqVo reqVo) {
        return saveOrUpdate(MapStructs.INSTANCE.reqVo2po(reqVo));
    }

    /**
     * 删除一条or多条通知【逻辑删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean removeByIds(String ids) {
        List<String> notificationIds = Arrays.asList(ids.split(","));
        if (CollectionUtils.isEmpty(notificationIds)) return false;

        // 查出所有通知【并且设置删除】
        List<Notification> notifications = Streams.list2List(baseMapper.selectBatchIds(notificationIds), (notification -> {
            notification.setStatus(Constants.Status.NOTIFICATION_STATUS_DEL);
            return notification;
        }));

        return updateBatchById(notifications);
    }

    /**
     * 根据通知ID获取作业信息
     * @param notificationId ：通知ID
     * @return ：通知数据
     */
    @Override
    public NotificationVo getByNotificationId(Long notificationId) {
        if (notificationId == null || notificationId <= 0) return null;
        MpLambdaQueryWrapper<Notification> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Notification::getId, notificationId).eq(Notification::getStatus, Constants.Status.NOTIFICATION_STATUS_UNDER);
        return MapStructs.INSTANCE.po2vo(baseMapper.selectOne(wrapper));
    }

    /**
     * 删除一条or多条通知【彻底删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean removeHistory(String ids) {
        if (!StringUtils.hasLength(ids)) return false;

        List<String> notificationIds = Arrays.asList(ids.split(","));
        // 检查通知是否能删除
//        checkNotificationRemove(notificationIds);
        boolean deleteNotification = removeByIds(notificationIds);
        boolean deleteStudentNotification = studentNotificationService.removeByNotificationId(notificationIds);
        // 在用户作业表里删除通知
        if (!deleteNotification || !deleteStudentNotification) {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(NotifyReqVo reqVo) {
        return saveOrUpdate(MapStructs.INSTANCE.reqVo2po(reqVo));
    }



    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdateFromWx(NotificationPublishReqVo reqVo) {
        // 使用 token 解析出微信 id
        String weChatId = MiniApps.getOpenId(reqVo.getWxToken());
        reqVo.getNotification().setPublishId(weChatId);

        // 查看当前用户权限
        StudentAuthorizationSetVo authInfo = studentAuthorizationService.getStudentAuthorizationSetById(weChatId);

        // 检查权限
        if (!checkPermission(reqVo, authInfo)) {
            return JsonVos.raise(CodeMsg.AUTH_NOT_PUBLISH);
        }

        return saveOrUpdate(reqVo);
    }

    private boolean checkPermission(NotificationPublishReqVo reqVo, StudentAuthorizationSetVo authInfo) {
        if (Objects.equals(reqVo.getType(), Constants.Status.NOTIFICATION_STATUS_USER)) {
            // 发给学生
            String studentIdsStr = reqVo.getReqTos().getStudentId();
            Set<String> studentIds = new HashSet<>(Arrays.asList(studentIdsStr.split(",")));

            // 获取这些学生的班级ID
            List<StudentVo> students = studentService.getStudentsByStudentIds(new ArrayList<>(studentIds));
            Set<String> classIdSet = students.stream()
                .map(student -> String.valueOf(student.getClassId()))
                .collect(Collectors.toSet());

            // 检查是否有权限
            return classIdSet.stream()
                .allMatch(authInfo.getClassId()::contains);
        } else {
            // 发班级
            return authInfo.getClassId().contains(reqVo.getReqTos().getClassId());
        }
    }

    // TODO 待完成发布通知实现
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(NotificationPublishReqVo reqVo) {
        // 随机生成通知 UUID
        String notificationUuid = Strings.getUUID(10);
        reqVo.getNotification().setNotificationUuid(notificationUuid);

        // 发班级
        if (Objects.equals(reqVo.getType(), Constants.Status.NOTIFICATION_STATUS_CLASS)) {
            // 发给班级
            // 1. 保存通知源信息
            saveOrUpdate(reqVo.getNotification());

            // 2. 找出班级下的所有学生
            String classIdsStr = reqVo.getReqTos().getClassId();
            List<StudentVo> students = studentService.getStudentsByClassIdsString(classIdsStr);

            // 3. 保存通知班级表
            List<NotificationClass> notificationClasses = Arrays.stream(classIdsStr.split(","))
                .map(classId -> new NotificationClass(notificationUuid, Integer.parseInt(classId)))
                .collect(Collectors.toList());

            notificationClassesService.saveBatch(notificationClasses);

            // 4. 保存学生通知
            List<StudentNotificationReqVo> studentNotificationReqVoList = students.stream()
                .map(student -> new StudentNotificationReqVo(null,
                    student.getWechatId(),
                    notificationUuid,
                    Constants.Status.STUDENT_NOTIFICATION_STATUS_UNREAD))
                .collect(Collectors.toList());

            return studentNotificationService.saveBatch(studentNotificationReqVoList);
        }

        // 发学生
        if (Objects.equals(reqVo.getType(), Constants.Status.NOTIFICATION_STATUS_USER)) {
            // 发给学生
            String studentIdsStr = reqVo.getReqTos().getStudentId();
            Set<String> studentIds = new HashSet<>(Arrays.asList(studentIdsStr.split(",")));

            // 获取这些学生的班级ID
            List<StudentVo> students = studentService.getStudentsByStudentIds(new ArrayList<>(studentIds));

            // 1. 保存通知源信息
            saveOrUpdate(reqVo.getNotification());

            // 2. 保存学生通知
            List<StudentNotificationReqVo> studentNotificationReqVoList = students.stream()
                .map(student -> new StudentNotificationReqVo(null,
                    student.getWechatId(),
                    notificationUuid,
                    Constants.Status.STUDENT_NOTIFICATION_STATUS_UNREAD))
                .collect(Collectors.toList());

            return studentNotificationService.saveBatch(studentNotificationReqVoList);
        }

        return false;
    }


    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdateFromAdmin(NotificationPublishReqVo reqVo) {
        // 获取管理员ID作为发布者ID
        String adminId = String.valueOf(reqVo.getAdminId());
        reqVo.getNotification().setPublishId(adminId);

        // 管理员无需权限检查，可以直接发布
        return saveOrUpdate(reqVo);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteByUuid(String notificationUuid) {
        MpLambdaQueryWrapper<Notification> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Notification::getNotificationUuid, notificationUuid);
        Notification notification = new Notification();
        notification.setStatus(Constants.Status.NOTIFICATION_STATUS_DEL);  // 设置逻辑删除标志
        return this.update(notification, wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationVo getByUuid(String notificationUuid) {
        MpLambdaQueryWrapper<Notification> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Notification::getNotificationUuid, notificationUuid)
            .eq(Notification::getStatus, Constants.Status.NOTIFICATION_STATUS_UNDER); // 仅查询未删除的记录

        Notification notification = this.getOne(wrapper);
        return MapStructs.INSTANCE.po2vo(notification);
    }


    /**
     * 检查作业是否能删除【只有是历史作业才能彻底删除】
     * @param workIds ：检查的作业ID
     */
    private void checkNotificationRemove(List<String> workIds) {
        if (CollectionUtils.isEmpty(workIds)) return;
        List<Notification> notifications = listByIds(workIds);
        for (Notification notification : notifications) {
            if (Constants.Status.NOTIFICATION_STATUS_UNDER.equals(notification.getStatus())) {
                JsonVos.raise(CodeMsg.WRONG_WORK_NO_REMOVE);
            }
        }
    }
}

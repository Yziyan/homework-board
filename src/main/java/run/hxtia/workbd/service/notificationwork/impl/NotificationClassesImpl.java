package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.mapper.NotificationClassesMapper;
import run.hxtia.workbd.mapper.NotificationMapper;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.po.NotificationClass;
import run.hxtia.workbd.service.notificationwork.NotificationClassesService;

import java.util.Collection;

/**
 * @author Xiaojin
 * @date 2024/6/10
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationClassesImpl extends ServiceImpl<NotificationClassesMapper, NotificationClass> implements NotificationClassesService  {

    @Override
    @Transactional(readOnly = false)
    public boolean saveBatch(Collection<NotificationClass> entityList) {
        return saveBatch(entityList, 1000); // 默认每次批量插入1000条数据
    }
}

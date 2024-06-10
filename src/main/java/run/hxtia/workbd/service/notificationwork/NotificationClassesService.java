package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.NotificationClass;

import java.util.Collection;

/**
 * @author Xiaojin
 * @date 2024/6/10
 */
@Transactional(readOnly = true)
public interface NotificationClassesService extends IService<NotificationClass>  {

    @Transactional(readOnly = false)
    boolean saveBatch(Collection<NotificationClass> entityList);
}

package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 通知班级关联表
 */
@Data
@TableName("notification_classes")
public class NotificationClass implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 通知UUID
     */
    @TableField("notification_uuid")
    private String notificationUuid;

    /**
     * 班级ID
     */
    @TableField("class_id")
    private Integer classId;

    public NotificationClass(String notificationUuid, Integer i) {
        this.notificationUuid = notificationUuid;
        this.classId = i;
    }
}

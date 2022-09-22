package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户-作业表(UsersWorks)实体类
 *
 * @author ZhiYan
 * @since 2022-09-21 10:23:51
 */
@Data
@TableName("users_works")
public class UserWork implements Serializable {
    private static final long serialVersionUID = 714852878467288369L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作业ID
     */
    private Long workId;
    /**
     * 阅读状态【0：未读，1：已读】
     */
    private Short workStatus;
    /**
     * 是否完成【0：未完成，1：已完成】
     */
    private Short workPin;
    /**
     * 查看次数
     */
    private Integer times;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;

}

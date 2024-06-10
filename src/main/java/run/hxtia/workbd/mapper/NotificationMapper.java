package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Notification;


@Repository
public interface NotificationMapper extends BaseMapper<Notification> {

    /**
     * 根据学院ID查询通知
     * @param collegeId 学院ID
     * @param page 分页对象
     * @return 通知列表
     */
    @Select("SELECT DISTINCT n.* FROM notification_classes nc " +
        "JOIN notifications n ON nc.notification_uuid = n.notification_uuid " +
        "JOIN classes c ON nc.class_id = c.id " +
        "JOIN grades g ON c.grade_id = g.id " +
        "WHERE g.college_id = #{collegeId}")
    IPage<Notification> selectNotificationsByCollegeId(@Param("collegeId") Integer collegeId, IPage<Notification> page);
}

package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author Xiaojin
 * @date 2024/5/18
 */


@Data
@AllArgsConstructor
@ApiModel(" 通知对象 ")
public class NotifyReqVo {

    @ApiModelProperty("通知ID【没有ID就是新建，ID > 0 是编辑】前端没有就不需要传")
    private Long id;

    @ApiModelProperty("通知UUID【没有ID就是新建，有就是编辑】，前端没有就不需要传")
    private String notificationUuid;

    /**
     * 标题
     */
    @ApiModelProperty("通知标题")
    private String title;

    /**
     * 消息内容
     */
    @ApiModelProperty("通知内容")
    private String content;

    /**
     * 用户ID
     */
    @ApiModelProperty("通知发布者ID，前端可以不需要传可以通过token获取")
    private String publishId;

    /**
     * 类型
     */
    @ApiModelProperty("通知类型，不知道类型就传 “ANNOUNCEMENT” ")
    private String category;
}

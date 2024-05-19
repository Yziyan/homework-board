package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/19
 */


@Data
@AllArgsConstructor
@ApiModel(" 【保存|编辑】学生通知请求体 ")
public class StudentNotificationReqVo {

    @ApiModelProperty("学生通知ID【没有ID就是新建，ID > 0 是编辑】")
    private Long id;

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("通知uuid")
    private String notificationUuid;

    @ApiModelProperty("是否已读")
    private Boolean isRead;

}

package run.hxtia.workbd.pojo.vo.notificationwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/18
 */

@Data
@AllArgsConstructor
@ApiModel("【发布】发布通知请求对象")
public class NotificationPublishReqVo {

    @ApiModelProperty("通知info")
    private NotifyReqVo notification;

    @ApiModelProperty("通知发类型(1班级/2学生)")
    private String type;

    @ApiModelProperty("发给谁？班级为单位发还是学生为单位发？")
    private NotificationReqTos reqTos;

    // C 端学生想要发布通知，需要 Token
    private String wxToken;

    public void fillInfo(String wxToken) {
        this.wxToken = wxToken;
    }
}

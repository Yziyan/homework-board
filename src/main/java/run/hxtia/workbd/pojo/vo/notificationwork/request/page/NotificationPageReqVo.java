package run.hxtia.workbd.pojo.vo.notificationwork.request.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;

import java.util.Date;

@Data
@ApiModel("通知模块分页请求")
@EqualsAndHashCode(callSuper = true)
public class NotificationPageReqVo extends KeywordPageReqVo {

    @JsonIgnore
    private String token;
}

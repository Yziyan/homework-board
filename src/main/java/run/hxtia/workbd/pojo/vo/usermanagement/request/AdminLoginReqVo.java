package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.CaptchaReqVo;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("【登录】后台模块")
public class AdminLoginReqVo extends CaptchaReqVo {

    @NotBlank
    @ApiModelProperty(value = "邮件", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}

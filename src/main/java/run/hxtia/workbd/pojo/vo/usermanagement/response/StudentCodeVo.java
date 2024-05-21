package run.hxtia.workbd.pojo.vo.usermanagement.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/21
 */

@Data
@ApiModel("学生Code返回对象")
public class StudentCodeVo {

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("授权码code")
    private String code;

    @ApiModelProperty("创建时间")
    private String createdAt;
}

package run.hxtia.workbd.pojo.vo.usermanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Xiaojin
 * @date 2024/5/20
 */

@Data
@AllArgsConstructor
@ApiModel("【保存】学生code 信息请求体")
public class StudentCodeReqVo {

        @ApiModelProperty("学生ID")
        private String studentId;

        @ApiModelProperty("授权码code")
        private String code;
}

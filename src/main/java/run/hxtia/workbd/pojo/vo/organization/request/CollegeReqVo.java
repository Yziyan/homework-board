package run.hxtia.workbd.pojo.vo.organization.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Data
@AllArgsConstructor
@ApiModel("【保存】学院实体")
public class CollegeReqVo {

    @NotBlank
    @ApiModelProperty(value = "学院名", required = true)
    private String name;

    @ApiModelProperty(value = "学院描述", required = true)
    private String description;

    @ApiModelProperty(value = "学院logo", required = true)
    private String logoUrl;

    public CollegeReqVo() {
    }

    public CollegeReqVo(String name) {
        this.name = name;
    }
}

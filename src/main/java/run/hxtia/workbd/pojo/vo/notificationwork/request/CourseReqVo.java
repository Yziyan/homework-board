package run.hxtia.workbd.pojo.vo.notificationwork.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Data
@ApiModel("【保存】课程实体")
public class CourseReqVo {

    @ApiModelProperty(value = "课程 Id, 传递就是编辑课程信息")
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "课程名", required = true)
    private String name;

    @ApiModelProperty(value = "课程描述", required = true)
    private String description;

    @ApiModelProperty(value = "课程教师id", required = true)
    private Integer teacherId;


    private Integer collegeId;

    @JsonIgnore
    private String token;
}

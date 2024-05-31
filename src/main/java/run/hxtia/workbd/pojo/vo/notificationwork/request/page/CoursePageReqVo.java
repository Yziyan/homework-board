package run.hxtia.workbd.pojo.vo.notificationwork.request.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CoursePageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("学院ID，C 端需要传，B 端无需传递")
    private Integer collegeId;

    @JsonIgnore
    private String token;
}

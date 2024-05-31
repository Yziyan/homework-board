package run.hxtia.workbd.pojo.vo.usermanagement.request.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;

/**
 * @author Xiaojin
 * @date 2024/5/31
 */
@Data
@ApiModel("学生信息分页请求Vo")
@EqualsAndHashCode(callSuper = true)
public class StudentPageReqVo extends KeywordPageReqVo {

    @JsonIgnore
    private String token;
}

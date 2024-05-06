package run.hxtia.workbd.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.admin.HomeworkService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/admin/works")
@RequiredArgsConstructor
@Api(tags = "HomeworkController")
@Tag(name = "HomeworkController", description = "作业管理模块")
public class HomeworkController {

    private final HomeworkService homeworkService;
    @PostMapping("/api/homeworks")
    @ApiOperation("教师发布作业任务")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_CREATE)
    public JsonVo create(@Valid HomeworkReqVo reqVo) throws Exception {
        if (homeworkService.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @GetMapping("/api/homeworks/{homework_id}")
    @ApiOperation("查看指定的作业。")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_READ)
    public DataJsonVo<HomeworkReqVo> searchOne(@NotNull @RequestParam Long HomeworkId) {
        return JsonVos.ok(homeworkService.getByHomeworkId(HomeworkId));
    }



    @PostMapping("/api/homeworks/{homework_id}")
    @RequiresPermissions(Constants.Permission.WORK_MANAGE_DELETE)
    @ApiOperation("删除指定的作业")
    public JsonVo remove(@RequestParam @NotBlank(message = "ids是必传参数") String ids){
        if (homeworkService.removeByIds(ids)) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

}

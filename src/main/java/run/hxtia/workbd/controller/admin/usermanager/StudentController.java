package run.hxtia.workbd.controller.admin.usermanager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentPageReqVo;
import run.hxtia.workbd.service.usermanagement.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author Xiaojin
 * @date 2024/5/31
 */
@RestController("adminStudentController")
@RequestMapping("/admin/userManager/students")
@Api(tags = "StudentsController")
@Tag(name = "StudentsController", description = "【B端】学生模块")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 新增获取某个学院下所有学生信息的接口，支持分页
    @PostMapping("/searchListPage")
    @ApiOperation("分页获取学生信息")
    @RequiresPermissions(Constants.Permission.STUDENT_READ)
    public PageJsonVo<StudentVo> getStudentsByCollegeId(@RequestBody StudentPageReqVo pageReqVo, HttpServletRequest request) {
        pageReqVo.setToken(request.getHeader(Constants.Web.HEADER_TOKEN));
        return JsonVos.ok(studentService.getStudentsByCollegeId(pageReqVo));
    }

    @GetMapping("/{wechatId}")
    @ApiOperation("根据wechatId获取学生详细信息")
    @RequiresPermissions(Constants.Permission.STUDENT_READ)
    public DataJsonVo<StudentVo> getStudentInfo(@PathVariable @NotNull String wechatId) {
        return JsonVos.ok(MapStructs.INSTANCE.po2vo(studentService.getById(wechatId)));
    }
}

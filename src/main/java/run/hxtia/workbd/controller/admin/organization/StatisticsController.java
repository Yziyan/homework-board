package run.hxtia.workbd.controller.admin.organization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.usermanagement.AdminUserService;
import run.hxtia.workbd.service.usermanagement.StudentService;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */


@RestController
@RequestMapping("/admin/organization/statistics")
@Api(tags = "StatisticsController")
@Tag(name = "StatisticsController", description = "【C端】数据管理模块")
@RequiredArgsConstructor
public class StatisticsController {
    private final StudentService studentService;
    private final AdminUserService adminUserService;
    // 课程
    private final CourseService courseService;
    private final HomeworkService homeworkService;


    //TODO 学生人数
    @GetMapping("/countTotalStudents")
    @ApiOperation("统计学生总人数")
    public DataJsonVo<Integer> countTotalStudents() {
        return JsonVos.ok(studentService.countTotalStudents());
    }
    // TODO 管理员人数
    // 统计管理员总人数
    @GetMapping("/countTotalAdmins")
    @ApiOperation("统计管理员总人数")
    public DataJsonVo<Integer> countTotalAdmins() {
        return JsonVos.ok(adminUserService.countTotalAdmins());
    }

    // TODO 课程数
    @GetMapping("/countTotalCourses")
    @ApiOperation("统计课程总数")
    public DataJsonVo<Integer> countTotalCourses() {
        return JsonVos.ok(courseService.countTotalCourses());
    }
    // TODO 作业数
    @GetMapping("/countTotalHomeworks")
    @ApiOperation("统计作业总数")
    public DataJsonVo<Integer> countTotalHomeworks() {
        return JsonVos.ok(homeworkService.countTotalHomeworks());
    }
    // TODO 通知数

    // TODO 每个课程的作业数和每个课程的作业完成数

}

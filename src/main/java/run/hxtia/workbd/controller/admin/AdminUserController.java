package run.hxtia.workbd.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import run.hxtia.workbd.common.commoncontroller.BaseController;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.vo.request.AdminLoginReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserRegisterReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserReqVo;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.result.JsonVo;
import run.hxtia.workbd.service.admin.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.function.Function;


@RestController
@RequestMapping("/admin/users")
@Api(tags = "AdminUserController")
@Tag(name = "AdminUserController", description = "用户模块")
@RequiredArgsConstructor
public class AdminUserController extends BaseController<AdminUsers, AdminUserReqVo> {

    private final AdminUserService adminUserService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public DataJsonVo<AdminLoginVo> login(@Valid @RequestBody AdminLoginReqVo reqVo) {
        return JsonVos.ok(adminUserService.login(reqVo));
    }

    @PostMapping("/register")
    @ApiOperation("注册超管【组织发起者】")
    public JsonVo register(@Valid @RequestBody AdminUserRegisterReqVo reqVo) {
        if (adminUserService.register(reqVo)) {
            return JsonVos.ok(CodeMsg.REGISTER_OK);
        } else {
            return JsonVos.error(CodeMsg.REGISTER_ERROR);
        }
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public JsonVo logout(@RequestHeader(Constants.Web.HEADER_TOKEN) String token) {
        Redises redises = Redises.getRedises();
        // 清空缓存中的token就可以了
        redises.del(Constants.Web.HEADER_TOKEN + token);
        return JsonVos.ok();
    }

    @Override
    protected IService<AdminUsers> getService() {
        return adminUserService;
    }

    @Override
    protected Function<AdminUserReqVo, AdminUsers> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}

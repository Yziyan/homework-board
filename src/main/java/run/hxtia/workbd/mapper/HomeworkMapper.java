package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.AdminUserRole;
import run.hxtia.workbd.pojo.po.Homework;

/**
 * 作业模块 持久层
 */
@Repository
public interface HomeworkMapper extends BaseMapper<Homework> {

}

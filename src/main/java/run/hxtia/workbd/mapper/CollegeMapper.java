package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.College;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */

@Repository
public interface CollegeMapper extends BaseMapper<College> {

    /**
     * 用户注册时，给其注册默认的组织
     * @param collegeInfo：学院信息
     * @return ：是否成功
     */
    @Insert("INSERT INTO colleges (name) VALUES (#{collegeInfo.name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDefaultRegisterCollegeName(@Param("collegeInfo") College collegeInfo);
}

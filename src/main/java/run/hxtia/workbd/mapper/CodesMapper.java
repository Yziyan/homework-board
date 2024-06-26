package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import run.hxtia.workbd.pojo.po.Codes;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CodeAndCourseAndClassInfoVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */
public interface CodesMapper extends BaseMapper<Codes> {
    @Select("SELECT c.code, c.created_at, c.status, co.name AS course_name, cl.name AS class_name, gr.name AS grade_name, col.name AS college_name " +
        "FROM codes c " +
        "LEFT JOIN courses co ON c.course_id = co.id " +
        "LEFT JOIN classes cl ON c.class_id = cl.id " +
        "LEFT JOIN grades gr ON cl.grade_id = gr.id " +
        "LEFT JOIN colleges col ON gr.college_id = col.id " +
        "WHERE c.publish_id = #{userId}")
    List<CodeAndCourseAndClassInfoVo> getCodeListByUserId(@Param("userId") Integer userId);

    /**
     * 根据code获取授权码信息
     * @param code 授权码
     * @return 授权码信息
     */
    @Select("SELECT * FROM codes WHERE code = #{code}")
    Codes selectByCode(@Param("code") String code);

}

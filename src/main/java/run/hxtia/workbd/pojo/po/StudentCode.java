package run.hxtia.workbd.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiaojin
 * @date 2024/5/20
 */


@Data
@TableName("student_code")
public class StudentCode implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 授权码id
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

}

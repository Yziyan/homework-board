package run.hxtia.workbd.pojo.vo.notificationwork.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.Strings;

import java.util.Date;

@Data
@ApiModel("作业信息")
public class HomeworkVo {

    @ApiModelProperty("作业ID")
    private Long id;

    @ApiModelProperty("作业标题")
    private String title;

    @ApiModelProperty("作业描述")
    private String description;

    @ApiModelProperty("图片链接，多个图片用 「,」拼接")
    private String pictureLinks;

    @ApiModelProperty("截止日期")
    private Long deadline;

    @ApiModelProperty("创建时间")
    private Long createdAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;

    @ApiModelProperty("课程ID")
    private Integer courseId;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("发布者ID")
    private String publisherId;

    @ApiModelProperty("发布平台")
    private String publishPlatform;

    @ApiModelProperty("状态【1：启用 0：未启用】")
    private Short status;


    public void jointPictureLinks(String joint) {
        if (!StringUtils.hasLength(pictureLinks)) {
            return;
        }

        // 说明有图片，每一个都拼接上前缀
        String[] links = pictureLinks.split(Constants.SpecialChars.COMMA);

        for (int i = 0; i < links.length; i++) {
            links[i] =  joint + links[i];
        }

        pictureLinks = Strings.join(links, Constants.SpecialChars.COMMA);
    }
}


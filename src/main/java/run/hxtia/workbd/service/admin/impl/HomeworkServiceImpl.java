package run.hxtia.workbd.service.admin.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.HomeworkMapper;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.service.admin.HomeworkService;
import run.hxtia.workbd.service.admin.StudentHomeworkService;
import run.hxtia.workbd.service.admin.UserWorkService;

import java.util.Arrays;
import java.util.List;

/**
 * 作业模块 【管理】业务层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements HomeworkService {

    private final StudentHomeworkService studentHomeworkService;
    @Override
    public boolean saveOrUpdate(HomeworkReqVo reqVo) throws Exception {
        Homework po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 上传图片
        String filePath = "";
        if (reqVo.getId() == null) {
            // 保证是新建时，才来上传图片
            List<MultipartFile> pictureFiles = reqVo.getPictureFiles();
            if (!CollectionUtils.isEmpty(pictureFiles))
                filePath = Uploads.uploadImages(pictureFiles);

            // 如果上传成功，保存图片路径到数据库
            if (StringUtils.hasLength(filePath))
                po.setPictures(filePath);
        }

        try {
            // 保存数据
            return saveOrUpdate(po);
        } catch (Exception e) {
            // 出现异常将刚上传的图片给删掉
            log.error(e.getMessage());
            e.printStackTrace();
            Uploads.deleteFiles(filePath);
            return false;
        }
    }

    /**
     * 根据作业ID获取作业信息
     * @param HomeworkId ：作业ID
     * @return ：作业数据
     */
    @Override
    public HomeworkReqVo getByHomeworkId(Long HomeworkId) {
        if (HomeworkId == null || HomeworkId <= 0) return null;
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Homework::getId, HomeworkId).eq(Homework::getEnable, Constants.Status.WORK_ENABLE);
        return MapStructs.INSTANCE.po2vo(baseMapper.selectOne(wrapper));
    }

        /**
         * 删除一条or多条作业【逻辑删除】
         * @param ids：需要删除的作业ID
         * @return ：是否成功
         */
        @Override
        public boolean removeByIds(String ids) {
            List<String> workIds = Arrays.asList(ids.split(","));
            if (CollectionUtils.isEmpty(workIds)) return false;

            // 查出所有作业【并且设置不可见】
            List<Homework> works = Streams.map(baseMapper.selectBatchIds(workIds), (work -> {
                work.setEnable(Constants.Status.WORK_DISABLE);
                return work;
            }));

            return updateBatchById(works);
        }


    /**
     * 删除一条or多条作业【彻底删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeHistory(String ids) {
        if (!StringUtils.hasLength(ids)) return false;

        List<String> workIds = Arrays.asList(ids.split(","));
        // 检查作业是否能删除
        checkWorkRemove(workIds);
        boolean deleteWork = removeByIds(workIds);
        boolean deleteUserWork = studentHomeworkService.removeByWorkId(workIds);
        // 在用户作业表里删除作业
        if (!deleteWork || !deleteUserWork) {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
        return true;
    }
    /**
     * 检查作业是否能删除【只有是历史作业才能彻底删除】
     * @param homeworkIds ：检查的作业ID
     */
    private void checkWorkRemove(List<String> homeworkIds) {
        if (CollectionUtils.isEmpty(homeworkIds)) return;
        List<Homework> homeworks = listByIds(homeworkIds);
        for (Homework homework : homeworks) {
            if (Constants.Status.WORK_ENABLE.equals(homework.getEnable())) {
                JsonVos.raise(CodeMsg.WRONG_WORK_NO_REMOVE);
            }
        }
    }

}

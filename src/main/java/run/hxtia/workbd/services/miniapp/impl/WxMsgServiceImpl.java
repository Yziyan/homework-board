package run.hxtia.workbd.services.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.mapStruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.utils.MiniApps;
import run.hxtia.workbd.pojo.vo.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.services.miniapp.WxMsgService;

@Slf4j
@Service
@RequiredArgsConstructor
public class WxMsgServiceImpl implements WxMsgService {

    private final WxMaService wxMaService;
    private final Redises redises;

    @Override
    public boolean sendMsg(WxSubscribeMessageReqVo subscribeMessage, String token) {
        MiniApps.checkAppId(wxMaService);
        try {
            // 从redis中取出用户信息
            WxMaJscode2SessionResult session = (WxMaJscode2SessionResult) redises.get("user:" + token);
            subscribeMessage.setToUser(session.getOpenid());

            // 如果客户端没有传TemplateId，那么设置默认的
            if (!StringUtils.hasLength(subscribeMessage.getTemplateId()))
                subscribeMessage.setTemplateId(MiniApps.TEMPLATE_ID);

            wxMaService.getMsgService().
                sendSubscribeMsg(MapStructs.INSTANCE.reqVo2wxVo(subscribeMessage));

        } catch (WxErrorException e) {
            log.error(e.getMessage());
            return false;
        } finally {
            // 清理ThreadLocal
            WxMaConfigHolder.remove();
        }
        return true;
    }
}

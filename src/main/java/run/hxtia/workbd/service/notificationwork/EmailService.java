package run.hxtia.workbd.service.notificationwork;

/**
 * 邮件发送 业务层
 */
public interface EmailService {
    /**
     * 发送验证码邮件
     * @param to：接收方邮件地址
     * @param subject：主题
     */
    void sendHTMLEmail(String to, String subject) throws Exception;
}

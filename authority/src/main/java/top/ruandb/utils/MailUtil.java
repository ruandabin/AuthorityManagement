package top.ruandb.utils;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.google.common.collect.Sets;

import top.ruandb.dto.Mail;

@Slf4j
public class MailUtil {

    public static boolean send(Mail mail) {

    	//TODO
        String from = "";
        int port = 25;
        String host = "smtp.163.com";
        String pass = "";
        String nickname = "权限管理系统";

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setSmtpPort(port);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setMsg(mail.getMessage());
            email.send();
            log.info("{} 发送邮件到 {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "发送邮件到" + StringUtils.join(mail.getReceivers(), ",") + "失败", e);
            return false;
        }
    }

    public static void main(String[] args) {
    	
    	Set<String> receiver = Sets.newHashSet();
    	receiver.add("");//TODO
    	Mail mail = Mail.builder().subject("划小会议").receivers(receiver).message("5.28").build();
    	MailUtil.send(mail);
	}
}


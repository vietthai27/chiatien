package com.thai27.chiatien.Ulti;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    @Autowired
    JavaMailSender mailSender;

    public String sendNewPassword(String userMail, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("FIS.auto.service@gmail.com");
        message.setTo(userMail);
        message.setSubject("Tài khoản " + username + " đã reset mật khẩu thành công");
        message.setText("Mật khẩu mới là: " + password);
        mailSender.send(message);
        return "Đổi mật khẩu thành công, vui lòng check emal để biết mật khẩu mới !";
    }

    public String sendSignupSuccess(String userMail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("FIS.auto.service@gmail.com");
        message.setTo(userMail);
        message.setSubject("WELCOME");
        message.setText("Tài khoản với tên người dùng: " + username + " đã đăng ký thành công");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            return "Email không hợp lệ vui lòng thử email khác";
        }
        return "Đăng ký tài khoản " + username + " thành công !!!";
    }

    public String sendSignupRequest(String userMail, String username, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("FIS.auto.service@gmail.com");
        message.setTo(userMail);
        message.setSubject("Đăng ký tạo tài khoản");
        message.setText("Bạn vừa đăng ký tạo tài khoản với tên người dùng: " + username + ". " +
                "Vui lòng nhập mã xác thực: " + code + " để tạo tài khoản trên hệ thống");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            return "Email không hợp lệ vui lòng thử email khác";
        }
        return "Đăng ký tạo tài khoản thành công vui lòng kiểm tra email !!!";
    }

}

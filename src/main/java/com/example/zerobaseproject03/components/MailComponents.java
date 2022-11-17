package com.example.zerobaseproject03.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


// 메일 인증을 위한 클래스(gmail)

@Component
@RequiredArgsConstructor

public class MailComponents {
    private final JavaMailSender javaMailSender;



    // 메일을 보내는 메소드(보내는 주소, 메일 제목, 메일 내용)
    public boolean sendMail(String mail, String subject, String text){

        boolean result = false;

        MimeMessagePreparator mimeMessagePreparator =
                new MimeMessagePreparator() {
                    @Override
                    public void prepare(MimeMessage mimeMessage) throws Exception {

                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                                true,"UTF-8");

                        helper.setTo(mail);
                        helper.setSubject(subject);
                        helper.setText(text);
                    }
                };

        try {
            javaMailSender.send(mimeMessagePreparator);
            result = true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}

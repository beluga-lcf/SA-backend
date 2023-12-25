package com.example.genius.service;

import com.example.genius.entity.Mail;

public interface EmailService {

    void sendRelateEmail(String customMail, String relater, String relatee);

    public void sendSimpleMail(Mail mail);

    public void sendHtmlMail(Mail mail);

    public void sendRegisterVerifyMail(String customMail, String verifyCode);
}

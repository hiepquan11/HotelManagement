package com.project1.HotelManagement.Service.Email;

import org.thymeleaf.context.Context;

public interface EmailService {
    public void sendEmail(String to , String subject, String template, Context context);
}

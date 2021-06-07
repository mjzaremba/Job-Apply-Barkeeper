package com.pjatk.barkeeper.barbackend.email;

public interface EmailSender {
    void sendEmail(String receiver, String emailMessage);
}

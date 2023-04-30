package com.miu.onlinemarketplace.service.email.emailsender;

import com.miu.onlinemarketplace.common.dto.*;
import com.miu.onlinemarketplace.common.enums.UserStatus;
import com.miu.onlinemarketplace.entities.EmailHistory;

public interface EmailSenderService {
    Boolean sendSignupMail(SignUpMailSenderDto signUpMailSenderDto);

    Boolean sendForgotPasswordMail(ForgotPasswordMailSenderDto passwordMailSenderDto);

    Boolean sendOrderMail(OrderMailSenderDto orderMailSenderDto);

    Boolean sendAccountActivateAndSuspendMail(Long userId, UserStatus userStatus);

    Boolean sendPaymentFailNotificationMail(PaymentFailMailSenderDto mailSenderDto);

    void resendMail(EmailHistory emailHistory);
    Boolean sendPaymentNotification(OrderPayDto orderPayDto);
}

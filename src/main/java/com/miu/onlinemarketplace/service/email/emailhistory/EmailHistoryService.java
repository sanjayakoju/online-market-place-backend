package com.miu.onlinemarketplace.service.email.emailhistory;

import com.miu.onlinemarketplace.common.dto.AllEmailHistoryPage;
import com.miu.onlinemarketplace.common.dto.EmailHistorySaveDto;
import com.miu.onlinemarketplace.entities.EmailHistory;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

public interface EmailHistoryService {
    void saveEmailHistory(EmailHistorySaveDto emailHistorySaveDto);

    AllEmailHistoryPage getEmailHistoryPage(Pageable pageable);

    Stream<EmailHistory> getAllEmailUnsuccessfulMail(Integer noOfMail);

    void updateSendStatusEmailHistory(Long emailHistoryId, Boolean status);
}

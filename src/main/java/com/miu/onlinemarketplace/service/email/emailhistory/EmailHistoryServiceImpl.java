package com.miu.onlinemarketplace.service.email.emailhistory;

import com.miu.onlinemarketplace.common.dto.AllEmailHistoryPage;
import com.miu.onlinemarketplace.common.dto.EmailHistoryDto;
import com.miu.onlinemarketplace.common.dto.EmailHistorySaveDto;
import com.miu.onlinemarketplace.entities.EmailHistory;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.EmailHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class EmailHistoryServiceImpl implements EmailHistoryService {

    private final EmailHistoryRepository emailHistoryRepository;
    private final ModelMapper modelMapper;

    public EmailHistoryServiceImpl(EmailHistoryRepository emailHistoryRepository, ModelMapper modelMapper) {
        this.emailHistoryRepository = emailHistoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveEmailHistory(EmailHistorySaveDto emailHistoryDto) {
        EmailHistory emailHistory = modelMapper.map(emailHistoryDto, EmailHistory.class);//TODO: implement Model Mapper
        emailHistoryRepository.save(emailHistory);
    }

    @Override
    public AllEmailHistoryPage getEmailHistoryPage(Pageable pageable) {
        Page<EmailHistory> emailHistoryPage = emailHistoryRepository.findAllByIsSend(pageable, true);
        AllEmailHistoryPage allEmailHistoryPage = new AllEmailHistoryPage();
        allEmailHistoryPage.setTotalPage(emailHistoryPage.getTotalPages());
        allEmailHistoryPage.setTotalItem(emailHistoryPage.getTotalElements());
        allEmailHistoryPage.setEmailHistory(
                emailHistoryPage.get()
                        .map(emailHistory -> modelMapper.map(emailHistory, EmailHistoryDto.class))
                        .collect(Collectors.toList()));
        return allEmailHistoryPage;
    }

    @Override
    public Stream<EmailHistory> getAllEmailUnsuccessfulMail(Integer noOfMail) {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "emailHistoryId");
        Page<EmailHistory> emailHistoryPage = emailHistoryRepository.findAllByIsSend(pageable, false);
        return emailHistoryPage.get();
    }

    @Override
    public void updateSendStatusEmailHistory(Long emailHistoryId, Boolean status) {
        EmailHistory emailHistory = emailHistoryRepository.findById(emailHistoryId)
                .orElseThrow(() -> new DataNotFoundException("Email History not found"));
        emailHistory.setIsSend(status);
        emailHistoryRepository.save(emailHistory);
    }
}

package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.AllEmailHistoryPage;
import com.miu.onlinemarketplace.service.email.emailhistory.EmailHistoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email-history")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class EmailHistoryController {
    private final EmailHistoryService emailHistoryService;

    public EmailHistoryController(EmailHistoryService emailHistoryService) {
        this.emailHistoryService = emailHistoryService;
    }

    @GetMapping
    AllEmailHistoryPage getEmailHistoryPage(@RequestParam(defaultValue = "10", required = false) int perPage,
                                            @RequestParam(defaultValue = "1", required = false) int page,
                                            @RequestParam(defaultValue = "emailHistoryId", required = false) String sortBy,
                                            @RequestParam(defaultValue = "ASC", required = false) String order
    ) {
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.valueOf(order), sortBy));
        return emailHistoryService.getEmailHistoryPage(pageable);
    }
}

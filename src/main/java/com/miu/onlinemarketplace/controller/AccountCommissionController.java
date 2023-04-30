package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.service.accountcommission.AccountCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountCommissionController {

    private AccountCommissionService accountCommissionService;

    public AccountCommissionController(AccountCommissionService accountCommissionService) {
        this.accountCommissionService = accountCommissionService;
    }

    // TODO - Please check the use of this method. If required implement and also integrate with UI, else remove.
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/accountcommission")
    public ResponseEntity<?> saveAccountCommission(@Validated @RequestBody List<OrderItem> orderItem) {
        if(accountCommissionService != null) {
            accountCommissionService.saveCommission();
            return new ResponseEntity<>("Account Commission Save Successfully!!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account Commission Save Successfully!!!", HttpStatus.BAD_REQUEST);
    }
}

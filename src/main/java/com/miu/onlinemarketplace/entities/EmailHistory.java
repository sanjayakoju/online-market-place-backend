package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.MailType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EmailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailHistoryId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailType mailType;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String fromEmail;
    @Column(nullable = false)
    private String toEmail;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @UpdateTimestamp
    private LocalDateTime mailSendDateTime;
    @Column(name = "is_send", nullable = false, columnDefinition = "boolean default false")
    private Boolean isSend;
}

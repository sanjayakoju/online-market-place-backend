package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.MailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;
    private String fromEmail;
    private String subject;
    @Column(columnDefinition = "text" )
    private String template;
    @Enumerated(EnumType.STRING)
    private MailType mailType;
}

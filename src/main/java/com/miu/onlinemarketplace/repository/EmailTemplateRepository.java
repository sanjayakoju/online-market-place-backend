package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.common.enums.MailType;
import com.miu.onlinemarketplace.entities.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findByMailType(MailType mailType);
}

package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
}

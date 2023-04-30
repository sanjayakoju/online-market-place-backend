package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.security.models.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByRole(EnumRole role);

}

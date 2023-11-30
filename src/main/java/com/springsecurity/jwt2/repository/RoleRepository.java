package com.springsecurity.jwt2.repository;


import com.springsecurity.jwt2.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}

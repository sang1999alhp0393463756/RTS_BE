package com.example.demo123.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo123.entity.ERole;
import com.example.demo123.entity.Role;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    @Transactional
    @Modifying
    @Query(value = "update rts.user_roles set role_id = ?1 where user_id = ?2 ",nativeQuery = true)
    void updateRoleForUser(Integer role_id,Integer user_id);
    String getRole = "SELECT * FROM rts.roles";
    @Query(value = getRole, nativeQuery = true)
    List<Role> getRole();
    
}
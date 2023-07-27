package com.coniverse.dangjang.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.user.entity.User;

/**
 * USER Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface UserRepository extends JpaRepository<User, String> {
}

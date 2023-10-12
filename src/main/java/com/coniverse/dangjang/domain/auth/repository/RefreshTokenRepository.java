package com.coniverse.dangjang.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.coniverse.dangjang.domain.auth.entity.RefreshToken;

/**
 * RefreshToken Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}

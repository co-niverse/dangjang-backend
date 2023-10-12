package com.coniverse.dangjang.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.coniverse.dangjang.domain.auth.entity.BlackToken;

/**
 * 로그아웃 BlackToken Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface BlackTokenRepository extends CrudRepository<BlackToken, String> {

}

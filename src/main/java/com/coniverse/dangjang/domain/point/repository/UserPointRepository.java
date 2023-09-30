package com.coniverse.dangjang.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.point.entity.UserPoint;

public interface UserPointRepository extends JpaRepository<UserPoint, String> {
	/**
	 * point 갱신
	 * oauthId로 사용자 정보 조회
	 *
	 * @since 1.0.0
	 */
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update UserPoint u set u.point=?2 where u.oauthId=?1")
	void updatePointByOauthId(String oauthId, int point);
}

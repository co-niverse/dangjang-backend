package com.coniverse.dangjang.domain.version.entity;

import com.coniverse.dangjang.global.support.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * version entity
 *
 * @author TEO
 * @since 1.3.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Version extends BaseEntity {
	@Id
	private Long id;
	private String minVersion;
	private String latestVersion;

	@Builder
	private Version(String minVersion, String latestVersion) {
		this.minVersion = minVersion;
		this.latestVersion = latestVersion;
	}
}

package com.coniverse.dangjang.domain.version.entity;

import com.coniverse.dangjang.global.support.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * app version entity
 *
 * @author TEO
 * @since 1.3.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Version extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long versionId;
	private String minVersion;
	private String latestVersion;

	@Builder
	private Version(String minVersion, String latestVersion) {
		this.minVersion = minVersion;
		this.latestVersion = latestVersion;
	}
}

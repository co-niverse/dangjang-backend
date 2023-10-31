package com.coniverse.dangjang.domain.version.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class Version {
	@Id
	@CreatedDate
	private LocalDateTime createdAt;
	private String minVersion;
	private String latestVersion;

	@Builder
	private Version(String minVersion, String latestVersion) {
		this.minVersion = minVersion;
		this.latestVersion = latestVersion;
	}
}

package com.coniverse.dangjang.domain.user.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.coniverse.dangjang.domain.user.dto.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author EVE
 * @since 1.0
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
	/**
	 * UserId : 복합키 클래스
	 *
	 * @since 1.0
	 */
	@EmbeddedId
	private UserId userId;
	private String nickName;
	private Date birthday;

	@Column(name = "activity_amount", length = 10)
	private String activityAmount;

	private int height;
	private int recommended_calorie;

	private Status status;

	@CreatedDate
	private Date created_at;

	private Date updated_at;

	@Column(name = "image_path", length = 10)
	private String imagePath;

	private Boolean diabetes;
	private int diabetes_year;
	private Boolean medicine;
	private Boolean injection;

	@Builder
	public User(UserId userId, Status status, String nickname, Date birthday, String activityAmount, int height, int recommended_calorie,
		Date updated_at, String imagePath, Boolean diabetes, int diabetes_year, Boolean medicine, Boolean injection) {
		this.userId = userId;
		this.nickName = nickname;
		this.birthday = birthday;
		this.activityAmount = activityAmount;
		this.height = height;
		this.recommended_calorie = recommended_calorie;
		this.updated_at = updated_at;
		this.imagePath = imagePath;
		this.diabetes = diabetes;
		this.diabetes_year = diabetes_year;
		this.medicine = medicine;
		this.injection = injection;
		this.status = status;
	}
}

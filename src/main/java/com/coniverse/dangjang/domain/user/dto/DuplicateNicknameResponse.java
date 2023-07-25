package com.coniverse.dangjang.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DuplicateNicknameResponse {
	private boolean isDuplicate;
}

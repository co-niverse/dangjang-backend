package com.coniverse.dangjang.domain.guide.exercise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/guide/exercise")
public class ExerciseGuideController {
	private final ExerciseGuideSearchService exerciseGuideSearchService;

	@GetMapping
	public ResponseEntity<SuccessSingleResponse<ExerciseGuideResponse>> get(@ValidLocalDate @RequestParam String date,
		@AuthenticationPrincipal User principal) {
		ExerciseGuideResponse response = exerciseGuideSearchService.findGuide(principal.getUsername(), date);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

}

package com.coniverse.dangjang.domain.healthMetric.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.coniverse.dangjang.domain.healthMetric.exception.IncorrectCreatedAtException;

public class CreatedAtUtilTest {
	CreatedAtUtil createdAtUtil = new CreatedAtUtil();

	@Test
	void 생성일자를_생성한다() {
		assertThat(createdAtUtil.generateCreatedAt(1, 1)).isEqualTo(LocalDate.now().getYear() + "-01-01");

	}

	@ParameterizedTest
	@ValueSource(ints = {2, 4, 6, 9, 11})
	void 생성일자가_올바르지_않을_때_예외가_발생한다(int month) {
		assertThatThrownBy(() -> createdAtUtil.generateCreatedAt(month, 31)).isInstanceOf(IncorrectCreatedAtException.class);
	}
}

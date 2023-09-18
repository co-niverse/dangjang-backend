package com.coniverse.dangjang.domain.guide.exercise.convert;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.global.util.LocalDateChangeUtil;

@SpringBootTest
public class LocalDateChangeUtilTest {
	@Autowired
	private LocalDateChangeUtil localDateChangeUtil;
	private LocalDate KST날짜 = LocalDate.of(2023, 12, 31);
	private LocalDate UTC날짜 = LocalDate.of(2023, 12, 31);

	@Test
	public void KST시간을_UTC에맞게_변환한다() {
		//given & when
		LocalDate 변환된_날짜 = localDateChangeUtil.convertDateToUTC(KST날짜);
		ZonedDateTime UTC변환날짜 = ZonedDateTime.of(변환된_날짜.atTime(0, 0, 0), ZoneId.of("Asia/Seoul")).withZoneSameInstant(ZoneId.of("UTC"));
		//then
		assertThat(UTC변환날짜.getYear()).isEqualTo(KST날짜.getYear());
		assertThat(UTC변환날짜.getMonthValue()).isEqualTo(KST날짜.getMonthValue());
		assertThat(UTC변환날짜.getDayOfMonth()).isEqualTo(KST날짜.getDayOfMonth());
		assertThat(UTC변환날짜.getHour()).isEqualTo(15);
		assertThat(UTC변환날짜.getMinute()).isEqualTo(0);
		assertThat(UTC변환날짜.getSecond()).isEqualTo(0);
	}

	@Test
	public void UTC시간을_KST에맞게_변환한다() {
		//given & when
		LocalDate 변환된_날짜 = localDateChangeUtil.convertDateToKST(UTC날짜);
		ZonedDateTime KST변환날짜 = ZonedDateTime.of(변환된_날짜.atTime(15, 0, 0), ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
		//then
		assertThat(KST변환날짜.getYear()).isEqualTo(UTC날짜.getYear());
		assertThat(KST변환날짜.getMonthValue()).isEqualTo(UTC날짜.getMonthValue());
		assertThat(KST변환날짜.getDayOfMonth()).isEqualTo(UTC날짜.getDayOfMonth());
		assertThat(KST변환날짜.getHour()).isEqualTo(0);
		assertThat(KST변환날짜.getMinute()).isEqualTo(0);
		assertThat(KST변환날짜.getSecond()).isEqualTo(0);

	}
}

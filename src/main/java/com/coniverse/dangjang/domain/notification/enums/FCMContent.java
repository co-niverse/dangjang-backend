package com.coniverse.dangjang.domain.notification.enums;

import lombok.AllArgsConstructor;

/**
 * FCM Message 내용 구성
 * <p>
 * 접속하지 않은 날짜에 따라 title과 body 내용을 달르게 구성한다.
 * 접속하지 않은 날짜 기준은 1일전,3일전,7일전,2주전,한달전으로 구성된다.
 * </p>
 *
 * @author EVE
 * @since 1.7.0
 */
@AllArgsConstructor
public enum FCMContent {
	TITLE("오늘의 건강 상태는?", "오늘 접속하지 않았어요!", "당뇨 관리 작심삼일?!", "일주일 동안의 건강 상태 확인하기", "2주간 나의 건강 변화 기록하기", "한 달 동안의 나의 건강 상태는?!"),
	BODY("꾸준히 기록하고, 건강 상태를 비교해 봐요!", "어제 이어서 오늘도 열심히 건강을 관리해 봐요 ", "3일 동안 접속하지 않았어요~ 다시 건강을 관리해 봐요!", "일주일 동안의 상태를 기록하고 확인해 봐요 !",
		"2주 동안의 변화가 궁금하지 않으세요? 접속해서 건강 상태를 확인해 봐요 ! ", "오랜만에 건강 상태를 확인해볼까요?");
	private final String defaultMessage;
	private final String day1;
	private final String day3;
	private final String day7;
	private final String day14;
	private final String day30;

	/**
	 * 접속하지 않은 기간에 맞춰 title을 반환한다
	 *
	 * @param dateDiff 접속하지 않은 기간
	 * @return title
	 * @since 1.7.0
	 */
	public String getTitle(int dateDiff) {
		switch (dateDiff) {
			case 1:
				return this.day1;
			case 3:
				return this.day3;
			case 7:
				return this.day7;
			case 14:
				return this.day14;
			case 30:
				return this.day30;
			default:
				return this.defaultMessage;
		}
	}

	/**
	 * 접속하지 않은 기간에 맞춰 body를 반환한다
	 *
	 * @param dateDiff 접속하지 않은 기간
	 * @return body
	 * @since 1.7.0
	 */
	public String getBody(int dateDiff) {
		switch (dateDiff) {
			case 1:
				return this.day1;
			case 3:
				return this.day3;
			case 7:
				return this.day7;
			case 14:
				return this.day14;
			case 30:
				return this.day30;
			default:
				return this.defaultMessage;
		}
	}

}

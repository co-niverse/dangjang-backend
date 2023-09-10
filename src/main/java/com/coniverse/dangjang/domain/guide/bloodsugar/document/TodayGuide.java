package com.coniverse.dangjang.domain.guide.bloodsugar.document;

/**
 * 혈당 오늘의 가이드
 *
 * @author TEO
 * @since 1.0.0
 */
public class TodayGuide {
	private final String alert;
	private int count;

	protected TodayGuide(String alert) {
		this.alert = alert;
		this.count = 0;
	}

	/**
	 * 개수를 1 증가시킨다.
	 *
	 * @since 1.0.0
	 */
	protected void plusCount() {
		this.count++;
	}

	/**
	 * 개수를 1 감소시킨다.
	 *
	 * @since 1.0.0
	 */
	protected void minusCount() {
		this.count--;
	}

	/**
	 * 경보가 같은지 확인한다.
	 *
	 * @param alert 경보
	 * @return 경보가 같으면 true, 다르면 false
	 */
	protected boolean isSameAlert(String alert) {
		return this.alert.equals(alert);
	}
}

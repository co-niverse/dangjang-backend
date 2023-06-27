package dangjang.challenge.global.dto;

import dangjang.challenge.global.dto.content.Content;

/**
 * 공통된 성공 응답을 보내기 위해 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record SuccessResponse(boolean success, int errorCode, String message, Content data) {
	public SuccessResponse(String message, Content data) {
		this(true, 0, message, data);
	}
}

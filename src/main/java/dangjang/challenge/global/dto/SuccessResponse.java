package dangjang.challenge.global.dto;

/**
 * 공통된 성공 응답을 보내기 위해 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record SuccessResponse(boolean success, int errorCode, String message, Object data) {
	public SuccessResponse(String message, Object data) {
		this(true, 0, message, data);
	}
}

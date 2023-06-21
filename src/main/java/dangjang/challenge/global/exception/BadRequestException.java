package dangjang.challenge.global.exception;

public class BadRequestException extends BusinessException {
	public BadRequestException() {
		super(400, "잘못된 요청입니다.");
	}
}

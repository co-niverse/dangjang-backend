package dangjang.challenge.global.exception;

public class NonExistentUserException extends BusinessException {
	public NonExistentUserException() {
		super(404, "존재하는 유저가 아닙니다.");
	}
}

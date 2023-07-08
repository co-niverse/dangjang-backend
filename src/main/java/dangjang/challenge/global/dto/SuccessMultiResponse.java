package dangjang.challenge.global.dto;

import java.util.List;

public record SuccessMultiResponse<T>(boolean success, int errorCode, String message, List<T> data) {
	public SuccessMultiResponse(String message, List<T> data) {
		this(true, 0, message, data);
	}
}

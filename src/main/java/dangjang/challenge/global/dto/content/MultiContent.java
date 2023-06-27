package dangjang.challenge.global.dto.content;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 반환할 객체가 여러 개일 때 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record MultiContent<T>(@JsonInclude(JsonInclude.Include.NON_NULL) List<T> contents) implements Content {
	@Override
	public int getMinVersion() {
		return Content.MIN_VERSION;
	}

	@Override
	public int getLatestVersion() {
		return Content.LATEST_VERSION;
	}
}

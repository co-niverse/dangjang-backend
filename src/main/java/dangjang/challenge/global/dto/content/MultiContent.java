package dangjang.challenge.global.dto.content;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 반환할 객체가 여러 개일 때 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record MultiContent<T>(int minVersion, int latestVersion,
							  @JsonInclude(JsonInclude.Include.NON_NULL) List<T> contents) implements Content {
	public MultiContent(List<T> contents) {
		this(Content.MIN_VERSION, Content.LATEST_VERSION, contents);
	}

	@Override
	public int getMinVersion() {
		return this.minVersion;
	}

	@Override
	public int getLatestVersion() {
		return this.latestVersion;
	}
}

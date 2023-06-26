package dangjang.challenge.global.dto.content;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 반환할 객체가 한 개일 때 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record SingleContent<T>(int minVersion, int latestVersion, @JsonInclude(JsonInclude.Include.NON_NULL) T content)
	implements Content {
	public SingleContent(T content) {
		this(Content.MIN_VERSION, Content.LATEST_VERSION, content);
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

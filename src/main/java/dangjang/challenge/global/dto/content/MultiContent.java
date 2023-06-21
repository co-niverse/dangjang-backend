package dangjang.challenge.global.dto.content;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 반환할 객체가 여러 개일 때 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
public record MultiContent<T>(int minVersion, int latestVersion,
							  @JsonInclude(JsonInclude.Include.NON_NULL) List<T> contents) implements Content {
	public MultiContent(List<T> contents) {
		this(Content.minVersion, Content.latestVersion, contents);
	}
}

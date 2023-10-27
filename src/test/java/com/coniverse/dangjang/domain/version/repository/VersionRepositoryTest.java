package com.coniverse.dangjang.domain.version.repository;

import static com.coniverse.dangjang.fixture.VersionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.coniverse.dangjang.domain.version.entity.Version;
import com.coniverse.dangjang.support.annotation.JpaRepositoryTest;

/**
 * @author TEO
 * @since 1.3.0
 */
@JpaRepositoryTest
public class VersionRepositoryTest {
	@Autowired
	private VersionRepository versionRepository;

	@Test
	void 가장_최근의_버전_정보를_반환한다() {
		// given
		int count = 30;
		List<Version> versions = IntStream.rangeClosed(1, count)
			.mapToObj(i -> 버전_엔티티("1.0." + i, "1.0." + i))
			.toList();
		versionRepository.saveAll(versions);

		// when
		Version version = versionRepository.findFirstByOrderByCreatedAtDesc();

		// then
		assertAll(
			() -> assertThat(version.getMinVersion()).isEqualTo("1.0." + count),
			() -> assertThat(version.getLatestVersion()).isEqualTo("1.0." + count)
		);
	}
}

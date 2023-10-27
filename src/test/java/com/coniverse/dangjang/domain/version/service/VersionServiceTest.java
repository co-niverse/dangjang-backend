package com.coniverse.dangjang.domain.version.service;

import static com.coniverse.dangjang.fixture.VersionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.coniverse.dangjang.domain.version.dto.request.VersionRequest;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.entity.Version;
import com.coniverse.dangjang.domain.version.repository.VersionRepository;

/**
 * @author TEO
 * @since 1.3.0
 */
class VersionServiceTest {
	private VersionService versionService;
	private VersionRepository versionRepository;
	private static final String KEY = "1234";

	@BeforeEach
	void setUp() {
		versionRepository = Mockito.mock(VersionRepository.class);
		versionService = new VersionService(versionRepository, KEY);
	}

	@Test
	void 버전_응답을_반환한다() {
		// given
		String minVersion = "1.0.1";
		String latestVersion = "1.0.1";
		Version version = 버전_엔티티(minVersion, latestVersion);
		doReturn(version).when(versionRepository).findFirstByOrderByCreatedAtDesc();

		// when
		VersionResponse<?> versionResponse = versionService.getVersionResponse();

		// then
		assertThat(versionResponse.minVersion()).isEqualTo(minVersion);
		assertThat(versionResponse.latestVersion()).isEqualTo(latestVersion);
	}

	@Test
	void 키가_같으면_버전을_성공적으로_저장한다() {
		// given
		String minVersion = "1.0.1";
		String latestVersion = "1.0.1";
		doReturn(null)
			.when(versionRepository)
			.save(any());
		VersionRequest versionRequest = 버전_요청(minVersion, latestVersion, KEY);

		// when
		VersionResponse<?> versionResponse = versionService.saveVersion(versionRequest);

		// then
		assertThat(versionResponse.minVersion()).isEqualTo(minVersion);
		assertThat(versionResponse.latestVersion()).isEqualTo(latestVersion);
	}

	@Test
	void 키가_다르면_예외가_발생한다() {
		// given
		String minVersion = "1.0.1";
		String latestVersion = "1.0.1";
		VersionRequest versionRequest = 버전_요청(minVersion, latestVersion, "wrong key");

		// when
		assertThatThrownBy(() -> versionService.saveVersion(versionRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}
}

package dangjang.challenge.domain.intro.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dangjang.challenge.global.dto.SuccessResponse;
import dangjang.challenge.global.dto.content.Content;
import dangjang.challenge.global.dto.content.SingleContent;
import dangjang.challenge.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntroService {
	private int error = -1;

	public SuccessResponse getIntro() {
		Content content = new SingleContent<>(null);
		error += 1;
		if (error % 2 == 0) {
			return new SuccessResponse(HttpStatus.OK.getReasonPhrase(), content);
		}
		throw new BadRequestException();
	}
}

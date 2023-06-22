package dangjang.challenge.domain.intro.service;

import dangjang.challenge.global.dto.content.Content;
import dangjang.challenge.global.dto.content.SingleContent;
import dangjang.challenge.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntroService {
	private int error = -1;

	public Content getIntro() {
		Content content = new SingleContent<>(null);
		error += 1;
		if (error % 2 == 0) {
			return content;
		}
		throw new BadRequestException();
	}
}

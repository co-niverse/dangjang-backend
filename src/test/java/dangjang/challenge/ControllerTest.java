package dangjang.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

/**
 * WebMvcTest용 공통 메서드이다. 상속하여 사용한다.
 *
 * @author Teo
 * @since 1.0
 */
@WebMvcTest(includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
public class ControllerTest {
	@Autowired
	private MockMvc mockMvc;

	public ResultActions post(final String uri, final String content, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.post(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);
	}

	public ResultActions put(final String uri, final String content, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.put(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);
	}

	public ResultActions patch(final String uri, final String content, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.patch(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);
	}

	public ResultActions get(final String uri, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.get(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	public ResultActions get(final String uri, final MultiValueMap<String, String> params, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.get(uri, pathVariables)
				.params(params)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	public ResultActions delete(final String uri, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.delete(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
		);
	}
}

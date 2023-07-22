package com.coniverse.dangjang.support;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

/**
 * Methods for WebMvc Test
 *
 * @author TEO
 * @since 1.0
 */
public class SimpleMockMvc {
	public static ResultActions post(final MockMvc mockMvc, final String uri, final String content, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.post(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);
	}

	public static ResultActions put(final MockMvc mockMvc, final String uri, final String content, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.put(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);
	}

	public static ResultActions patch(final MockMvc mockMvc, final String uri, final String content, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.patch(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);
	}

	public static ResultActions get(final MockMvc mockMvc, final String uri, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.get(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	public static ResultActions get(final MockMvc mockMvc, final String uri, final MultiValueMap<String, String> params, final Object... pathVariables) throws
		Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.get(uri, pathVariables)
				.params(params)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	public static ResultActions delete(final MockMvc mockMvc, final String uri, final Object... pathVariables) throws Exception {
		return mockMvc.perform(
			MockMvcRequestBuilders.delete(uri, pathVariables)
				.accept(MediaType.APPLICATION_JSON)
		);
	}
}

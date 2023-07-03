package dangjang.challenge.domain.login.service;

import dangjang.challenge.domain.login.api.OAuthApiClient;
import dangjang.challenge.domain.login.model.OAuthProvider;
import dangjang.challenge.domain.login.model.Params.OAuthLoginParams;
import dangjang.challenge.domain.login.model.response.OAuthInfoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {

        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = params.oAuthToken();
        return client.requestOauthInfo(accessToken);
    }
}
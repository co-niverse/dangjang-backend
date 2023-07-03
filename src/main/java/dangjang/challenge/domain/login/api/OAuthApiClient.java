package dangjang.challenge.domain.login.api;

import dangjang.challenge.domain.login.model.OAuthProvider;
import dangjang.challenge.domain.login.model.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();

    OAuthInfoResponse requestOauthInfo(String accessToken);
}

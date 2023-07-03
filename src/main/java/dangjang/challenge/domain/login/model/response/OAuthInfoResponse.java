package dangjang.challenge.domain.login.model.response;

import dangjang.challenge.domain.login.model.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();

    OAuthProvider getOAuthProvider();
}

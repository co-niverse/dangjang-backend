package dangjang.challenge.domain.login.model.Params;

import dangjang.challenge.domain.login.model.OAuthProvider;

public interface OAuthLoginParams {
    //provider 반환
    OAuthProvider oAuthProvider();

    String oAuthToken();

}


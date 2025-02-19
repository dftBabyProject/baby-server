package com.dft.baby.web.filter.apple;

import com.dft.baby.web.exception.member.LoginException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.dft.baby.web.exception.ExceptionType.APPLE_LOGIN_MATCH_KEY_ERROR;

@Getter @Setter @AllArgsConstructor
public class ApplePublicKeyResponse {
    private List<ApplePublicKey> keys;

    public ApplePublicKeyResponse() {
    }

    public ApplePublicKey getMatchedKey(String kid, String alg) {
        return keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst()
                .orElseThrow(() ->
                        new LoginException(APPLE_LOGIN_MATCH_KEY_ERROR.getCode(), APPLE_LOGIN_MATCH_KEY_ERROR.getErrorMessage()));
    }
}
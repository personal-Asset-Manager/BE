package com.jjoony.assetmanagement.domain.auth.dto;

import java.util.Map;

public interface OAuth2Response {
    Map<String, Object> getAttributes();

    String getProvider();

    String getProviderId();

    String getNickname();

    String getEmail();
}

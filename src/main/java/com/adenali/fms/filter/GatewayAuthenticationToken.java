package com.adenali.fms.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class GatewayAuthenticationToken extends AbstractAuthenticationToken {
    private final String principal; // This will now be the User ID

    public GatewayAuthenticationToken(String userId) {
        super(AuthorityUtils.createAuthorityList("ROLE_GATEWAY"));
        this.principal = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() { return this.principal; }

    @Override
    public Object getCredentials() { return null; }
}

/*
 * Copyright 2018-Present Okta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.okta.spring.boot.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.HashSet;

final class OktaJwtAuthenticationConverter extends JwtAuthenticationConverter {

    private final String groupClaim;

    public OktaJwtAuthenticationConverter(String groupClaim) {
        this.groupClaim = groupClaim;
    }

    @Override
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        Collection<GrantedAuthority> result = new HashSet<>(super.extractAuthorities(jwt));
        result.addAll(TokenUtil.tokenClaimsToAuthorities(jwt.getClaims(), groupClaim));

        return result;
    }
}
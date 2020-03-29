/*
 * Copyright 2019-Present Entando Inc. (http://www.entando.com) All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.enatndo.client;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import com.enatndo.config.EntandoProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Component;

@Component
public class EntandoAuthClient {

    private final EntandoProperties entandoProperties;

    public EntandoAuthClient(EntandoProperties entandoProperties) {
        this.entandoProperties = entandoProperties;
    }

    interface UserDetail {

        @RequestLine("GET /users/{userId}")
        User get(@Param("userId") String userId);
    }

    public User getUserDetail(String userId) {

        // IMPORTANT: don't reuse this objects for multiple calls.
        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        ClientCredentialsResourceDetails resourceDetails = getClientCredentialsResourceDetails();

        OAuth2FeignRequestInterceptor oauth2Interceptor = new OAuth2FeignRequestInterceptor(clientContext, resourceDetails);

        return Feign.builder()
                .requestInterceptor(oauth2Interceptor)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserDetail.class, entandoProperties.getAuthServiceUri())
                .get(userId);
    }

    private ClientCredentialsResourceDetails getClientCredentialsResourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setClientId(entandoProperties.getClientId());
        resourceDetails.setClientSecret(entandoProperties.getClientSecret());
        resourceDetails.setAccessTokenUri(entandoProperties.getAccessTokenUri());
        return resourceDetails;
    }

    public static class User {

        private String id;
        private String username;
        private boolean enabled;
        private boolean emailVerified;
        private String firstName;
        private String lastName;
        private String email;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}

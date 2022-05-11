package com.main.user.auth;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import com.main.user.resources.UserAuthenticationResource;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                            .substring(AUTHENTICATION_SCHEME.length()).trim();
        
        try {

            // Validate the token
            validateToken(token, requestContext);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
            
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateToken(String token, ContainerRequestContext requestContext) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    	
    	JsonWebKeySet jwks = new JsonWebKeySet(UserAuthenticationResource.jwkList); 
        JsonWebKey jwk = jwks.findJsonWebKey("1", null,  null,  null);

         
        // Validate Token's authenticity and check claims
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
          .setRequireExpirationTime() 
          .setAllowedClockSkewInSeconds(30)
          .setRequireSubject()
          .setExpectedIssuer("electrogrid.com")
          .setVerificationKey(jwk.getKey())
          .build();
     
        try {
          //  Validate the JWT and process it to the Claims
          JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
          System.out.println("JWT validation succeeded! " + jwtClaims);
        } catch (InvalidJwtException e) {
        	System.out.println("abortWithUnauthorized");
        	abortWithUnauthorized(requestContext);
        }
    }
}

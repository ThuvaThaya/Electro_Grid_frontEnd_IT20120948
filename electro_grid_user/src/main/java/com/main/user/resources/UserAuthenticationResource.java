package com.main.user.resources;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import com.google.gson.Gson;
import com.main.user.extras.UserNotFoundException;
import com.main.user.models.User;
import com.main.user.services.UserService;

@Path("/authentication")
public class UserAuthenticationResource {
	
	public static List<JsonWebKey> jwkList = null;
	   
	static {    
		jwkList = new LinkedList<>(); 
	    for (int kid = 1; kid <= 3; kid++) { 
	      JsonWebKey jwk = null;
	      try {
	        jwk = RsaJwkGenerator.generateJwk(2048); 
	        System.out.println("PUBLIC KEY (" + kid + "): "+ jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
	      } catch (JoseException e) {
	        e.printStackTrace();
	      } 
	      jwk.setKeyId(String.valueOf(kid));  
	      jwkList.add(jwk); 
	      
	    } 
	}
	  
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public Response authenticateUser(@FormParam("email") String email, 
                                     @FormParam("password") String password) {

//		System.out.println("credentials " + credentials.toString());
		
        try {

            // Authenticate the user using the credentials provided
            authenticate(email, password);

            // Issue a token for the user
            String token = issueToken(email);

            // Return the token on the response
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("token", token);
            String o = new Gson().toJson(hashMap);
            
            return Response.status(Response.Status.OK).entity(o).build();
            
        }catch (UserNotFoundException e) {
        	 return Response.status(Response.Status.FORBIDDEN).entity("User not found for provided email and password.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }      
    }

    private void authenticate(String email, String password) throws UserNotFoundException {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    	
    	UserService service = new UserService();
    	User user = service.validateUser(email, password);
    	
    	if(user == null) {
    		throw new UserNotFoundException("User not found for provided email and password.");
    	}
    }

    private String issueToken(String email) {
    	RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);
        
        senderJwk.setKeyId("1");
     
        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("electrogrid.com");
        claims.setExpirationTimeMinutesInTheFuture(30);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(2);
        claims.setSubject(email);
      
        JsonWebSignature jws = new JsonWebSignature();
     
        jws.setPayload(claims.toJson());
         
        jws.setKeyIdHeaderValue(senderJwk.getKeyId());
        jws.setKey(senderJwk.getPrivateKey());
         
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256); 
     
        String jwt = null;
        try {
          jwt = jws.getCompactSerialization();
        } catch (JoseException e) {
          e.printStackTrace();
        }
     
        System.out.println("jwt "+ jwt);
        return jwt;
 
    }

}

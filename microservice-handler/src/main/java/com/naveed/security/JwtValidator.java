package com.naveed.security;

import static com.naveed.security.SecurityConstants.SECRET;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtValidator {
	
	 public boolean validateToken(String token){
	        try{
	            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
	            return true;
	        }catch (SignatureException ex){
	            System.out.println("Invalid JWT Signature");
	        }catch (MalformedJwtException ex){
	            System.out.println("Invalid JWT Token");
	        }catch (ExpiredJwtException ex){
	            System.out.println("Expired JWT token");
	        }catch (UnsupportedJwtException ex){
	            System.out.println("Unsupported JWT token");
	        }catch (IllegalArgumentException ex){
	            System.out.println("JWT claims string is empty");
	        }
	        return false;
	    }


	    //Get user Id from token

	    public Long getUserIdFromJWT(String token){
	        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	        String id = (String)claims.get("id");

	        return Long.parseLong(id);
	    }

}

package com.codeword.snb.config.JSONWebToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {
     private final SecretKey key =
             Keys.hmacShaKeyFor(JwtConstants.JWT_SECRETE.getBytes());

     public String generateToken(Authentication auth){
         return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .signWith(key)
                .claim("email", auth.getName())
                .compact();
     }

     public String getEmailFromJwtToken(String jwt){
         jwt = jwt.substring(7);
         Claims claims = Jwts.parserBuilder()
                 .setSigningKey(key)
                 .build()
                 .parseClaimsJws(jwt)
                 .getBody();
         return String.valueOf(claims.get("email"));
     }

}

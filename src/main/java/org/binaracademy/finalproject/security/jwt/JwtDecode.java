package org.binaracademy.finalproject.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Request.RequestMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JwtDecode {

    private final HttpServletRequest request;
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    public RequestMeta decode(){
        String bearerJwtToken = request.getHeader("Authorization");
        String[] tokenJwt = bearerJwtToken.split(" ");
        return RequestMeta.builder()
                .userId(Long.valueOf(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(tokenJwt[1]).getBody().get("userId").toString()))
                .username(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(tokenJwt[1]).getBody().getIssuer())
                .email(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(tokenJwt[1]).getBody().get("email").toString())
                .token(tokenJwt[1])
                .type(tokenJwt[0])
                .build();
    }
}
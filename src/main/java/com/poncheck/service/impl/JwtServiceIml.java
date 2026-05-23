package com.poncheck.service.impl;

import com.poncheck.entity.User;
import com.poncheck.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceIml implements JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    @Value("${JWT_EXPIRATION}")
    private Long jwtExpiration;
    @Value("${JWT_REFRESH_EXPIRATION}")
    private Long refreshExpiration;


    public Date extractExpiration(String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith(getHashedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    public String extractUsername(String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith(getHashedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    @Override
    public String generateToken(User user) {
        return buildToken(user, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(User user, Long expiration){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claims(Map.of("role", user.getRole()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getHashedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    private SecretKey getHashedKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

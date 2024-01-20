package pl.janiuk.shopapi.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Client;
import pl.janiuk.shopapi.service.IJwtTokenService;


import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenService implements IJwtTokenService {
    @Value(value = "${jwt.secret}")
    private String jwtSecret;
    private static final long jwtExpirationMs = 1000 * 60 * 60;

    @Override
    public String generateToken(Client client) {
        String username = client.getUsername();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .subject(username)
                .claim("role", client.getRoleByRoleId().getRole())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    private Key getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

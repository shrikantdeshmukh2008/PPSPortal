package mff.oms.user.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String SECRET_KEY;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public String extractEmail(String token) {
    	 return Jwts.parserBuilder()
                 .setSigningKey(SECRET_KEY.getBytes())
                 .build()
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();
    }
}

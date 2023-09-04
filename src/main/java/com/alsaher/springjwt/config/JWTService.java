package com.alsaher.springjwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JWTService {
  private static final String SECRET_KEY = "136bYNXkXF5OEvsGFQk7HNQlOE2snyNLz+q7WAauSJzO2FnU1oCZE1YZx/m6J7ovr796eK29W5lCGLSPoWSptD0KiKNcqyPK8jffnJsrjVxjXHXIpMjBbqoXbEJg/jjp9/pEzrJxTJ/KK4U5cmyJeBLwvkoVpU38ylXp0TZQ8JVV/3+dhaDoevv8WVlb6mpp8Sib3wF5TRoyTgXA00kmA/XVXFhTREVZV/4wNmlBF1aTBC1SIRk5RKn/RgDKZQprMC4cfplhESgMu1GCLU7XHOTB4EI0l82JXyKOcnpiZzUF0Ml1j26BVHuR+rnr31GteOyNlole2snPwpiACylCEGplyQ034BwHAj/df8sHA8s=";

  public String extractUsername(String token) {
    return extractClaim(token,claims -> claims.getSubject());

  }
  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  public Claims extractAllClaims(String token) {
    return Jwts
            .parserBuilder()
            .setSigningKey(getSigninKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  public boolean isTokenValid(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, claims -> claims.getExpiration());
  }



  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(),userDetails);

  }
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 24*60*1000))
            .signWith(getSigninKey(), SignatureAlgorithm.HS256)
            .compact();

  }

  private Key getSigninKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

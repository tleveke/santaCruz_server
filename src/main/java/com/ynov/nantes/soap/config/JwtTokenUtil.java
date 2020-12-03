package com.ynov.nantes.soap.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;
  
  public static final long JWT_TOKEN_VALIDITY = 5*60*60;

  @Value("${jwt.secret}")
  private String secret;

//récupérer le nom d'utilisateur du Token jwt
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

//récupérer la date d'expiration du Token jwt
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

//pour récupérer les informations du Token, nous aurons besoin de la clé secrète
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

//vérifier si le jeton a expiré
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private Boolean ignoreTokenExpiration(String token) {
    // Ici on spécifie les Tokens pour ça l'expiration est ignorée
    return false;
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

//lors de la création du token -
 //1. Définir les revendications du Token, telles que l'émetteur, l'expiration, le sujet et l'ID
 // 2. Signez le JWT à l'aide de l'algorithme HS512 et de la clé secrète.
 // 3. Selon la sérialisation compacte JWS (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
 // compactage du JWT en une chaîne de sécurité URL
  private String doGenerateToken(Map<String, Object> claims, String subject) {

    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  public Boolean canTokenBeRefreshed(String token) {
    return (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  //Assez évident mais on commente pour le bon sens :3 ==> Validation du jeton
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
  public Boolean validateTokenEmail(String token, String email) {
      final String username = getUsernameFromToken(token);
      return (username.equals(email) && !isTokenExpired(token));
    }
}
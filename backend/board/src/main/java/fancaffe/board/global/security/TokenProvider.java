package fancaffe.board.global.security;

import fancaffe.board.domain.member.dto.MemberDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String AccessTokenCreate(String memberId) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(15, ChronoUnit.HOURS)
        );

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(memberId)
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }
    public String RefreshTokenCreate(MemberDTO member) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(15, ChronoUnit.HOURS)
        );


        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(member.getId().toString())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build().parseClaimsJws(token)
                .getBody();
        if (claims.getExpiration().before(new Date())) {
            throw new ExpiredJwtException(null, claims, "Token has expired");
        }
        return claims.getSubject();
    }

    public String extractIdByAccessToken(String accessToken){
        System.out.println("[TokenProvider] extractAllClaims");
        String jwt = accessToken.replace("Bearer ", "").trim(); // 'Bearer ' 부분 제거 및 공백 제거

        // Claims 객체 가져오기
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                        .build().parseClaimsJws(jwt)
                        .getBody();
        return claims.getSubject();
    }


    public String extractIdByRefreshToken(String refreshToken){

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build().parseClaimsJws(refreshToken)
                .getBody().getSubject();
    }
}

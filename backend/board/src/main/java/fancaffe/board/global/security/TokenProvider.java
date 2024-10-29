package fancaffe.board.global.security;

import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String create(MemberDTO member){
//        Date expiryDate = Date.from(
//                Instant.now()
//                        .plus(15, ChronoUnit.SECONDS)
//        );
        // 만료 시간 설정 (15초 후)
        Instant nowUtc = Instant.now(); // 현재 UTC 시간
        Instant expiryInstant = nowUtc.plus(1, ChronoUnit.DAYS); // 15초 후의 UTC 시간

        // KST로 변환
        LocalDateTime expiryDateTimeKST = LocalDateTime.ofInstant(expiryInstant, ZoneId.of("Asia/Seoul"));
        Date expiryDate = Date.from(expiryDateTimeKST.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("expiry date" + expiryDate);
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

    public String extractAllClaims(String token){
        System.out.println("[TokenProvider] extractAllClaims");
        String jwt = token.replace("Bearer ", "").trim(); // 'Bearer ' 부분 제거 및 공백 제거

        // Claims 객체 가져오기
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                        .build().parseClaimsJws(jwt)
                        .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getIssuer());
        return claims.getSubject();
    }
}

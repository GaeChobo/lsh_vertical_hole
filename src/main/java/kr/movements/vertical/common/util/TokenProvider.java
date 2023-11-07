package kr.movements.vertical.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kr.movements.vertical.common.exception.UnauthorizedException;
import kr.movements.vertical.entity.MemberEntity;
import kr.movements.vertical.repo.MemberRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName : kr.mv.common.jwt
 * fileName    : TokenProvider
 * author      : ckr
 * date        : 2023/01/15
 * description : jwt 토큰 생성 및 활용을 위한 클래스
 */
@Component
public class TokenProvider implements InitializingBean {

    private final MemberRepository memberRepository;

    private final String accessSecret;

    private Key accessKey;


    @Override
    public void afterPropertiesSet() {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
    }

    public TokenProvider(@Value("${jwt.access-secret}") String accessSecret, MemberRepository memberRepository){
        this.accessSecret = accessSecret;
        this.memberRepository = memberRepository;
    }

    public String createToken(Long userId, Authentication authentication, boolean isAccessToken, List<GrantedAuthority> authorities) {
        Date currentDate = new Date();
        Date expirationDate = isAccessToken ?
                new Date(currentDate.getTime() + Duration.ofHours(24).toMillis()) :
                new Date(currentDate.getTime() + Duration.ofDays(1).toMillis()); // 24시간 후 만료

        List<String> authorityStrings = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("userId", userId)
                .claim("authorities", authorityStrings)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .compact();
    }




    public Authentication getAuthentication(String token) {
        // 토큰으로부터 claims 객체 획득
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        long userId = (long) claims.get("userId");
        List<String> authorityStrings = (List<String>) claims.get("authorities"); // authorities 정보 읽어옴

        List<GrantedAuthority> authorities = authorityStrings.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        MemberEntity memberEntity = memberRepository.findByIdAndHasDeleted(userId, false).orElseThrow(() -> new UnauthorizedException("인증에 실패하였습니다."));

        User user = new User(memberEntity.getId().toString(), memberEntity.getUserPw(), authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities); // authorities 전달
    }



    public void validateToken(String token){
        try {
            // 액세스 토큰이 유효한지 검증한다.
            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            //FIXME : 오류 구분 필요. 토큰이 잘못된건지, 만료인지
        } catch (SignatureException e) {
            throw new UnauthorizedException("토큰 서명이 일치하지 않습니다.");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("형식이 일치하지않는 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("토큰 구성이 올바르지 않습니다.");
        } catch (ExpiredJwtException e){
            throw new UnauthorizedException("토큰 유효기간이 만료되었습니다. 토큰 재발급을 받아주세요.");
        }
    }
}

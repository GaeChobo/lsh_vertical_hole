package kr.movements.vertical.common.filter;

import kr.movements.vertical.common.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final String authorizationHeader = "Authorization";
    private final TokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(authorizationHeader);

        if (StringUtils.hasText(accessToken)) {
            log.info("doFilterInternal accessToken @@@@@@@@@ : " + accessToken);
            tokenProvider.validateToken(accessToken);
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context 에 '{}' 인증 정보를 저장했습니다.", authentication.getName());
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}
    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(authorizationHeader);
//        String kakaoToken = request.getHeader(kakaoAuthorizationHeader);
        if (StringUtils.hasText(accessToken)) {
            log.info("doFilterInternal accessToken @@@@@@@@@ : "+ accessToken);
            tokenProvider.validateToken(accessToken, true);
            Authentication authentication = tokenProvider.getAuthentication(accessToken, true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context 에 '{}' 인증 정보를 저장했습니다.", authentication.getName());
//        }
//        else if (StringUtils.hasText(kakaoToken)) {
            // TODO: 카카오 토큰으로 SM에 회원정보가 있는지 확인만 하는 용도
//            Map<String, Object> kakaoAccess = oauthKakao.getKakaoAccess(kakaoToken); //카카오인증 api에 토큰으로 인증요청.
//            Long kakaoId = (Long) kakaoAccess.get("id");
            //회원정보가 있는지 확인하는 api
//            oauthTokenProvider.getKakaoAuthenticationCheck(kakaoToken, kakaoId);
//            request.setAttribute("kakaoNumber", kakaoAccess.get("id"));

        } else {
            SecurityContextHolder.clearContext();
//            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
    */


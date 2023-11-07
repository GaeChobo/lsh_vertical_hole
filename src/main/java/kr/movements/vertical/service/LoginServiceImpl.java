package kr.movements.vertical.service;

import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.common.util.TokenProvider;
import kr.movements.vertical.dto.LoginDto;
import kr.movements.vertical.dto.TokenDto;
import kr.movements.vertical.entity.MemberEntity;
import kr.movements.vertical.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service(value = "loginService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final MemberRepository memberRepository;


    public TokenDto login(LoginDto loginDto) {
        MemberEntity memberEntity = memberRepository.findByUserEmailAndHasDeleted(loginDto.getEmail(), false)
                .orElseThrow(() -> new BadRequestException("회원아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), memberEntity.getUserPw())) {
            throw new BadRequestException("회원아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER_ROLE")); // 권한명 변경

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberEntity, null, authorities);

        String accessToken = tokenProvider.createToken(memberEntity.getId(), authentication, true, authorities); // authorities를 전달

        return new TokenDto(memberEntity.getId(), accessToken);
    }

}

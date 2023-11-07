package kr.movements.vertical.service;

import kr.movements.vertical.common.exception.BadRequestException;
import kr.movements.vertical.dto.MemberCreateDto;
import kr.movements.vertical.entity.MemberEntity;
import kr.movements.vertical.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Long memberCreate(MemberCreateDto dto) {

        if (StringUtils.hasText(dto.getUserEmail()) && memberRepository.existsByUserEmailAndHasDeleted(dto.getUserEmail(), Boolean.FALSE)) {
            throw new BadRequestException("이미 사용 중인 이메일입니다.");
        }

        if (StringUtils.hasText(dto.getUserPhone()) && memberRepository.existsByUserPhoneAndHasDeleted(dto.getUserPhone(), Boolean.FALSE)) {
            throw new BadRequestException("이미 사용 중인 핸드폰 번호입니다.");
        }

        String bcryptPwd = passwordEncoder.encode(dto.getUserPw());

        MemberEntity memberEntity = memberRepository.save(
                MemberEntity.builder()
                        .userName(dto.getUserName())
                        .userEmail(dto.getUserEmail())
                        .userPw(bcryptPwd)
                        .userPhone(dto.getUserPhone())
                        .build());

        return memberEntity.getId();
    }
}

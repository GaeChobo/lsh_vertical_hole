package kr.movements.vertical.service;

import kr.movements.vertical.dto.MemberCreateDto;

public interface MemberService {

    Long memberCreate(MemberCreateDto dto);
}

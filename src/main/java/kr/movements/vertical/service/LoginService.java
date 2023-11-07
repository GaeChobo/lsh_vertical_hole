package kr.movements.vertical.service;

import kr.movements.vertical.dto.LoginDto;
import kr.movements.vertical.dto.TokenDto;

public interface LoginService {

    TokenDto login(LoginDto loginDto);
}

package kr.movements.vertical.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.movements.vertical.dto.LoginDto;
import kr.movements.vertical.dto.Payload;
import kr.movements.vertical.dto.TokenDto;
import kr.movements.vertical.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Auth")
@RequestMapping("/auth")
public class AuthController {


    private final LoginService loginService;


    @ApiOperation(value = "로그인")
    @PostMapping(value = "/login")
    public Payload<TokenDto> login(@Valid @RequestBody LoginDto loginDto,
                                   HttpServletRequest request, HttpServletResponse response) {
        return new Payload<>( HttpStatus.OK, request.getServletPath(), loginService.login(loginDto), response);
    }
}

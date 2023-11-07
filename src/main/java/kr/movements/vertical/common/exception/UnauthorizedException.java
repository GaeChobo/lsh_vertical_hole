package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @file name : UnauthorizedException.java
 * @project : spring-boot-init
 * @date : Dec 7, 2022
 * @author : ckr
 * @history:
 * @program comment : 클라이언트의 인증 정보가 유효하지 않은 경우 (HTTP STATUS 401)
 */
public class UnauthorizedException extends BootException {


    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(HttpStatus code) {
        super(code);
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(Class<?> className) {
        super(HttpStatus.UNAUTHORIZED, className);
    }

    public UnauthorizedException(Class<?> className, String message) {
        super(HttpStatus.UNAUTHORIZED, className, message);
    }

    public UnauthorizedException(Exception e) {
        super(e);
    }
}

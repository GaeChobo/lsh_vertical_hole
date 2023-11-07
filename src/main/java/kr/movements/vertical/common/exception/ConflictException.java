package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @file name : ConflictException.java
 * @project : spring-boot-init
 * @date : Dec 7, 2022
 * @author : ckr
 * @history:
 * @program comment : 클라이언트의 요청이 서버의 상태와 충돌이 발생한 경우 (HTTP STATUS 409)
 */
public class ConflictException extends BootException {

    public ConflictException() {
        super(HttpStatus.CONFLICT);
    }

    public ConflictException(HttpStatus code) {
        super(code);
    }

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

    public ConflictException(Class<?> className) {
        super(HttpStatus.CONFLICT, className);
    }

    public ConflictException(Class<?> className, String message) {
        super(HttpStatus.CONFLICT, className, message);
    }

    public ConflictException(Exception e) {
        super(e);
    }
}

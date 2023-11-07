package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @file name : BadRequestException.java
 * @project : spring-boot-init
 * @date : Dec 7, 2022
 * @author : ckr
 * @history:
 * @program comment : 클라이언트의 요청이 올바르지 않은 경우 (HTTP STATUS 400)
 */
public class BadRequestException extends BootException {


    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(HttpStatus code) {
        super(code);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(Class<?> className) {
        super(HttpStatus.BAD_REQUEST, className);
    }

    public BadRequestException(Class<?> className, String message) {
        super(HttpStatus.BAD_REQUEST, className, message);
    }

    public BadRequestException(Exception e) {
        super(e);
    }
}

package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @file name : NotFoundException.java
 * @project : spring-boot-init
 * @date : Dec 7, 2022
 * @author : ckr
 * @history:
 * @program comment : 클라이언트가 요청한 자원이 서버에 존재하지 않는 경우 (HTTP STATUS 404)
 */
public class NotFoundException extends BootException {


    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(HttpStatus code) {
        super(code);
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(Class<?> className) {
        super(HttpStatus.NOT_FOUND, className);
    }

    public NotFoundException(Class<?> className, String message) {
        super(HttpStatus.NOT_FOUND, className, message);
    }

    public NotFoundException(Exception e) {
        super(e);
    }
}

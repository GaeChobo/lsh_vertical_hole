package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @file name : ForbiddenException.java
 * @project : spring-boot-init
 * @date : Dec 7, 2022
 * @author : ckr
 * @history:
 * @program comment : 클라이언트가 인증은 되었으나 적절한 권한이 없는 경우 (HTTP STATUS 403)
 */
public class ForbiddenException extends BootException {


    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(HttpStatus code) {
        super(code);
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public ForbiddenException(Class<?> className) {
        super(HttpStatus.FORBIDDEN, className);
    }

    public ForbiddenException(Class<?> className, String message) {
        super(HttpStatus.FORBIDDEN, className, message);
    }

    public ForbiddenException(Exception e) {
        super(e);
    }
}

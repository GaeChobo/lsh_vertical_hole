//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

public class IOException extends BootException {
    private static final long serialVersionUID = -6487620777759955224L;

    public IOException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public IOException(HttpStatus code) {
        super(code);
    }

    public IOException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public IOException(Class<?> className, StackTraceElement... ste) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, className, ste);
    }

    public IOException(Class<?> className, String message, StackTraceElement... ste) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, className, message, ste);
    }

    public IOException(Exception e) {
        super(e);
    }
}

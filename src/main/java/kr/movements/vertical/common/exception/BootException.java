//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package kr.movements.vertical.common.exception;

import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BootException extends RuntimeException {
    private static final long serialVersionUID = -4696840207094187339L;
    private final HttpStatus status;

    public BootException(HttpStatus code) {
        super(code.getReasonPhrase());
        this.status = code;
    }

    public BootException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BootException(Class<?> className, int... line) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        StackTraceElement[] trace = new StackTraceElement[]{new StackTraceElement(className.toString(), "", "", line.length > 1 ? line[0] : -1)};
        this.setStackTrace(trace);
    }

    public BootException(HttpStatus code, String message) {
        super(message);
        this.status = code;
    }

    public BootException(Class<?> className, StackTraceElement... ste) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, className, ste);
    }

    public BootException(HttpStatus code, Class<?> className, StackTraceElement... ste) {
        this(code);
        StackTraceElement[] trace = new StackTraceElement[]{new StackTraceElement(className.toString(), "", "", ste[0] != null ? ste[0].getLineNumber() : -1)};
        this.setStackTrace(trace);
    }

    public BootException(Class<?> className, String message, StackTraceElement... ste) {
        this(message);
        StackTraceElement[] trace = new StackTraceElement[]{new StackTraceElement(className.toString(), "", "", ste[0] != null ? ste[0].getLineNumber() : -1)};
        this.setStackTrace(trace);
    }

    public BootException(HttpStatus code, Class<?> className, String message, StackTraceElement... ste) {
        this(code, message);
        StackTraceElement[] trace = new StackTraceElement[]{new StackTraceElement(className.toString(), "", "", ste[0] != null ? ste[0].getLineNumber() : -1)};
        this.setStackTrace(trace);
    }

    public BootException(Exception e) {
        this(e.getMessage());
        this.setStackTrace(e.getStackTrace());
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getStrTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

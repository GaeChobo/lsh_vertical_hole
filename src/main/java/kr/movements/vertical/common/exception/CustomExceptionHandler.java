package kr.movements.vertical.common.exception;

import kr.movements.vertical.dto.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @file name : CustomExceptionHandler.java
 * @project : spring-boot-init
 * @date : Feb 20, 2023
 * @author : "ckr"
 * @history:
 * @program comment : 프로젝트 내 Exception 처리
 */

@Slf4j
@RestControllerAdvice(basePackages = "kr.movements.vertical")
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Payload<String> defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String message = "\n=*=*=*=    Exception     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, request.getServletPath(), e.getMessage(), response);
    }
    /*
	@ExceptionHandler(Exception.class)
	public Payload<String> defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		String message = "\n=*=*=*=    Exception     =*=*=*=\n";
		log.error(message + e.getMessage(), e);
		return new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다. 관리자에게 문의하세요.", request.getServletPath(), response);
	}

     */

	@ExceptionHandler(IOException.class)
	public Payload<String> IOExceptionHandler(HttpServletRequest request, HttpServletResponse response, IOException e) {
		String message = "\n=*=*=*=    IO exception     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
		return new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다. 관리자에게 문의하세요.", request.getServletPath(), response);
	}

    @ExceptionHandler(NullPointerException.class)
    public Payload<String> NullExceptionHandler(HttpServletRequest request, HttpServletResponse response, NullPointerException e) {
        String message = "\n=*=*=*=     NullPointerException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다. 관리자에게 문의하세요.", request.getServletPath(), response);
    }

    @ExceptionHandler(BootException.class)
    public Payload<String> BootExceptionHandler(HttpServletRequest request, HttpServletResponse response, BootException e) {
        String message = "\n=*=*=*=    Boot exception     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, request.getServletPath(), e.getMessage(), response);
    }

    /**
     * @method name : DuplicateKeyExceptionHandler
     * @date : Jan 5, 2023
     * @author : "ckr"
     * @history :
     * @method comment : PostgreSQL 관련 exception handler
     */
//    @ExceptionHandler(PSQLException.class)
//    public Payload<String> PSQLExceptionHandler(HttpServletRequest request, HttpServletResponse response, PSQLException e) {
//        String message = "\n=*=*=*=     PSQLException     =*=*=*=\n";
//        log.error(message + e);
//        return new Payload<String>(HttpStatus.BAD_REQUEST, e.getMessage(), request.getServletPath(), response);
//    }

    /**
     * @method name : MethodArgumentTypeMismatchExceptionHandler
     * @date : Jan 5, 2023
     * @author : "ckr"
     * @history :
     * @method comment : method param 에 지정된 타입과 다른 타입의 param이 들어와서 포맷 변환에 실패했을때
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Payload<String> MethodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentTypeMismatchException e) {
        String message = "\n=*=*=*=     MethodArgumentTypeMismatchException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);
        return new Payload<>(HttpStatus.BAD_REQUEST, request.getServletPath(), "매개변수의 타입이 올바르지 않습니다. (" + e.getCause() + ")", response);
    }

//    /**
//     * @method name : ConstraintViolationExceptionHandler
//     * @date : Jan 5, 2023
//     * @author : "ckr"
//     * @history :
//     * @method comment : method param 혹은 return 값의 문제가 있을 경우
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public Payload<String> ConstraintViolationExceptionHandler(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException e) {
//        String message = "\n=*=*=*=     ConstraintViolationException     =*=*=*=\n";
//        log.error(message + e.getMessage(), e);
//
//        return new Payload<String>(HttpStatus.BAD_REQUEST, e.getMessage(), request.getServletPath(), response);
//    }

    /**
     * @method name : MethodArgumentNotValidExceptionHandler
     * @date : Jan 5, 2023
     * @author : "ckr"
     * @history :
     * @method comment : @RequestBody 데이터 바인딩중 유효성 검사에 통과하지 못한 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Payload<String> MethodArgumentNotValidExceptionHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        String message = "\n=*=*=*=     MethodArgumentNotValidException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);

        return new Payload<>(HttpStatus.BAD_REQUEST, request.getServletPath(), e.getBindingResult().getFieldError().getDefaultMessage(), response);
    }

	@ExceptionHandler(BadRequestException.class)
	public Payload<String> BadRequestExceptionHandler(HttpServletRequest request, HttpServletResponse response, BadRequestException e) {
		String message = "\n=*=*=*=    BadRequestException     =*=*=*=\n";
		log.error(message + e.getMessage(), e);

		return new Payload<>(HttpStatus.BAD_REQUEST, request.getServletPath(), e.getMessage(), response);
	}

    /**
     * @method name : ForbiddenExceptionHandler
     * @date : Dec 7, 2022
     * @author : "ckr"
     * @history :
     * @method comment : 클라이언트가 특정 작업에 대한 권한이 없어서 진행할 수 없는 경우
     */
    @ExceptionHandler(ForbiddenException.class)
    public Payload<String> ForbiddenExceptionHandler(HttpServletRequest request, HttpServletResponse response, ForbiddenException e) {
        String message = "\n=*=*=*=    ForbiddenException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);

        return new Payload<>(HttpStatus.FORBIDDEN, request.getServletPath(), e.getMessage(), response);
    }

    /**
     * @method name : UnauthorizedExceptionHandler
     * @date : Dec 7, 2022
     * @author : "ckr"
     * @history :
     * @method comment : 클라이언트의 권한이 유효하지 않은 경우
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Payload<String> UnauthorizedExceptionHandler(HttpServletRequest request, HttpServletResponse response, UnauthorizedException e) {
        String message = "\n=*=*=*=     UnauthorizedException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);

        return new Payload<>(HttpStatus.UNAUTHORIZED, request.getServletPath(), e.getMessage(), response);
    }

    /**
     * @method name : NotFoundExceptionHandler
     * @date : Dec 7, 2022
     * @author : "ckr"
     * @history :
     * @method comment : 클라이언트가 요청한 자원이 서버에 존재하지 않는 경우
     */
    @ExceptionHandler(NotFoundException.class)
    public Payload<String> NotFoundExceptionHandler(HttpServletRequest request, HttpServletResponse response, NotFoundException e) {
        String message = "\n=*=*=*=     NotFoundException     =*=*=*=\n";
        log.error(message + e.getMessage(), e);

        return new Payload<>(HttpStatus.NOT_FOUND, request.getServletPath(), e.getMessage(), response);
    }
}
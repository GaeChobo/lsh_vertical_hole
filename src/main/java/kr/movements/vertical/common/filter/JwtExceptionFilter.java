package kr.movements.vertical.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.movements.vertical.common.exception.UnauthorizedException;
import kr.movements.vertical.dto.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName : kr.movements.smv2.common.filter
 * fileName    : ExceptionHandlerFilter
 * author      : ckr
 * date        : 2023/05/07
 * description :
 */
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            setErrorResponse(request, response, HttpStatus.UNAUTHORIZED, e);
        }
    }

    private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, Throwable ex){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(new Payload<>(httpStatus, request.getServletPath(), ex.getMessage(), response)));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

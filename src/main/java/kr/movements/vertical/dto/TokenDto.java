package kr.movements.vertical.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDto {

    private final Long userId;
    private final String accessToken;

    public TokenDto(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }
}
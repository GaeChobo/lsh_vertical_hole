package kr.movements.vertical.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@ApiModel("현장 - 멤버 맵핑 등록 dto")
@Getter
@Builder
public class CompletionFixDto {

    private LocalDate minCreateDate;
    private LocalDate maxCreateDate;

    @QueryProjection
    public CompletionFixDto(LocalDate minCreateDate, LocalDate maxCreateDate) {

        this.minCreateDate = minCreateDate;
        this.maxCreateDate = maxCreateDate;
    }
}

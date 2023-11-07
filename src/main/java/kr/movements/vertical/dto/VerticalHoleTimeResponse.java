package kr.movements.vertical.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerticalHoleTimeResponse {

    private VerticalHoleGraphResponse verticalHoleGraphResponse;
    private List<AlertResponse> alertResponses;
}

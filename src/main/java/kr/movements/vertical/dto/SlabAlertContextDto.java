package kr.movements.vertical.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlabAlertContextDto {
    private List<String> slabAlertContextList;
}
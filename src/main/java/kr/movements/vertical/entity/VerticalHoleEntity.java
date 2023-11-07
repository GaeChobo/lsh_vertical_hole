package kr.movements.vertical.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "VERTICAL_HOLE")
@Entity
public class VerticalHoleEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="area_id", nullable = false)
    private AreaEntity area;

    @Column(length = 45)
    private String verticalHoleName;

    @JsonFormat(timezone = "Asia/Seoul")
    @Column
    private LocalDate verticalHoleStartDate;

    @JsonFormat(timezone = "Asia/Seoul")
    @Column
    private LocalDate verticalHoleEndDate;

    @Column
    private Long drawingFileId;

    @Column
    private Long modelFileId;

    @Column
    private Boolean verticalHoleRider;

    public void verticalHoleDrawingFileUpdate(Long fileId) {

        this.drawingFileId = fileId;
    }

    public void verticalHoleModelFileUpdate(Long fileId, Boolean verticalHoleRider) {
        this.modelFileId = fileId;
        this.verticalHoleRider = verticalHoleRider;
    }

    public void verticalScheduleUpdate(LocalDate verticalHoleStartDate, LocalDate verticalHoleEndDate) {
        this.verticalHoleStartDate = verticalHoleStartDate;
        this.verticalHoleEndDate = verticalHoleEndDate;
    }
}

package ru.kpfu.itis.bagautdinov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeworkInfoDto {
    private boolean isChecked;
    private boolean isPassed;
    private Integer score;
}

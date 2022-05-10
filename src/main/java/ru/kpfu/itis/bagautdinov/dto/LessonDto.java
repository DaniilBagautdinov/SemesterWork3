package ru.kpfu.itis.bagautdinov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDto {
    private Long id;
    @NotBlank(message = "Обязательно для ввода")
    @Size(min = 10, message = "Должно быть больше {min} символов")
    @Size(max = 255, message = "Должно быть меньше {max} символов")
    private String title;
    @NotBlank(message = "Обязательно для ввода")
    @Size(min = 10, message = "Должно быть больше {min} символов")
    @Size(max = 50, message = "Должно быть меньше {max} символов")
    private String description;
}

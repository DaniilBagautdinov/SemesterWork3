package ru.kpfu.itis.bagautdinov.dto;

import ru.kpfu.itis.bagautdinov.models.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private FileInfo avatar;
    private String registrationDate;
}

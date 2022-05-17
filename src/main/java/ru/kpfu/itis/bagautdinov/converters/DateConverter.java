package ru.kpfu.itis.bagautdinov.converters;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
@Configuration
public class DateConverter implements Converter<Date, String> {

    @Override
    public String convert(@NotNull Date source) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(source);
    }
}

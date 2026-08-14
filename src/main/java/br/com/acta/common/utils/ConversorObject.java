package br.com.acta.common.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ConversorObject{
    public static BigDecimal toBigDecimal(Object object) {
        return new BigDecimal(object.toString());
    }

    public static LocalDate toLocalDate(Object object){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(object.toString(), formatter);
    }

    public static <T extends Enum<T>> T toEnum(Object valor, Class<T> enumClass) {
        return Enum.valueOf(enumClass, valor.toString().toUpperCase());
    }
}

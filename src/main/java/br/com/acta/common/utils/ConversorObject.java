package br.com.acta.common.utils;

import br.com.acta.common.handler.exception.InvalidRequestException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class ConversorObject {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static BigDecimal toBigDecimal(Object object) throws NumberFormatException {
        return new BigDecimal(object.toString());
    }

    public static LocalDate toLocalDate(Object object, boolean ehPassado) throws DateTimeParseException {
        String dataString = object.toString();

        if (!dataString.matches("\\d{4}-\\d{2}-\\d{2}"))
            throw new DateTimeParseException("A data deve estar no formato yyyy-MM-dd", dataString, 0);
        LocalDate data = LocalDate.parse(dataString, DATE_TIME_FORMATTER);

        if (ehPassado && data.isAfter(LocalDate.now()))
            throw new InvalidRequestException("A data deve ser presente ou passada");

        if (!ehPassado && data.isBefore(LocalDate.now()))
            throw new InvalidRequestException("A data deve ser presente ou futura");

        return data;
    }

    public static LocalDate toLocalDate(Object object) throws DateTimeParseException {
        String dataString = object.toString();
        if (!dataString.matches("\\d{4}-\\d{2}-\\d{2}"))
            throw new DateTimeParseException("A data deve estar no formato yyyy-MM-dd", dataString, 0);

        return LocalDate.parse(dataString, DATE_TIME_FORMATTER);
    }


    public static <T extends Enum<T>> T toEnum(Object valor, Class<T> enumClass) throws IllegalArgumentException {
        return Enum.valueOf(enumClass, valor.toString().toUpperCase());
    }
}

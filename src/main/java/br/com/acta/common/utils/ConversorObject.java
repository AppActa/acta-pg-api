package br.com.acta.common.utils;

import br.com.acta.common.handler.exception.InvalidRequestException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class ConversorObject {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static BigDecimal toBigDecimal(Object object) {
        try {
            return new BigDecimal(object.toString());
        } catch (NumberFormatException nfe) {
            throw new InvalidRequestException("O valor informado é inválido");
        }
    }

    public static LocalDate toLocalDate(Object object, boolean ehPassado) {
        String dataString = object.toString();

        if (!dataString.matches("\\d{4}-\\d{2}-\\d{2}"))
            throw new InvalidRequestException("A data deve estar no formato yyyy-MM-dd");

        try {
            LocalDate data = LocalDate.parse(dataString, DATE_TIME_FORMATTER);

            if (ehPassado && data.isAfter(LocalDate.now()))
                throw new InvalidRequestException("A data deve ser presente ou passada");

            if (!ehPassado && data.isBefore(LocalDate.now()))
                throw new InvalidRequestException("A data deve ser presente ou futura");

            return data;
        } catch (DateTimeParseException dtpe){
            throw new InvalidRequestException("A data deve estar no formato yyyy-MM-dd");
        }
    }

    public static LocalDate toLocalDate(Object object) {
        String dataString = object.toString();
        if (!dataString.matches("\\d{4}-\\d{2}-\\d{2}"))
            throw new InvalidRequestException("A data deve estar no formato yyyy-MM-dd");

        return LocalDate.parse(dataString, DATE_TIME_FORMATTER);
    }

    public static Long toLong(Object object) {
        String valor = object.toString();
        if (!valor.matches("\\d+")) throw new InvalidRequestException("O valor deve ser um número");

        long valorLong = Long.parseLong(valor);
        if (valorLong <= 0) throw new InvalidRequestException("O valor deve ser maior que 0");

        return valorLong;
    }

    public static <T extends Enum<T>> T toEnum(Object valor, Class<T> enumClass) throws IllegalArgumentException {
        try {
            return Enum.valueOf(enumClass, valor.toString().toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new InvalidRequestException("O valor informado é inválido");
        }
    }
}

package br.com.luizalabs.wishlist.products.core.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Daniel Santos
 * {@link FormatterUtil} classe útil que por motivação e finalidade múltiplas operações envolvendo formatações retornando
 * o conteúdo em {@link String}
 * @since 15/11/2021
 */
public class FormatterUtil {

    private static final String VALIDATION_MESSAGE = "Parameter referring to {DATE}, is invalid and/or non-existent.";

    private FormatterUtil() {
    }

    public static String formatterLocalDateTimeBy(LocalDateTime data) {
        return formatterLocalDateTimeBy(data, "dd/MM/yyyy HH:mm:ss");
    }

    public static String formatterLocalDateTimeBy(LocalDateTime data, String strFormato) {
        if (Objects.isNull(data))
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        return data.format(DateTimeFormatter.ofPattern(strFormato));
    }
}

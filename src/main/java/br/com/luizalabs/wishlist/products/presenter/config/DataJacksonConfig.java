package br.com.luizalabs.wishlist.products.presenter.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Configuration
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class DataJacksonConfig {

    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        configurarVisibilidadeDasPropriedades(mapper);
        return mapper;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat("dd/MM/yyyy HH:mm:ss");
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        };
    }

    private void configurarVisibilidadeDasPropriedades(ObjectMapper mapper) {
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.DEFAULT)
                .withSetterVisibility(JsonAutoDetect.Visibility.DEFAULT)
                .withCreatorVisibility(JsonAutoDetect.Visibility.DEFAULT));
    }

    private void configurarParsersDeData(ObjectMapper mapper) {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}

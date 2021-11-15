package br.com.luizalabs.wishlist.products.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@Slf4j
@Document(collection = "wishlist")
public class Wishlist implements IGenerateCreatedDate {

    @Id
    private UUID id;

    @Field(name = "client_id")
    private UUID clientId;

    @Field(name = "title")
    private String title;

    @Field(name = "dt_created")
    private LocalDateTime dtCreated;

    @Field(name = "itens")
    private List<ItemWishlist> itemWishlist;

//    @Transient
//    private TypeStatusSession typeStatusSession;

    public void generateDtCreated() {
        this.dtCreated = IGenerateCreatedDate.generateCreatedDt();
    }

    public Wishlist generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

//    public Pauta beginSession(Instant fim) {
//        session = Session.start(fim);
//        log.info("Session: to open session {} with the date {}.", FormatterUtil.formatterLocalDateTimeFrom(session.getEnd()), FormatterUtil.formatterLocalDateTimeBy(session.getDtCreated()));
//        generateTypeStatusSession();
//        return this;
//    }

//    public Wishlist product(Vote vote) {
//        session.addVote(vote);
//        return this;
//    }

    @Override
    public String toString() {
        var mapper = new ObjectMapper();
        var jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}

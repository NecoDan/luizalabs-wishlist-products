package br.com.luizalabs.wishlist.products.presenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"br.com.luizalabs.wishlist.products.data.db.jpa.repositories"})
public class DBConfig {

}

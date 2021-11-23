package br.com.luizalabs.wishlist.products.events;

import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import org.springframework.context.ApplicationEvent;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public class WishlistCreatedEvent extends ApplicationEvent {
    public WishlistCreatedEvent(Wishlist source) {
        super(source);
    }
}

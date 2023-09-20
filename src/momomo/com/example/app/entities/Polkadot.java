package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.Time;
import momomo.com.db.entities.$EntityIdUUID;
import momomo.com.example.app.Crypto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Simple sample entity
 *
 * @author Joseph S.
 */
@Entity @Table(name = "polkadot") public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Polkadot extends $EntityIdUUID {
    
    private Timestamp time;
    private double    usd; // Represents the price in usd
    
    /**
     * Represents the service to be used when perfoming table operations on the Polkadot table as to separate the
     * declaration of the entity from logic applied to it in order to keep the entity Polkadot clean.
     * 
     * Note! We extend {@link momomo.com.example.app.Crypto.CryptoService} here!
     */
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Polkadot> { private Service() {}
        
        /////////////////////////////////////////////////////////////////////
        
        public Polkadot insert(Timestamp time, double usd) {
            return requireTransaction(() -> super.save( create().setTime(time).setUsd(usd) ));
        }
        
        /////////////////////////////////////////////////////////////////////
        
        public void populate(int multiply) {
            // Multiple transactions each started inside the insert method
            insert(Time.stamp(), multiply * 1);
            insert(Time.stamp(), multiply * 2);
    
            // Two at once, insert call will just continue using this created transaction
            requireTransaction(() -> {
                insert(Time.stamp(), multiply * 3);
                insert(Time.stamp(), multiply * 4);
            });
        }
        
        /////////////////////////////////////////////////////////////////////
    }
}

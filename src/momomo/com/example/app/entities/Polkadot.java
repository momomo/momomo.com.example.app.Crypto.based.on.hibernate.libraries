package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.Time;
import momomo.com.db.entities.$EntityIdLong;
import momomo.com.example.app.Crypto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Simple sample entity
 *
 * @author Joseph S.
 */
@Entity @Table(name = Polkadot.Cons.table) public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Polkadot extends $EntityIdLong {
    
    private Timestamp time;
    private double    usd; // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We separate constants and transient things from the main entity to keep the entity Polkadot clean
     */
    public static final class Cons {
        public static final String table = "polkadot";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Polkadot table as to separate the
     * declaration of the entity from logic applied to it in order to keep the entity Polkadot clean.
     * <p>
     * Note! We extend {@link momomo.com.example.app.Crypto.CryptoService} here!
     */
    public static final Service S = new Service();
    
    public static final class Service extends Crypto.CryptoService<Polkadot> { private Service() {}
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Our insert from {@link momomo.com.example.app.entities.Bitcoin.Service#insert(java.sql.Timestamp, double)} example rewritten in this more compact way
         */
        public Polkadot insert(Timestamp time, double usd) {
            return requireTransaction(() -> save( new Polkadot().setTime(time).setUsd(usd) ));
        }
        
        /////////////////////////////////////////////////////////////////////
        
        public void populate(int mul) {
            // Multiple transactions each started inside the insert method
            insert(Time.stamp(), mul * 1001);
            insert(Time.stamp(), mul * 1002);
            insert(Time.stamp(), mul * 1003);
            
            // Two at once, insert call will just continue using this created transaction
            requireTransaction(() -> {
                insert(Time.stamp(), mul * 1004);
                insert(Time.stamp(), mul * 1005);
            });
        }
        
        /////////////////////////////////////////////////////////////////////
    }
}

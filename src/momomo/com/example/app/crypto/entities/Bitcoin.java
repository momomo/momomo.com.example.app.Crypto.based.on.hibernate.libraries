package momomo.com.example.app.crypto.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.db.entities.$Entity;
import momomo.com.example.app.crypto.Crypto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Simple sample entity 
 * 
 * @author Joseph S.
 */
@Entity
@Table(name = Bitcoin.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Bitcoin implements $Entity {
    
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We separate constants and transient things from the main entity to keep the entity Bitcoin clean
     */
    public static final class Cons {        
        public static final String table = "bitcoin";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Bitcoin table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Bitcoin clean.  
     */
    public static final Service S = new Service(); public static final class Service {
        
        /**
         * Also 
         * @see Etherum.Service#insert(java.sql.Timestamp, double)
         * @see Polkadot.Service#insert(java.sql.Timestamp, double)
         */
        public Bitcoin insert(Timestamp time, double usd) {
            // This 'very very expensive' creation need not to be inside the transaction (just for demo)
            Bitcoin entity = new Bitcoin()
                .setId(UUID.randomUUID())
                .setTime(time)
                .setUsd(usd);
    
            // Now, require the transaction and execute save within, also return the saved entity. 
            return Crypto.repository.requireTransaction((tx) -> {
                return save(entity);
            });
            
            // We should now 100% have a new row in the table bitcoin!
        }
        
        /**
         * Save your entity here anyway you would normally do!
         * 
         * Note, if you extend our base app service {@link momomo.com.example.app.crypto.Crypto.CryptoService} then we would not need to use Crypto.repository.save() but just save()!
         * 
         * We are just showing the minimal viable here but please see the entities 
         * {@link momomo.com.example.app.crypto.entities.Polkadot.Service} and {@link momomo.com.example.app.crypto.entities.Stellar.Service}
         * Which do extend {@link momomo.com.example.app.crypto.Crypto.CryptoService}.   
         * 
         */
        private Bitcoin save(Bitcoin entity) {
            return Crypto.repository.save(entity);  // OR Crypto.repository.session().save(entity); OR Crypto.repository.session().saveOrUpdate(entity);
        }
    }
    
}

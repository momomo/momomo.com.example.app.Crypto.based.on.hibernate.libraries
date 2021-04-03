package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.Randoms;
import momomo.com.Time;
import momomo.com.db.entities.$Entity;
import momomo.com.example.app.Crypto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Simple sample entity.  
 * 
 * @author Joseph S.
 */
@Entity
@Table(name = Bitcoin.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Bitcoin implements $Entity {
    
    @Id
    private Long      id;
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
    public static final Service S = new Service(); public static final class Service { private Service(){}
        
        /////////////////////////////////////////////////////////////////////
    
        /**
         * Also 
         * @see Etherum.Service#insert(java.sql.Timestamp, double)
         * @see Polkadot.Service#insert(java.sql.Timestamp, double)
         */
        public Bitcoin insert(Timestamp time, double usd) {
            // This 'very very expensive' creation need not to be inside the transaction (just for demo)
            Bitcoin entity = new Bitcoin()
                .setId(Randoms.Long())
                .setTime(time)
                .setUsd(usd);
    
            return save(entity);
        }
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Save your entity here anyway you would normally do!
         * 
         * Note, if you extend our base app service {@link momomo.com.example.app.Crypto.CryptoService} then we would not need to use Crypto.repository.save() but just save()!
         * 
         * We are just showing the minimal viable here but please see the entities 
         * {@link momomo.com.example.app.entities.Polkadot.Service} and {@link momomo.com.example.app.entities.Stellar.Service}
         * Which do extend {@link momomo.com.example.app.Crypto.CryptoService}.   
         * 
         */
        private Bitcoin save(Bitcoin entity) {
            return Crypto.repository.save(entity);  // OR Crypto.repository.session().save(entity); OR Crypto.repository.session().saveOrUpdate(entity);
        }
        
        /////////////////////////////////////////////////////////////////////
        
        public void populate(int mul) {
            // All in one transaction 
            Crypto.repository.requireTransaction(() -> {
                insert(Time.stamp(), mul * 100001);
                insert(Time.stamp(), mul * 100002);
                insert(Time.stamp(), mul * 100003);
                insert(Time.stamp(), mul * 100004);
                insert(Time.stamp(), mul * 100005);
            });
    
            // We should now 100% have new rows in the table bitcoin!
        }
        
        /////////////////////////////////////////////////////////////////////
    }
    
}

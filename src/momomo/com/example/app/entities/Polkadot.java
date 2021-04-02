package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.Time;
import momomo.com.db.entities.$EntityIdUUID;
import momomo.com.example.app.Crypto;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

/**
 * Simple sample entity 
 * 
 * @author Joseph S.
 */
@Entity
@Table(name = Polkadot.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Polkadot extends $EntityIdUUID {
    
    private Timestamp time;
    private double    usd;     // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We separate constants and transient things from the main entity to keep the entity Polkadot clean
     */
    public static final class Cons {
        public static final String table = "polkadot", time = "time", usd = "usd";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Polkadot table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Polkadot clean.  
     * 
     * Note! We extend {@link momomo.com.example.app.Crypto.CryptoService} here!
     */
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Polkadot> { private Service(){}
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Our insert from previous example rewritten in this 'nicer' way
         * 
         * Also 
         * @see Bitcoin.Service#insert(java.sql.Timestamp, double) 
         * @see Etherum.Service#insert(java.sql.Timestamp, double) 
         */
        public Polkadot insert(Timestamp time, double usd) {
             // Note, we do not need to access Crypto.R anymore inside this Service
            
            return requireTransaction(()-> {
                Polkadot entity = new Polkadot()
                    .setTime(time)
                    .setUsd(usd)
                ;
                
                return super.save(entity);   // super not required, but added for clarity   
            });
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        public void populate(int mul) {
            // Multiple transactions each started inside the insert method
            insert(Time.stamp(), mul * 1001);
            insert(Time.stamp(), mul * 1002);
            insert(Time.stamp(), mul * 1003);
            
            // Two at once, insert call will just continue using this created transaction
            requireTransaction(()-> {
                insert(Time.stamp(), mul * 1004);
                insert(Time.stamp(), mul * 1005);
            });
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
    }
}

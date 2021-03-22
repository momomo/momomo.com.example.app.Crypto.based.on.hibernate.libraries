package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
    
    @Column(name = Cons.time) 
    private Timestamp time;
    @Column(name = Cons.usd ) 
    private double    usd;     // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We separate constants and transient things from the main entity to keep the entity Polkadot clean
     */
    public static final class Cons {
        public static final String table = "polkadot";
        public static final String time  = "time";
        public static final String usd   = "usd";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Polkadot table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Polkadot clean.  
     */
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Polkadot> { private Service(){}

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
                // And save method and a bunch of other methods are provided for us, they delegate through the repository
                // super not required, but added for clarity
                Polkadot entity = new Polkadot()
                    .setTime(time)
                    .setUsd(usd)
                ;
                
                return super.save(entity);   
            });
        }
        
        /**
         * Return all the historic data within polkadot table 
         */
        public List<Polkadot> historic() {
            return supportTransaction(()-> {
                return super.list();
            });
        }
        
        public List<Polkadot> range(Timestamp from, Timestamp to) {
            return supportTransaction(()-> {
                return list(criteria()
                    .add( Restrictions.ge(Cons.time, from) )
                    .add( Restrictions.le(Cons.time, to  ) )
                );
            });
        }
        
    }
}

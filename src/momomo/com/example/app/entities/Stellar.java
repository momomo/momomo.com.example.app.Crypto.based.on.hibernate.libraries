package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Entity
@Table(name = Stellar.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Stellar extends $EntityIdUUID {
    
    private Timestamp time;
    private double    usd;     // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We separate constants and transient things from the main entity to keep the entity Stellar clean
     */
    public static final class Cons {
        public static final String table = "stellar";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Stellar table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Stellar clean.  
     */
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Stellar> { private Service(){}
        /**
         * Our insert from previous example rewritten in this 'nicer' way
         * 
         * Also 
         * @see momomo.com.example.app.entities.Bitcoin.Service#insert(java.sql.Timestamp, double) 
         * @see momomo.com.example.app.entities.Etherum.Service#insert(java.sql.Timestamp, double) 
         */
        public Stellar insert(Timestamp time, double usd) {
            return save( create().setTime(time).setUsd(usd) ); // create will create the entity for us provide a standard zero arg constructor on Stellar.java
        }
    }
}

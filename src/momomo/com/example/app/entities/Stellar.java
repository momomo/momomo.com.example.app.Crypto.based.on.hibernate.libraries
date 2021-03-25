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
    
    public static final class Cons {
        public static final String table = "stellar";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Stellar> { private Service(){}
        public Stellar insert(Timestamp time, double usd) {
            return save( create().setTime(time).setUsd(usd) ); 
        }
    }
}

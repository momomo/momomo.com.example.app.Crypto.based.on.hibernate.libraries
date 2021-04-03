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
import java.util.UUID;

/**
 * Simple sample entity.  
 * 
 * @author Joseph S.
 */
@Entity @Table(name = "bitcoin") public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Bitcoin implements $Entity {
    
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Bitcoin table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Bitcoin clean.  
     */
    public static final Service S = new Service(); public static final class Service { private Service(){}
        
        /////////////////////////////////////////////////////////////////////
        
        public Bitcoin insert(Timestamp time, double usd) {
            // This 'very very expensive' creation need not to be inside the transaction (just for demo)
            Bitcoin entity = new Bitcoin().setId(Randoms.UUID()).setTime(time).setUsd(usd);
    
            return Crypto.repository.requireTransaction((tx)-> {
                return save(entity);
            });
        }
        
        private Bitcoin save(Bitcoin entity) {
            return Crypto.repository.save(entity);
        }
        
        /////////////////////////////////////////////////////////////////////
        
        public void populate(int multiply) {
            // Multiple transactions each started inside the insert method
            insert(Time.stamp(), multiply * 1);
            insert(Time.stamp(), multiply * 2);
            
            // Two at once, insert call will just continue using this created transaction
            Crypto.repository.requireTransaction(() -> {
                insert(Time.stamp(), multiply * 3);
                insert(Time.stamp(), multiply * 4);
            });
        }
        
        /////////////////////////////////////////////////////////////////////
    }
    
}

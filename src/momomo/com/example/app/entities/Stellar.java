package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.Time;
import momomo.com.db.$Transaction;
import momomo.com.db.entities.$EntityIdLong;
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
@Entity @Table(name = Stellar.Cons.table) public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Stellar extends $EntityIdLong {
    
    @Column(name = Cons.time) private Timestamp time;
    @Column(name = Cons.usd ) private double    usd;     // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class Cons {
        public static final String table = "stellar", time = "time", usd = "usd";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Polkadot table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Polkadot clean.  
     *
     * Note! We extend {@link momomo.com.example.app.Crypto.CryptoService} here!
     */
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Stellar> { private Service(){}
    
        /////////////////////////////////////////////////////////////////////
        
        public Stellar insert(Timestamp time, double usd) {
            return save( create().setTime(time).setUsd(usd) ); 
        }
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Return all the historic data within polkadot table 
         */
        public List<Stellar> historic() {
            return supportTransaction(()-> {
                return super.list();
            });
        }
        
        /////////////////////////////////////////////////////////////////////
        
        public List<Stellar> range(Timestamp from, Timestamp to) {
            return supportTransaction(()-> {
                return list(criteria()
                    .add( Restrictions.ge(Stellar.Cons.time, from) )
                    .add( Restrictions.le(Stellar.Cons.time, to  ) )
                );
            });
        }
        
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Bunch of complex and nested transaction.
         
         * Take a look at the comments within!
         */
        public void populate() {
            newTransaction((tx1) -> {
                insert(Time.stamp(), 11);
                insert(Time.stamp(), 12);
                
                // Start a new transaction within
                newTransaction(tx2 -> {
                    insert(Time.stamp(), -21);
                    insert(Time.stamp(), -22);
                    
                    // Another one
                    newTransaction(tx3 -> {
                        insert(Time.stamp(), 31);
                        insert(Time.stamp(), 32);
                    });
                    
                    // Continue on the previous last active one (same as tx2) 
                    requireTransaction(tx4 -> {
                        insert(Time.stamp(), -41);
                        insert(Time.stamp(), -42);
                    });
                    
                    // Neither -21 ... or -41 ... will get into db since that tx rolledback
                    tx2.rollback();
                    
                    // The same as tx1, no issues there. Should be in.   
                    requireTransaction(tx5 -> {
                        insert(Time.stamp(), 51);
                        insert(Time.stamp(), 52);
                        
                        // The same as tx1 and tx5, no issues there. Should enter db.    
                        requireTransaction(($Transaction tx6) -> {
                            insert(Time.stamp(), 61);
                            insert(Time.stamp(), 62);
                            
                            // New transaction, will be in    
                            newTransaction(($Transaction tx7) -> {
                                insert(Time.stamp(), 71);
                                insert(Time.stamp(), 72);
                            });
                            
                            // New transaction, won't be in since rolled back    
                            newTransaction(($Transaction tx8) -> {
                                insert(Time.stamp(), -81);
                                insert(Time.stamp(), -82);
                                
                                tx8.rollback();
                            });
                        });
                    });
                });
            });
        }
        
        /////////////////////////////////////////////////////////////////////
    }
}

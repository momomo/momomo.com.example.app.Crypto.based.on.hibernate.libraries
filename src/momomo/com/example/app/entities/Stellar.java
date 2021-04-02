package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.Time;
import momomo.com.db.$TransactionHibernate;
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
@Table(name = Stellar.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Stellar extends $EntityIdUUID {
    
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
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Bunch of complex and nested transaction! 
         * Take a look at the comments!
         */
        public void populate(int multiplier) {
            
            newTransaction((tx1) -> {
                insert(Time.stamp(), multiplier * 101);
                insert(Time.stamp(), multiplier * 102);
                insert(Time.stamp(), multiplier * 103);
                insert(Time.stamp(), multiplier * 104);
                insert(Time.stamp(), multiplier * 105);
                
                // Start a new transaction within
                newTransaction(tx2 -> {
                    insert(Time.stamp(), multiplier * 201);
                    insert(Time.stamp(), multiplier * 202);
                    insert(Time.stamp(), multiplier * 203);
                    insert(Time.stamp(), multiplier * 204);
                    insert(Time.stamp(), multiplier * 205);
                    
                    // Another one
                    newTransaction(tx3 -> {
                        insert(Time.stamp(), multiplier * 301);
                        insert(Time.stamp(), multiplier * 302);
                        insert(Time.stamp(), multiplier * 303);
                        insert(Time.stamp(), multiplier * 304);
                        insert(Time.stamp(), multiplier * 305);
                    });
                    
                    // Continue on the previous last active one (same as tx2) 
                    requireTransaction(tx4 -> {
                        insert(Time.stamp(), multiplier * 401);
                        insert(Time.stamp(), multiplier * 402);
                        insert(Time.stamp(), multiplier * 403);
                        insert(Time.stamp(), multiplier * 404);
                        insert(Time.stamp(), multiplier * 405);
                    });
                    
                    // Neither 201 ... or 401 ... will get into db since that tx rolledback
                    tx2.rollback();
                    
                    // The same as tx1, no issues there. Should be in.   
                    requireTransaction(tx5 -> {
                        insert(Time.stamp(), multiplier * 501);
                        insert(Time.stamp(), multiplier * 502);
                        insert(Time.stamp(), multiplier * 503);
                        insert(Time.stamp(), multiplier * 504);
                        insert(Time.stamp(), multiplier * 505);
                        
                        // The same as tx1 and tx5, no issues there. Should enter db.    
                        requireTransaction(($TransactionHibernate tx6) -> {
                            insert(Time.stamp(), multiplier * 601);
                            insert(Time.stamp(), multiplier * 602);
                            insert(Time.stamp(), multiplier * 603);
                            insert(Time.stamp(), multiplier * 604);
                            insert(Time.stamp(), multiplier * 605);
                            
                            // New transaction, will be in    
                            newTransaction(($TransactionHibernate tx7) -> {
                                insert(Time.stamp(), multiplier * 701);
                                insert(Time.stamp(), multiplier * 702);
                                insert(Time.stamp(), multiplier * 703);
                                insert(Time.stamp(), multiplier * 704);
                                insert(Time.stamp(), multiplier * 705);
                            });
                            
                            // New transaction, won't be in    
                            newTransaction(($TransactionHibernate tx8) -> {
                                insert(Time.stamp(), multiplier * 801);
                                insert(Time.stamp(), multiplier * 802);
                                insert(Time.stamp(), multiplier * 803);
                                insert(Time.stamp(), multiplier * 804);
                                insert(Time.stamp(), multiplier * 805);
                                
                                tx8.rollback();
                            });
                        });
                    });
                });
            });
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
    }
}

package momomo.com.example.app.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import momomo.com.db.$TransactionHibernate;
import momomo.com.db.$TransactionOptions;
import momomo.com.db.$TransactionOptionsHibernate;
import momomo.com.db.entities.$Entity;
import momomo.com.example.app.Crypto;
import org.hibernate.Session;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

/**
 * Simple sample entity 
 * 
 * @author Joseph S.
 */
@Entity
@Table(name = Etherum.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Etherum implements $Entity {
    
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We separate constants and transient things from the main entity to keep the entity Etherum clean
     */
    public static final class Cons {
        public static final String table = "etherum";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Represents the service to be used when perfoming table operations on the Etherum table as to separate the 
     * declaration of the entity from logic applied to it in order to keep the entity Etherum clean.  
     */
    public static final Service S = new Service(); public static final class Service { private Service(){}
        
        /**
         * Just a bunch of examples, nothing of it is executed
         * 
         * Also 
         * @see Bitcoin.Service#insert(java.sql.Timestamp, double)
         * @see Polkadot.Service#insert(java.sql.Timestamp, double)
         */
        public Etherum insert(Timestamp time, double usd) {  if ( true ) return null;
        
            Etherum entity = new Etherum()
                .setId(UUID.randomUUID())
                .setTime(time)
                .setUsd(usd)
            ;
            
            // Example a.
            Crypto.repository.requireTransaction(($TransactionHibernate transaction) -> {
                // Disable autocommit, so we commit when we want or not at all
                transaction.autocommit(false);
        
                save(entity);
                save(entity);
                save(entity);
                save(entity);
        
                transaction.commit();
            });
    
            // Example b.
            Crypto.repository.requireTransaction((tx) -> {
                tx.afterCommit(() -> {
                    // Send email perhaps when we exit the transaction after succesfully committing!
                    
                    // We have now inserted the value in our database successfully!
            
                    // We do not want to send the email unless we actually have 100% inserted the stuff so this is a handy method to use for that 
                });
        
                save(entity);
            });
    
            // Example c.
            String returns = Crypto.repository.requireTransaction(() -> {
                save(entity);
    
                return "we can return anything from the transactional lambda";
            });
    
            // Example d. 
            Etherum e = Crypto.repository.requireTransaction(() -> {
                return save(entity); // We repeat the return demo but by returning an entity
            });
    
            // Example e. 
            // Maybe we do not want to execute things inside the lambda block but desire more freedom? 
            $TransactionHibernate tx1 = Crypto.repository.requireTransaction();
            save(entity);
            save(entity);
            save(entity);
            save(entity);
            tx1.autocommit(false);
            tx1.afterCommit  (()-> {});
            tx1.afterRollback(()-> { /* A crime has been committed! Report error to the FBI! */ });
            tx1.rollback();
            tx1.commit();
    
            //////////////////////////////////////////////////
            //////////////////////////////////////////////////
            //////////////////////////////////////////////////
    
            // Example f. 
            Crypto.repository.newTransaction(() -> {
                // A new transaction is created! Not reusing an existing one if there is one!
            });
    
            // Example g.
            Crypto.repository.supportTransaction(() -> {
                // A read only transaction is created! Writing to the database is not possible, and will result in a terrible offense!
            });
    
            // Example h.
            Crypto.repository.newTransaction((tx) -> {
                save(entity);
        
                tx.rollback();
        
                save(entity);
    
                // note, now, the autocommit won't occur, since rollback will set commit to false as well as rolling back 
            });
    
            // Example i.
            Crypto.repository.requireTransaction(() -> {
                save(entity);
            }, false /** commit false**/ );
    
            // Example j. 
            try {
                Crypto.repository.requireTransaction(() -> {
                    throw new IOException();
                });
            } catch (IOException exception) {
                // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E)
            }
    
            // Example k. 
            try {
                File file = Crypto.repository.requireTransaction(() -> {
                    if ( false ) {
                        throw new IOException();
                    }
                    return new File("");
                });
            } catch (IOException exception) {
                // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E)
            }
    
            // Example l. 
            Session s1 = Crypto.repository.requireSession();
            Session s2 = Crypto.repository.newSession();
    
            // Example m.
            Crypto.repository.requireOptions()
                .propagation($TransactionOptions.Propagation.NEW)
                .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
                .timeout(1000)
                .create()
                .execute((tx)-> {
                    tx.autocommit(false);
                    tx.afterCommit(()-> {});
                    tx.afterRollback(()-> {});
            
                    // ... 
                })
            ;
    
            // Example n.  
            // Similar to example m. but without chaining  
            $TransactionOptionsHibernate options = Crypto.repository.requireOptions();
            // ... options.propagation(...)
            // ... options.create().execute(...)
    
            // Example o.
            $TransactionHibernate tx2 = Crypto.repository.requireOptions()
                .propagation($TransactionOptions.Propagation.NEW)
                .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
                .timeout(1000)
                .create()
            ;
    
            // Example p.
            Crypto.repository.requireOptions()
                .timeout(1000)
                .withConnection((java.sql.Connection connection) -> {
                    connection.setReadOnly(true);
                    connection.setCatalog("catalog");
                    connection.setTransactionIsolation(1);
                    connection.clearWarnings();
                    connection.createStatement();
                    connection.setTypeMap(new HashMap<>());
                    connection.setHoldability(1);
                    connection.setSavepoint();
                    connection.setSavepoint();
                    // ...
                })
                .create()
                .execute(tx -> {
                    tx.autocommit(false);
            
                    save(entity);
                    save(entity);
                    save(entity);
            
                    tx.commit();
                })
            ;
            
            return entity;
        }
        
        /**
         * Save your entity here anyway you normally do
         */
        private Etherum save(Etherum entity) {
            return entity; // No need to save, as this entire class is an example only
        }
    }
    
}

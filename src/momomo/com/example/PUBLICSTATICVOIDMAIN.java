package momomo.com.example;

import momomo.com.db.$Transaction;
import momomo.com.example.app.Crypto;
import momomo.com.example.app.entities.Bitcoin;
import momomo.com.example.app.entities.Polkadot;
import momomo.com.example.app.entities.Stellar;

/**
 * @author Joseph S.
 */
public class PUBLICSTATICVOIDMAIN {
    
    public static void main(String[] args) {
        Bitcoin.S.populate(1);
        Polkadot.S.populate(1);
        Stellar.S.populate(1);
    
        // We disable autocommit using false 
        {
            Crypto.repository.requireTransaction(tx-> {
                Bitcoin.S.populate(10);
            }, false /** disable autocommit **/ );
        }
    
        // We disable autocommit by call 
        {
            Crypto.repository.requireTransaction(tx-> {
                tx.autocommit(false);
            
                Bitcoin.S.populate(100);
            });
        }
    
        // We disable autocommit using false, and commit manually 
        {
            Crypto.repository.requireTransaction(tx-> {
                Bitcoin.S.populate(1000);
                
                tx.commit();
                
            }, false /** disable autocommit **/);
        }
    
        // We commit manually from inside the lambda
        {
            $Transaction tx = Crypto.repository.requireTransaction();
            Bitcoin.S.populate(10000);
        
            tx.commit();
        }
    
        // We commit manually when in 'free' mode
        {
            $Transaction tx = Crypto.repository.newTransaction();
            Bitcoin.S.populate(100000);
        
            tx.commit();
        }
    
        // We rollback from inside the lambda
        {
            Crypto.repository.requireTransaction(tx -> {
                Bitcoin.S.populate(1000000);
            
                tx.rollback();
            });
        }
    
        // We rollback from 'free' mode
        {
            $Transaction tx = Crypto.repository.requireTransaction();
            Bitcoin.S.populate(1000000);
            
            tx.rollback();
        }
    }
}

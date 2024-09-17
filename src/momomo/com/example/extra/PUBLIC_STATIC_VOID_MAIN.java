package momomo.com.example.extra;

import momomo.com.db.$Transaction;
import momomo.com.example.app.Crypto;
import momomo.com.example.app.entities.Bitcoin;
import momomo.com.example.app.entities.Polkadot;
import momomo.com.example.app.entities.Stellar;

/**
 * @author Joseph S.
 */
public class PUBLIC_STATIC_VOID_MAIN {
    
    public static void main(String[] args) {
        Bitcoin.S.populate(1);
        Polkadot.S.populate(1);
        Stellar.S.populate();
    
        // We disable autocommit using false, and commit manually 
        {
            Crypto.repository.requireTransaction(tx-> {
                Bitcoin.S.populate(1000);
                
                tx.commit();
                
            }, false /** disable autocommit **/);
        }
    
        // We rollback from inside the lambda
        {
            Crypto.repository.requireTransaction(tx -> {
                Bitcoin.S.populate(-10000);
            
                tx.rollback();
            });
        }
    
        // We rollback from 'free' mode
        {
            $Transaction tx = Crypto.repository.requireTransaction();
            Bitcoin.S.populate(-100000);
            
            tx.rollback();
        }
    }
}

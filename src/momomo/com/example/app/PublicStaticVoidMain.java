package momomo.com.example.app;

import momomo.com.Time;
import momomo.com.db.$TransactionHibernate;
import momomo.com.example.app.entities.Bitcoin;
import momomo.com.example.app.entities.Polkadot;
import momomo.com.example.app.entities.Stellar;

/**
 * @author Joseph S.
 */
public class PublicStaticVoidMain {
    
    public static void main(String[] args) {
        bitcoin(1);
        polkadot(1);
        stellar(1);
    
        {
            $TransactionHibernate tx = Crypto.repository.requireTransaction();
    
            bitcoin(10);
            polkadot(10);
    
            tx.rollback();      // We roll back!
        }
    }
    
    private static void bitcoin(int mul) {
        // Multiple transactions
        Bitcoin.S.insert(Time.stamp(), mul * 100001);
        Bitcoin.S.insert(Time.stamp(), mul * 100002);
        Bitcoin.S.insert(Time.stamp(), mul * 100003);
        Bitcoin.S.insert(Time.stamp(), mul * 100004);
        Bitcoin.S.insert(Time.stamp(), mul * 100005);
    }
    
    private static void polkadot(int mul) {
        // Multiple transactions
        Polkadot.S.insert(Time.stamp(), mul * 1001);
        Polkadot.S.insert(Time.stamp(), mul * 1002);
        Polkadot.S.insert(Time.stamp(), mul * 1003);
        Polkadot.S.insert(Time.stamp(), mul * 1004);
        Polkadot.S.insert(Time.stamp(), mul * 1005);
    }
    
    /**
     * Bunch of complex and nested transaction! 
     * Take a look at the comments!
     */
    private static void stellar(int mul) {
        // One transaction
        Crypto.repository.newTransaction((txA) -> {
            Stellar.S.insert(Time.stamp(), mul * 101);
            Stellar.S.insert(Time.stamp(), mul * 102);
            Stellar.S.insert(Time.stamp(), mul * 103);
            Stellar.S.insert(Time.stamp(), mul * 104);
            Stellar.S.insert(Time.stamp(), mul * 105);
    
            // Start a new transaction within
            Crypto.repository.newTransaction(txB -> {
                Stellar.S.insert(Time.stamp(), mul * 201);
                Stellar.S.insert(Time.stamp(), mul * 202);
                Stellar.S.insert(Time.stamp(), mul * 203);
                Stellar.S.insert(Time.stamp(), mul * 204);
                Stellar.S.insert(Time.stamp(), mul * 205);
    
                // Another one
                Crypto.repository.newTransaction(txC -> {
                    Stellar.S.insert(Time.stamp(), mul * 301);
                    Stellar.S.insert(Time.stamp(), mul * 302);
                    Stellar.S.insert(Time.stamp(), mul * 303);
                    Stellar.S.insert(Time.stamp(), mul * 304);
                    Stellar.S.insert(Time.stamp(), mul * 305);
                });
    
                // Continue on the previous last active one (same as txB) 
                Crypto.repository.requireTransaction(txD -> {
                    Stellar.S.insert(Time.stamp(), mul * 401);
                    Stellar.S.insert(Time.stamp(), mul * 402);
                    Stellar.S.insert(Time.stamp(), mul * 403);
                    Stellar.S.insert(Time.stamp(), mul * 404);
                    Stellar.S.insert(Time.stamp(), mul * 405);
                });
                
                // Neither 201 ... or 401 ... will get into db since that tx rolledback
                txB.rollback();
                
                // The same as txA, no issues there. Should be in.   
                Crypto.repository.requireTransaction(txE -> {
                    Stellar.S.insert(Time.stamp(), mul * 501);
                    Stellar.S.insert(Time.stamp(), mul * 502);
                    Stellar.S.insert(Time.stamp(), mul * 503);
                    Stellar.S.insert(Time.stamp(), mul * 504);
                    Stellar.S.insert(Time.stamp(), mul * 505);
    
                    // The same as txA and txE, no issues there. Should enter db.    
                    Crypto.repository.requireTransaction(($TransactionHibernate txF) -> {
                        Stellar.S.insert(Time.stamp(), mul * 601);
                        Stellar.S.insert(Time.stamp(), mul * 602);
                        Stellar.S.insert(Time.stamp(), mul * 603);
                        Stellar.S.insert(Time.stamp(), mul * 604);
                        Stellar.S.insert(Time.stamp(), mul * 605);
    
                        // New transaction, will be in    
                        Crypto.repository.newTransaction(($TransactionHibernate txG) -> {
                            Stellar.S.insert(Time.stamp(), mul * 701);
                            Stellar.S.insert(Time.stamp(), mul * 702);
                            Stellar.S.insert(Time.stamp(), mul * 703);
                            Stellar.S.insert(Time.stamp(), mul * 704);
                            Stellar.S.insert(Time.stamp(), mul * 705);
                        });
    
                        // New transaction, won't be in    
                        Crypto.repository.newTransaction(($TransactionHibernate txH) -> {
                            Stellar.S.insert(Time.stamp(), mul * 801);
                            Stellar.S.insert(Time.stamp(), mul * 802);
                            Stellar.S.insert(Time.stamp(), mul * 803);
                            Stellar.S.insert(Time.stamp(), mul * 804);
                            Stellar.S.insert(Time.stamp(), mul * 805);
                            
                            txH.rollback();
                        });
                    });
                });
            });
        });
    }
}

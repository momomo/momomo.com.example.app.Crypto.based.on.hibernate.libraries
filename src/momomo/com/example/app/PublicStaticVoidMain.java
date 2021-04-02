package momomo.com.example.app;

import momomo.com.db.$TransactionHibernate;
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
    
        {
            $TransactionHibernate tx = Crypto.TRANSACTIONAL.requireTransaction();
        
            Bitcoin.S.populate(10);
            Polkadot.S.populate(10);
        
            tx.rollback();      // We roll back!
        }
    }
    
}

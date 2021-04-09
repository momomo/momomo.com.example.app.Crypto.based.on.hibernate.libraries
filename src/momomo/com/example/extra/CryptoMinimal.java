package momomo.com.example.extra;

import momomo.com.db.$DatabasePostgres;
import momomo.com.db.$SessionConfig;
import momomo.com.db.$TransactionalHibernate;
import momomo.com.db.sessionmanager.$SessionManagerRepository;
import org.hibernate.SessionFactory;

/**
 * @author Joseph S.
 */
public class CryptoMinimal {
    
    /////////////////////////////////////////////////////////////////////
    
    private static final SessionFactory                SESSION_FACTORY = new CryptoSessionConfig().create();
    public  static final CryptoTransactionalRepository REPOSITORY      = new CryptoTransactionalRepository();
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_based_on_xxxxx_libraries";
        }
        
        @Override public String password() {
            return "postgres";
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
        private CryptoSessionConfig() {
            super(new CryptoDatabase());
        }
        
        @Override protected String[] packages() {
            return new String[]{ "momomo/com/example/app/entities" }; // The package to scan for entities 
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Note, both a repository and a transactional instance class in one! 
     */
    public static final class CryptoTransactionalRepository implements $SessionManagerRepository, $TransactionalHibernate {
        @Override public SessionFactory sessionFactory() {
            return SESSION_FACTORY;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
}

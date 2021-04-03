package momomo.com.example.app;

import momomo.com.db.$DatabasePostgres;
import momomo.com.db.$Service;
import momomo.com.db.$SessionConfig;
import momomo.com.db.$TransactionalHibernate;
import momomo.com.db.entities.$EntityId;
import momomo.com.db.sessionfactory.$SessionFactoryRepository;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

/**
 * This just represents the minimal viable code 
 * @author Joseph S.
 */
public class Crypto {
    
    /********************************************************************
     * As this is an example application, we prefer to show you how everything has its own area of responsibility and although they could be combined, we choose to present them by declaring them separately for clarity. Of course, the class {@link momomo.com.example.app.Crypto.CryptoTransactionalRepository} for instance, could implement $TransactionalHibernate as well and provide both features but we choose to show you can choose to use just the Transactional library should you not desire the other functionality that a repository also provides such as save(entity), refresh(entity), find(..), and so forth. See {@link momomo.com.example.app.Crypto.CryptoTransactionalRepository} for an example. 
     ********************************************************************/ 
    
    private static final CryptoDatabase                 DATABASE        = new CryptoDatabase     ();            // Not really required or used outside of this class
    private static final CryptoSessionConfig            SESSION_CONFIG  = new CryptoSessionConfig();            // Not really required or used outside of this class
    private static final SessionFactory                 SESSION_FACTORY = SESSION_CONFIG.create  ();            // Not really required or used outside of this class
    private static final CryptoTransactional            TRANSACTIONAL   = new CryptoTransactional() {};         // Not really required or used outside of this class
    private static final CryptoRepository               REPOSITORY      = new CryptoRepository   () {};         // Not really required or used outside of this class
    public  static final CryptoTransactionalRepository  repository      = new CryptoTransactionalRepository();  // Is a combo of TRANSACTIONAL and REPOSITORY in one. 
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    // All classes used above can be found declared in this file below 
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Our database setup also gives us access to execute JDBC queries anytime should we require that.
     * 
     * See {@link momomo.com.example.CryptoMaximal.CryptoDatabase} for more configuration options with plenty of comments. Also check the superclass.
     */
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_in_postgres";
        }
        
        @Override public String password() {
            return "postgres";
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * See {@link momomo.com.example.CryptoMaximal.CryptoSessionConfig} for more configuration options with plenty of comments. Also check the superclass. 
     */
    public static final class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
        public CryptoSessionConfig() {
            super(DATABASE);
        }
    
        @Override protected String[] packages() {
            return new String[]{ "momomo/com/example/app/entities" }; // The package to scan for entities 
        }
    
        @Override protected Params params() {
            return new Params().export(export -> 
                export.target(TargetType.DATABASE).action(SchemaExport.Action.BOTH)
            );
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    public interface CryptoRepository extends $SessionFactoryRepository {
        @Override default SessionFactory sessionFactory() {
            return SESSION_FACTORY;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    public interface CryptoTransactional extends $TransactionalHibernate {
        @Override default CryptoRepository repository() {
            return REPOSITORY;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    public static final class CryptoTransactionalRepository implements $SessionFactoryRepository, $TransactionalHibernate {
        @Override public SessionFactory sessionFactory() {
            return SESSION_FACTORY;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Provides a base class for entity services to simply extend to get various functionality, like requireTransaction(...), newTransaction(..) as well as save(entity), find(...), findByProperty(...) and so on.  
     * @param <T>
     */
    public abstract static class CryptoService<T extends $EntityId> extends $Service<T> implements CryptoTransactional {}
}

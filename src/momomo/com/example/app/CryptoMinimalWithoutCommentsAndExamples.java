package momomo.com.example.app;

import momomo.com.db.$DatabasePostgres;
import momomo.com.db.$SessionConfig;
import momomo.com.db.$TransactionalHibernate;
import momomo.com.db.sessionfactory.$SessionFactoryRepositoryHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.tool.schema.TargetType;

/**
 * This just represents the minimal viable code 
 * @author Joseph S.
 */
public class CryptoMinimalWithoutCommentsAndExamples {
    
    /////////////////////////////////////////////////////////////////////
    
    public  static final CryptoRepository    R               = new CryptoRepository   ();
    public  static final CryptoSessionConfig SESSION_CONFIG  = new CryptoSessionConfig();
    private static final SessionFactory      SESSION_FACTORY = SESSION_CONFIG.create();
    
    /////////////////////////////////////////////////////////////////////
    
    public static class CryptoRepository implements $SessionFactoryRepositoryHibernate, $TransactionalHibernate {
        @Override public SessionFactory sessionFactory() {
            return SESSION_FACTORY;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
        public CryptoSessionConfig() {
            super(new CryptoDatabase());
        }
    
        @Override protected String[] packages() {
            return new String[]{
                "momomo/com/example/app/entities" 
            };
        }
    
        @Override protected Params params() {
            return new Params().export(export -> export.target(TargetType.DATABASE));
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_in_postgres";
        }
        
        @Override public String password() {
            return "postgres";
        }
    }
}

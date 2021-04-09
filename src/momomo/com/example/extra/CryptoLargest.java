package momomo.com.example.extra;

import momomo.com.IO;
import momomo.com.Is;
import momomo.com.collections.$Set;
import momomo.com.db.$DatabasePostgres;
import momomo.com.db.$Migrations;
import momomo.com.db.$SessionConfig;
import momomo.com.example.app.entities.Stellar;
import org.hibernate.tool.schema.TargetType;

import java.io.File;

/**
 * Contains more configuration options than {@link momomo.com.example.app.Crypto} with comments. 
 * 
 * @author Joseph S.
 */
public class CryptoLargest {
    
    /**
     * Our database setup also gives us access to execute JDBC queries anytime should we require that.
     */
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_based_on_xxxxx_libraries";  // This database will be created in postgres if it does not exist already
        }
        
        @Override public String host() {
            return "localhost"; // Same as default in super class so this is not really required but for demo purposes
        }
        
        @Override public String port() {
            return "5432";      // Same as default in super class so this is not really required but for demo purposes
        }
        
        @Override public String username() {
            return "postgres";  // Same as default in super class so this is not really required but for demo purposes
        }
        
        @Override public String password() {
            return "postgres"; 
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Crypto setup of the session or really the SessionFactory through .create() method eventually
     */
    public static class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
        
        public CryptoSessionConfig() {
            super(new CryptoDatabase());
        }
    
        @Override protected String[] packages() {
            return new String[]{"momomo/com/example/app/entities"}; // The package to scan for entities
        }
        
        /////////////////////////////////////////////////////////////////////
        // All below in this class are just examples basically to show you what you can do. Nothing is required to be in there really
        /////////////////////////////////////////////////////////////////////
    
        /**
         * Example
         */
        @Override protected void properties() {
            // We can override or dismiss all of the properties normally applied in super, as  well as add any before, or after
            super.properties();
        }
    
        /////////////////////////////////////////////////////////////////////
    
        /**
         * Example 
         */
        @Override protected Export export(Export export) {
            if ( false ) {
                // By default we generate a file with the sql to allow you to do what you want with it, including database.sql( thesql ) 
                // to execute it but for demo we generate by directly targeting the database instead.
                // We normally do it using Migrations commented at the bottom below in SessionConfig
            
                return export.target(TargetType.DATABASE);
            }
        
            return export;
        }
    
        /////////////////////////////////////////////////////////////////////
    
        /**
         * Example
         */
        @Override protected boolean drop() {
            return !Is.Production();                 // We drop in development and test and start fresh everytime. You may use whatever logic here to drop or not. 
        }
    
        /////////////////////////////////////////////////////////////////////
    
        /**
         * Example
         */
        @Override protected void dropDB() {
            if ( true ) {
                // By default a dropDB() call will drop all the tables in the database
                super.dropDB();                             
            }
            else {
                // But we may opt to only drop some tables instead, and say keep tables that are expensive to drop in development, 
                // such as tables that have already crawled for data, such as images or what not. 
                // Of course you do as you please. 
                database.tablesDrop(
                    new $Set<String>()
                        .add(Stellar.Cons.table)
                        .add($Migrations.Cons.table)
                );
            }
        }
    
        /////////////////////////////////////////////////////////////////////
    
        @Override protected void migrate(File sql) {
            database.sql( IO.text(sql) );
        
            /**
             * Example
             */
            if ( false ) {
                // We can move / copy the entire sql create / drop-create as they are generated to a folder we desire 
                IO.copy(sql, new File("/path/to/our/sql/schema.sql"));
            }
    
            /**
             * Example
             */
            if ( false ) {
                // Rather than execute database.sql() we could do it using our migrations class.  
                // This would ensure this code block is never executed more than once.
                // The sql here is the sql generated by Hibernate that creates the database. 
                // Unless you've targeted the database with create(...) method, then you would 
                // need to execute the create manually which we prefer to do.
                // The migrations is a multipurpose vehicle to really perform any action only once transactionally
                migrations.new Migration("momomo.com.crypto.database.create") {
                    @Override
                    public void perform() {
                        database. sql(IO.text(sql) );
                    }
                };
                
                // The good thing with this approach is that we are able to manipulate the database before hibernate gets to it, 
                // and we can do native sql and then switch to using our entities should be desire to utilize the Hibernate API 
                // to update our data for instance.
                // Once migrations has been fully executed then they will not be reapplied. 
                // Migrations order can not be reshuffled in which case it will be detected and an exception will throw and roll back all of the new migrations.
                // Migrations can be deleted from the first to last if this has been configured to allow already applied migrations to be forgotten as the state of the db should already have them. 
                
                migrations.new Migration("momomo.com.crypto.database.add.column.1") {
                    @Override
                    public void perform() {
                        database.sql("ALTER TABLE ... ADD COLUMN ... ");
                    }
                };
                
                // Here we apply our migrations all in one big transaction. If any fail, we deem it safest to roll it all back in order for you to fix the errors  
                migrations.migrate();
                
                // By default migrations is not on, since we do not want to force the tables onto anyone currently using say liquibase or flyway, and therefore you need to add initial migration as well as execute migrate. 
                
                // To provide a migration of your choice just override the method newMigrations(); 
            }
        }
    
        /////////////////////////////////////////////////////////////////////
    }
    
}

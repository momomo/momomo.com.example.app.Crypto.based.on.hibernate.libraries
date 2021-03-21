<!---
-->

## momomo.com.example.app.Crypto

###### Example application in its early days
* Right now to provide an overview of the database & transactional libraries, their setups and how they are used in a completely and fully working application
    *  Requires a running `postgreSQL`
        * We might support an *in memory database* in the future for this sample application. 

#### Dependencies 
* [`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core) 
* [`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)
* [`momomo.com.platform.db.base`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)
* [`momomo.com.platform.db.base.jpa`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)
* [`momomo.com.platform.db.base.jpa.session`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)
* [`momomo.com.platform.db.base.jpa.session.with.postgres`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session.with.postgres)
* [`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)
* [`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)
* [`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)

### Background

The major reason for this sample application was to showcase the [`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional) API. 

But now we believe this is the beginning of an entire application platform coming as we continue to make available more and more of our libraries to the public.

### Guide

The entire thing is really much much simpler to understand and navigate if you just check out the **repository**, **navigate and/or run** the examples. But we will try to guide you here.

We've decided to develope a **Crypto** related application!

Start by looking at

* **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)**  
  We wanted you to start looking at how small the configuration actually is, to give you an overview because the actual class that sets things up is **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** but  
  lots of **comments and examples** have been added to **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** and it is therefore much fatter than it should be. 
  
  A stripped down version of it had to be provided *to showcase* how **small** a *working configuration* really is. 
  
  Now that you've seen it, you may proceed. 

* **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**  
Think of this class as your main application class where we put say things global to the application, something any class might want to access. 
We would not want to use something named **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)** so **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** is a good name.      
If you where developing a **Tv** application, maybe you would have a **`Tv.java`**  
If you where developing a **Store**, maybe you would have a **`Store.java`**  
If you where developing a **Finance**, maybe you would have a **`Finance.java`**  
&nbsp;  
Now, for simplicity we've put **everything** that is related to configuration or setups, or to grant access to anything inside this class, **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**.   
There is not really that much in it. Not more than **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)**.  

----

**We setup [`new Crypto.CryptoDatabase()`](https://github.com/momomo/momomo.com.example.app.Crypto/blob/master/src/momomo/com/example/app/Crypto.java#L52)**  
```java
/**
 * Our database setup also gives us access to execute JDBC queries anytime should we require that.
 */
public static final class CryptoDatabase implements $DatabasePostgres {
    @Override public String name() {
        return "crypto_database_name_in_postgres";
    }
    
    @Override public String password() {
        return "postgres"; 
    }
}
```

----
         
We setup the [`new Crypto.CryptoSessionConfig()`](https://github.com/momomo/momomo.com.example.app.Crypto/blob/master/src/momomo/com/example/app/Crypto.java#L80)          

```java
/**
 * Crypto setup of the session or really the SessionFactory through .create() method eventually
 */
public static class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
    
    public CryptoSessionConfig() {
        super(DATABASE);
    }

    @Override protected String[] packages() {
        // The packages we wish to scan for entities
        return new String[]{
            "momomo/com/example/app/entities"    // The package to scan 
        };
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

    /**
     * Example
     */
    @Override protected boolean drop() {
        return !Is.Production();                        // We drop in development and test and start fresh everytime. You may use whatever logic here to drop or not. 
    }

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
                    .add(Bitcoin.Cons.table)
                    .add($Migrations.Cons.table)
            );
        }
    }

    /**
     * Example 
     */
    @Override
    protected Params params() {
        if ( false ) {
            // By default we generate a file with the sql to allow you to do what you want with it, including database.sql( thesql ) 
            // to execute it but for demo we generate by directly targeting the database instead.
            // We normally do it using Migrations commented at the bottom below in SessionConfig
            
            return new $SessionConfig.Params().export(export -> export.target(TargetType.DATABASE));
        }
        
        return new $SessionConfig.Params(); // Defaults to TargetType.SCRIPT which generates the schema sql and which we execute in migrate() method on the first line instead 
    }

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
}
```



    




### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

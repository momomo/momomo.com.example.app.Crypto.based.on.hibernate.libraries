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
  
```java                                               
// This is all code required!

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
    
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_in_postgres";
        }
        
        @Override public String password() {
            return "postgres"; 
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
}
```  
   
  
  Now that you've seen it, glanced it, consumed it, you may ***proceed***. 

* We would not want to use something obtrusive as **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)** and don't want to scare you off, so we actually use:  

  **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**
  
  Think of this class as your ***main application class*** where we put say things **global** to the application, often `final` and something any class might want to access. 
      
    If you where developing a **Tv** application, maybe you would have a **`Tv.java`**  
    If you where developing a **Store**, maybe you would have a **`Store.java`**  
    If you where developing a **Finance**, maybe you would have a **`Finance.java`**  
    &nbsp;  
    Now, for simplicity we've put **everything** that is related to *configuration or setups** inside the class **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**.    
    
    There is not really that much in it, not more than **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)**.  
        
    But there is lots of **comments and examples of code** that's disabled.   
----

**This is what we setup**.   

```java
public  static final CryptoRepository    R               = new CryptoRepository   (); // Not used other than at the bottom of this file! Can you find it?
public  static final CryptoDatabase      DATABASE        = new CryptoDatabase     (); // You might want to execute some jdbc queries using this one sometimes. We don't in this application but we made it public for you. 
private static final CryptoSessionConfig SESSION_CONFIG  = new CryptoSessionConfig();
private static final SessionFactory      SESSION_FACTORY = SESSION_CONFIG.create  ();
```    

The classes used `CryptoRepository`, `CryptoDatabase`, `CryptoSessionConfig` can all be found declared within **[`Crypto.java`](https://github.com/momomo/momomo.com.example.app.Crypto/blob/master/src/momomo/com/example/app/Crypto.java)**.   
Same smaller versions of them as exists in **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)**.

----

### Demo of the `Transactional` API

### Chapter one 

We start by looking at our **first entity** 

*[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)*
```java                                                       
// A stripped down version of Bitcoin.java

@Entity @Table(name = Bitcoin.Cons.table) public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Bitcoin implements $Entity {
    
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class Cons {        
        public static final String table = "bitcoin";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final Service S = new Service(); public static final class Service {
        public Bitcoin insert(Timestamp time, double usd) {

            // This 'very very expensive' creation need not to be inside the transaction (just for demo)
            Bitcoin entity = new Bitcoin()
                .setId(UUID.randomUUID())
                .setTime(time)
                .setUsd(usd)
            ;
    
            // Now, require the transaction and execute save within, also return the saved entity. 
            return Crypto.repository.requireTransaction(() -> {
                return save(entity);
            });

        }
        
        private Bitcoin save(Bitcoin entity) {
            return Crypto.repository.save(entity);  
        }
    }
}
```

We highlight the method in `Bitcoin.Service.insert(Timestamp, double)`. 

In this method we create the entity 

```java
Bitcoin entity = new Bitcoin()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
```                          

The we require a write capable `transaction` which means create a `new transaction` if there is not already one ongoing for this thread, perhaps started by the caller to `insert()`. 

```
return Crypto.repository.requireTransaction((tx) -> {
    return save(entity);
});
```                                                                                                                            

The `save(entity)` will execute within the `transaction` and when it terminates it will `commit the transaction`, if it was the one who `started it` and not the parent, or the parent of the parent, and so forth.

The save implementation could really be your own normal logic. 

If you use Spring you would use that. We have here reused our already created and **very capable** repository which eventually will call `session.saveOrUpdate(entity)` and ensure that it was generated an id as it should.

To be able to use our repository we currently require that your `entities` implement our [`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/blob/master/src/momomo/com/db/%24Entity.java). We can see that this is no longer really required and we will remove this requirement eventually from our `Repository` implementation.

We invoke this method simply as `Bitcoin.S.insert(Time.stamp(), 10000.1)` from anyplace, even a static void main. It will trigger the database generation, scan the entities, setup the `sessionFactory` and get you a transaction and eventually **save the data**.

```java
public static void main(String[] args){
  Bitcoin.S.insert(Time.stamp(), 10000.1); // This will work, in case you did not believe it. From any place. Even the command line. Run or debug within your editor.   
}
```

You can find more code with plenty more examples, some very comples in **[`PublicStaticVoidMain.java`](src/momomo/com/example/app/PublicStaticVoidMain.java)**.     

---

### Chapter two


         








### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

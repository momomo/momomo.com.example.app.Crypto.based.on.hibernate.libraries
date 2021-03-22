<!---
-->

## momomo.com.example.app.Crypto

###### Example application currently mainly to showcase the Transactional API
       
* Currently mostly provides an overview of the [`momomo.com.platform.db.base`](https://github.com/momomo/momomo.com.platform.db.base) & [`transactional`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate) libraries, their setups and how they are used in a completely and fully working application
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
// This is all code required to get started!

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

@Entity @Table(name = Bitcoin.Cons.table) public ... final class Bitcoin implements $Entity {
    
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd                  

    ...
    
    public static final Service S = new Service(); public static final class Service {    
       /** 
        * Insert method that creates a bitcoin, then requires a transaction, and then saves it within the transaction, which then commits if it was the one to start it.  
        */                  
        public Bitcoin insert(Timestamp time, double usd) {
            
            // This ""very very expensive"" creation need not to be inside the transaction. 
            Bitcoin entity = new Bitcoin()
                .setId(UUID.randomUUID())
                .setTime(time)
                .setUsd(usd);
    
            // Now, require the transaction and execute save within, also return the saved entity. 
            return Crypto.repository.requireTransaction(() -> {
                return Crypto.repository.save(entity);
            });
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
    return Crypto.repository.save(entity);
});
```                                                                                                                            

The `save(entity)` will execute within the `transaction` and when it terminates it will `commit the transaction`, if it was the one who `started it` and not the parent, or the parent of the parent, and so forth.

The save implementation could really be your own normal logic. If you use Spring you would use whatever you where you using, if Hiberante you would do the same. 

We have here reused our already created and **very capable** repository which eventually will call `session.saveOrUpdate(entity)` and ensure that it was generated an id as it should.

>> Note, to be able to use our repository we currently require that your `entities` implement our [`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/blob/master/src/momomo/com/db/%24Entity.java) interface.   
>> We can see that this is no longer really required as the interface is empty, but was a safe mechanism for our internal code, and we will eventually remove this requirement from our `Repository` implementation.
>> The Transactional API does not have that requirement, but the repository.save(..), repository.find(...) do currently.    

We now can invoke this method simply as `Bitcoin.S.insert(Time.stamp(), 10000.1)` from anyplace, even a **`static void main`** and we do: 

```java
public static void main(String[] args){
  Bitcoin.S.insert(Time.stamp(), 10000.1); // This will work, from any place. Even the command line, or to run or debug within your editor without any external requirements.    
}
```                                                                                                                                      

This will **trigger** the database generation, scan the entities, setup the `sessionFactory` and get you a transaction and eventually create and **save the data** to the database.s

You can find plenty more examples, some very complex in **[`PublicStaticVoidMain.java`](src/momomo/com/example/app/PublicStaticVoidMain.java)**.

---

### Chapter two

You've now seen the **`requireTransaction(()->{})`**, let us see what else can we do.   

We now take a look at pieces of the inser method inside class **[`Etherum.Service.java`](src/momomo/com/example/app/entities/Etherum.java)**


```java
// Given 

Etherum entity = new Etherum()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
```

```java
// Example 1.
Crypto.repository.requireTransaction(($TransactionHibernate transaction) -> {
    // Disable autocommit, so we commit when we want or not at all
    transaction.autocommit(false);

    save(entity);
    save(entity);
    save(entity);
    save(entity);

    transaction.commit();
});
```

```java
// Example 2.
Crypto.repository.requireTransaction((tx) -> {
    tx.afterCommit(() -> {
        // Send email perhaps when we exit the transaction after succesfully committing!
 
        // We have now inserted the value in our database successfully!

        // We do not want to send the email unless we actually have 100% inserted the stuff so this is a handy method to use for that 
    });

    save(entity);
});
```

```java 
// Example 3.
String returns = Crypto.repository.requireTransaction(() -> {
    save(entity);

    return "we can return something from the transactional lambda";
});
```

```java
// 4. We repeat the return demo but by returning an entity
Etherum e = Crypto.repository.requireTransaction(() -> {
    return save(entity);
});
```

```java                                           
// 5. Maybe we do not want to execute things inside the lambda block but desire more freedom? 
$TransactionHibernate tx = Crypto.repository.requireTransaction();
save(entity);
save(entity);
save(entity);
save(entity);
tx.autocommit(false);
tx.afterCommit  (()-> {});
tx.afterRollback(()-> { /* A crime has been committed! Report error to the FBI! */ });
tx.rollback();
tx.commit();
```


```java
// Example a. 
Crypto.repository.newTransaction(() -> {
    // A new transaction is created! Not reusing an existing one if there is one!
});
```

```java                                                                               
// Example b.
Crypto.repository.supportTransaction(() -> {
    // A read only transaction is created! Writing to the database is not possible, and will result in a terrible offense!
});
```

```java    
// Example d.
Crypto.repository.newTransaction((tx) -> {
    save(entity);

    tx.rollback();

    save(entity);

    // note, now, the autocommit won't occur, as we have rolled back and basically discarded the entire transaction.  
});
```


```java                                                              
// Example e.
Crypto.repository.requireTransaction(() -> {
    save(entity);
}, false /** commit false**/ );
```


```java                                 
// Example f. 
try {
    Crypto.repository.requireTransaction(() -> {
        throw new IOException();
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E)
}
```

```java                       
// Example g. 
try {
    File file = Crypto.repository.requireTransaction(() -> {
        if ( false ) {
            throw new IOException();
        }
        return new File("");
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E)
}
```

```java                   
// Example h. 
Session s1 = Crypto.repository.requireSession();
Session s2 = Crypto.repository.newSession();

// Example i.
Crypto.repository.requireOptions()
    .propagation($TransactionOptions.Propagation.NEW)
    .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
    .execute((tx)-> {
        tx.autocommit(false);
        tx.afterCommit(()-> {});
        tx.afterRollback(()-> {});

        // ... 
    })
;
```


```java                                     
// Example j. or 
$TransactionOptionsHibernate options = Crypto.repository.requireOptions();
// ... options.propagation(...) ...
```


```java
// Example k.
$TransactionHibernate tx2 = Crypto.repository.requireOptions()
    .propagation($TransactionOptions.Propagation.NEW)
    .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
;
```


```java                                      
// Example l.
Crypto.repository.requireOptions()
    .timeout(1000)
    .withConnection((java.sql.Connection connection) -> {
        connection.setReadOnly(true);
        connection.setCatalog("catalog");
        connection.setTransactionIsolation(1);
        connection.clearWarnings();
        connection.createStatement();
        connection.setTypeMap(new HashMap<>());
        connection.setHoldability(1);
        connection.setSavepoint();
        connection.setSavepoint();
        // ...
    })
    .create()
    .execute(tx -> {
        tx.autocommit(false);

        save(entity);
        save(entity);
        save(entity);

        tx.commit();
    })
;
```
    




         








### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

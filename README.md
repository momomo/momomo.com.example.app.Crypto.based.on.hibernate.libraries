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

The classes used `CryptoRepository`, `CryptoDatabase`, `CryptoSessionConfig` can all be found declared within **[`Crypto.java`](https://github.com/momomo/momomo.com.example.app.Crypto/tree/master/src/momomo/com/example/app/Crypto.java)**.   
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

>> Note, to be able to use our repository we currently require that your `entities` implement our [`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java) interface.   
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

We now take a look at pieces of the insert method inside class **[`Etherum.Service.java`](src/momomo/com/example/app/entities/Etherum.java)**


```java
// Given 

Etherum entity = new Etherum()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
```

```java
// Example a.
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
// Example b.
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
// Example c.
String returns = Crypto.repository.requireTransaction(() -> {
    save(entity);

    return "we can return anything from the transactional lambda";
});
```

```java
// Example d.
Etherum e = Crypto.repository.requireTransaction(() -> {
    return save(entity); // We repeat the return demo but by returning an entity
});
```

```java                                           
// Example e. 
// Maybe we do not want to execute things inside the lambda block but desire more freedom? 
$TransactionHibernate tx1 = Crypto.repository.requireTransaction();
save(entity);
save(entity);
save(entity);
save(entity);
tx1.autocommit(false);
tx1.afterCommit  (()-> {});
tx1.afterRollback(()-> { /* A crime has been committed! Report error to the FBI! */ });
tx1.rollback();
tx1.commit();
```

```java
// Example f. 
Crypto.repository.newTransaction(() -> {
    // A new transaction is created! Not reusing an existing one if there is one!
});
```

```java                                                                               
// Example g.
Crypto.repository.supportTransaction(() -> {
    // A read only transaction is created! Writing to the database is not possible, and will result in a terrible offense!
});
```

```java    
// Example h.
Crypto.repository.newTransaction((tx) -> {
    save(entity);

    tx.rollback();

    save(entity);

    // note, now, the autocommit won't occur, as we have rolled back and basically discarded the entire transaction.  
});
```


```java                                                              
// Example i.
Crypto.repository.requireTransaction(() -> {
    save(entity);
}, false /** commit false**/ );
```


```java                                 
// Example j. 
try {
    Crypto.repository.requireTransaction(() -> {
        throw new IOException();
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. Will not commit.  
}
```

```java                       
// Example k. 
try {
    File file = Crypto.repository.requireTransaction(() -> {
        if ( false ) {
            throw new IOException();
        }
        return new File("");
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. Will not commit.
}
```

```java                   
// Example l. 
Session s1 = Crypto.repository.requireSession();
Session s2 = Crypto.repository.newSession();
```

```java
// Example m.
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
// Example n.  
// Similar to example m. but without chaining  
$TransactionOptionsHibernate options = Crypto.repository.requireOptions();
// ... options.propagation(...)
// ... options.create().execute(...)
```

```java
// Example o.
// Similar to example m. and n. but showing that create() returns a transaction that we can execute. 
$TransactionHibernate tx2 = Crypto.repository.requireOptions()
    .propagation($TransactionOptions.Propagation.NEW)
    .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
;
tx1.execute(()-> {
    save(entity);
});

tx1.execute(()-> {
    save(entity);

    tx1.commit();
}, false /** don't commit **/ );
```

```java                                      
// Example p.
// Similar to example m., n. and o. with more options made visibile.
Crypto.repository.requireOptions()
    .timeout(1000)

    // Notice the withConnection option being used! Full access! 
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

### Chapter three

Now that you have seen plenty of examples of what can be done, we now focus on showing how things can be made prettier by 
creating a   
`class `[`CryptoService<T extends EntityId>`](src/momomo/com/example/app/Crypto.java#L203)` extends `[`$Service<T>`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$Service.java)` implements `[`$TransactionalHibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate/tree/master/src/momomo/com/db/%24TransactionalHibernate.java)` {...}`

We've done so, inside, at the bottom of **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** with the implementation really simple:

```java
public static abstract class CryptoService<T extends $EntityId> extends $Service<T> implements $TransactionalHibernate {
    @Override public $SessionFactoryRepositoryHibernate repository() { 
        return Crypto.repository; // Or Crypto.R 
    }
}
```                                

We now take a look class **[`Polkadot.java`](src/momomo/com/example/app/entities/Polkadot.java)**

A couple of changes have happend from the `Bitcoin` and `Etherum` classes. 

   1. We've gotten rid of the `@Id private UUID id;` seen inside `Bitcoin` class as well set the setter inside the `insert()` method from `Bitcoin` class.   
   Instead our entity implements [`$EntityIdUUID`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/entities/$EntityIdUUID.java) which will provide and generate one for us automatically.
   
   2. The `Service` inside **[`Polkadot.java`](src/momomo/com/example/app/entities/Polkadot.java)** now `extends` `Crypto.CryptoService` as `public static final class Service extends Crypto.CryptoService<Polkadot> { ... }`  
   There is nothing to implement as everything required is already implemented by `Crypto.CryptService` which only provides the `Crypto.repository` to use.
   
   3. So what we by extending [`$Service`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/%24Service.java) is the following:   
   [![Available methods](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/signatures.v2.2021.04.22.jpg?raw=true)
       * We can `List<Polkadot> all     = super.list()` all.
       * We can `List<Polkadot> matches = super.list( criteria().add(...) )` to find using a criteria.
       * We can `List<Polkadot> matches = super.list(  )` find all that equals the price of `100 usd`. It will build the criteria for us.
       * We can `List<Polkadot> matches = super.findAllByProperty("time", Time.stamp()`;  
       * We can `Polkadot = super.findByProperty("time", Time.stamp()`;   
       * We can `Polkadot = super.findByEntity(new Polkadot().setTime(Time.stamp()).setUsd(100.1))`;
       * We can `super.save(entity)` to `session.saveOrUpdate()`. It will also verify the `save` was ok, since at times this won't occur such as being in a read only transaction by mistake.  
       * We can `requireTransaction(...); newTransaction(...); supportTransaction(...); newOptions(...);`  
       

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

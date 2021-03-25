<!---
-->

##### Example application currently mainly to showcase the Transactional API
       
* Currently mostly provides an overview of the **[`momomo.com.platform.db.base`](https://github.com/momomo/momomo.com.platform.db.base)** & **[`transactional`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)** libraries, their setups and how they are used in a completely and fully working application
    *  Requires a running `postgreSQL`
        * We might support an *in memory database* in the future for this sample application. 

##### Dependencies 
* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)** 
* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)**
* **[`momomo.com.platform.db.base`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**
* **[`momomo.com.platform.db.base.jpa`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**
* **[`momomo.com.platform.db.base.jpa.session`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**
* **[`momomo.com.platform.db.base.jpa.session.with.postgres`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session.with.postgres)**
* **[`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)**
* **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)**
* **[`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)**

---

### Background

The major reason for this sample application was to showcase the **[`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)** API. 

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
```  
   
  
Now that you've seen it, glanced it, consumed it, you may ***proceed***.

---

We would not want to use something named so obtrusively as **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)** and we ensure you we don't. 

Our main class is actually called **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** which is a decent name for our main application class and there is much in it, not more than **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)**, but there is lots of **comments**, **examples** and **disabled** code.
     
And this is **what we setup**.   

```java
public  static final CryptoRepository    R               = new CryptoRepository   (); // Not used other than at the bottom of this file! Can you find it?
public  static final CryptoDatabase      DATABASE        = new CryptoDatabase     (); // You might want to execute some jdbc queries using this one sometimes. We don't in this application but we made it public for you. 
private static final CryptoSessionConfig SESSION_CONFIG  = new CryptoSessionConfig();
private static final SessionFactory      SESSION_FACTORY = SESSION_CONFIG.create  ();
```    

The classes used `CryptoRepository`, `CryptoDatabase`, `CryptoSessionConfig` can all be found declared within **[`Crypto.java`](https://github.com/momomo/momomo.com.example.app.Crypto/tree/master/src/momomo/com/example/app/Crypto.java)**. Same smaller / compact versions of them as exists in **[`CryptoMinimalWithoutCommentsAndExamples.java`](src/momomo/com/example/app/CryptoMinimalWithoutCommentsAndExamples.java)** as already mentioned.

---

### Demonstration of the `Transactional` API

### Part one 

We start by looking at our **first entity** 

**[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)**
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
            // This ''very very expensive'' creation need not to be inside the transaction 
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

The we require a write capable `transaction` which means create a `new transaction` if there is not already one ongoing for this thread, perhaps already one started by the caller to `insert()`? If not, create it.   

```
return Crypto.repository.requireTransaction((tx) -> {
    return Crypto.repository.save(entity);
});
```                                                                                                                            

The `save(entity)` will execute within the `transaction` and when it terminates it will `commit the transaction`, if it was the one who `started it` and not the parent, or the parent of the parent, and so forth.

The **save call** could really be your own normal logic. If you use Spring you would use whatever you where you using, if Hiberante you would do the same. 

We have here reused our already created and **very capable** repository which eventually will call `session.saveOrUpdate(entity)` and ensure that it was generated an id as it should.

> Note! 
> To be able to use our repository we currently require that your `entities` implement our **[`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java)** interface.   
> We can see that this is no longer really required as the interface is empty, but was a safe mechanism for our internal code to ensure control but we will eventually remove this requirement from our `Repository` implementation.
> The Transactional API does not have that requirement, but the repository.save(..), repository.find(...) currently do.    

We now can invoke this method simply as `Bitcoin.S.insert(Time.stamp(), 10000.1)` from anyplace, even a **`static void main`** and we do: 

```java
public static void main(String[] args){
  Bitcoin.S.insert(Time.stamp(), 10000.1); // This will work, from any place. Even the command line, or to run or debug within your editor without any external requirements.    
}
```                                                                                                                                      

This will **trigger** the database generation, scan the entities, setup the `sessionFactory` and get you a transaction and eventually create and **save the data** to the database.s

You can find plenty more examples, some very complex in **[`PublicStaticVoidMain.java`](src/momomo/com/example/app/PublicStaticVoidMain.java)**.

---

### Part two

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

A rewind from the **[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)** class 

```java                                      
// This you've seen from Bitcoin.java 
Crypto.repository.requireTransaction(() -> {
    return save(entity);
});                              
```

We can demand a new transaction regardless of an existing one

```java
Crypto.repository.newTransaction(() -> {
    return save(entity);
});

```    

A read only transaction

```java
List<Polkadot> = Crypto.repository.supportTransaction(() -> {
    return list(); // super.list() available from the super class $Service 
});   
```

We can disable auto commit. 

```java
Crypto.repository.requireTransaction(($TransactionHibernate transaction) -> {
    // Disable autocommit, so we commit when we want or not at all
    transaction.autocommit(false);

    save(entity);

    transaction.commit();
});
```                                               

Another way to disable automatic commit. 

```java                                                              
Crypto.repository.requireTransaction(() -> {
    save(entity);
}, false /** commit false**/ );
```

We can hook in to do something once the commit is succesful.

```java
Crypto.repository.requireTransaction((tx) -> {
    tx.afterCommit(() -> {
        // Send email perhaps when we exit the transaction after succesfully committing!
 
        // We have now inserted the value in our database successfully!

        // We do not want to send the email unless we actually have 100% inserted the stuff so this is a handy method to use for that 
    });

    save(entity);
});
```

We can return something from inside the transaction.                                                         

```java
Etherum e = Crypto.repository.requireTransaction(() -> {
    return save(entity); // We repeat the return demo but by returning an entity
});
```

Return again, showcasing that you can return anything really.  

```java 
String returns = Crypto.repository.requireTransaction(() -> {
    save(entity);

    return "we can return anything from the transactional lambda";
});
```

A lambda less example, if we do not want to execute things inside a lambda but desire more freedom?  

```java                                           
$TransactionHibernate tx1 = Crypto.repository.requireTransaction();
save(entity);
tx1.autocommit(false);
tx1.afterCommit  (()-> {});
tx1.afterRollback(()-> { /* A crime has been committed! Report error to the FBI! */ });
tx1.rollback();
tx1.commit();
```          

Rolling back. 

```java    
Crypto.repository.newTransaction((tx) -> {
    save(entity);

    tx.rollback();

    save(entity);

    // note, now, the autocommit won't occur, as we have rolled back and basically discarded the entire transaction.  
});
```

We show that exceptions will bubble up to the caller. 

```java                                 
try {
    Crypto.repository.requireTransaction(() -> {
        throw new IOException();
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. Will not commit.  
}
```

We can return and throw exceptions.                                                     

```java                       
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

We can get access to the actual session and retain 100% control.  

```java                   
// Example l. 
Session s1 = Crypto.repository.requireSession();
Session s2 = Crypto.repository.newSession();
```                                            

We can build the transaction properties and set various things ourselves. 


```java
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

// Similar to example m. but without chaining  
$TransactionOptionsHibernate options = Crypto.repository.requireOptions();
// ... options.propagation(...)
// ... options.create().execute(...)
```

Getting a transaction that we can execute                                                                       

```java
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

More options made visibile

```java                                      
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
        // ...
    })
    .create()
    .execute(tx -> {
        tx.autocommit(false);

        save(entity);

        tx.commit();
    })
;
```                            

### Part three

Now that you have seen plenty of examples of what can be done, we now focus on showing how things can be made **prettier** by 
creating a an inner static class **[`CryptoService`](src/momomo/com/example/app/Crypto.java#L216)** **at the bottom** **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** with the implementation **being really simple**, and **minimal**:

```java
public static abstract class CryptoService<T extends $EntityId> extends $Service<T> implements $TransactionalHibernate {
    @Override public $SessionFactoryRepositoryHibernate repository() { 
        return Crypto.repository; // Or Crypto.R 
    }
}
```                                                        

Link to **[`$Service`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/$Service.java)** and **[`$TransactionalHibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate/tree/master/src/momomo/com/db/%24TransactionalHibernate.java)**

We now take a look class **[`Polkadot.java`](src/momomo/com/example/app/entities/Polkadot.java)** which has gone through a *couple of changes* compared to the **[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)** and **[`Etherum.java`](src/momomo/com/example/app/entities/Etherum.java)** classes. 

   * We've gotten rid of the `@Id private UUID id;` seen inside **[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)** as well set the `setId()` call in `insert()` method.   
   Instead our entity implements **[`$EntityIdUUID`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/entities/$EntityIdUUID.java)** which will provide and generate one for us automatically.
   
   * The `Service` inside **[`Polkadot.java`](src/momomo/com/example/app/entities/Polkadot.java)** now `extends` **[`Crypto.CryptoService`](src/momomo/com/example/app/Crypto.java#L216)** so we have `Polkadot.Service extends Crypto.CryptoService<Polkadot>`  
   There is nothing to implement as everything required is already implemented by **[`Crypto.CryptoService`](src/momomo/com/example/app/Crypto.java#L216)** which only provides the `Crypto.repository` to use.
   
   * So what we by extending **[`$Service`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/tree/master/src/momomo/com/db/%24Service.java)** is the following:   
   &nbsp;![Available methods](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/signatures.v2.2021.04.22.jpg?raw=true)
      * `List<Polkadot> all     = super.list()` to list all for that table.
      * `List<Polkadot> matches = super.list( criteria().add(...) )` to find many using a criteria query.
      * `List<Polkadot> matches = super.findAllByProperty("time", Time.stamp()` to many some using a property value.  
      * `Polkadot = super.findByProperty("time", Time.stamp()` to find one using property value.   
      * `Polkadot = super.findByEntity(new Polkadot().setTime(Time.stamp()).setUsd(100.1))`; to find one with the time stamp and price of `100` usd and will build the criteria for us.
      * `super.save(entity)` to `session.saveOrUpdate()`. It will also verify the `save` was ok, since at times this won't occur such as being in a read only transaction by mistake.  
      * `requireTransaction(...)` 
      * `newTransaction(...)` 
      * `supportTransaction(...)`
      * `requireOptions(...)`  
      * ...         


So now in **[`Polkadot.java`](src/momomo/com/example/app/entities/Polkadot.java)** we have 

```java
public static final class Service extends Crypto.CryptoService<Polkadot> { ... }
```                                                                            

with rewritten **`insert()`** without the need to access **`Crypto.R`** anymore from the **`Service`**

```java
public Polkadot insert(Timestamp time, double usd) {
    return requireTransaction(()-> {
        Polkadot entity = new Polkadot()
            .setTime(time)
            .setUsd(usd)
        ;
        return save(entity);   
    });
}      
```

and also added some more examples:

```java
/**
 * Return all the historic data within polkadot table 
 */
public List<Polkadot> historic() {
    return supportTransaction(()-> {
        return super.list();
    });
}            
```

and 

```java
/**
 * Return the data within time range
**/
public List<Polkadot> range(Timestamp from, Timestamp to) {
    return supportTransaction(()-> {
        return list(criteria()
            .add( Restrictions.ge(Cons.time, from) )
            .add( Restrictions.le(Cons.time, to  ) )
        );
    });
}
```

Take a look at **[`Stellar.java`](src/momomo/com/example/app/entities/Stellar.java)** for the minimal version of what the **[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)** class could look like. 
 
```java
@Entity
@Table(name = Stellar.Cons.table)
public @Accessors(chain = true) @Getter @Setter(AccessLevel.PROTECTED) final class Stellar extends $EntityIdUUID {
    
    private Timestamp time;
    private double    usd;     // Represents the price in usd
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class Cons {
        public static final String table = "stellar";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final Service S = new Service(); public static final class Service extends Crypto.CryptoService<Stellar> { private Service(){}
        public Stellar insert(Timestamp time, double usd) {
            return save( create().setTime(time).setUsd(usd) ); 
        }
    }
}
```

Here we no longer use `requireTransaction()` because we put that burden for the caller to know to reduce boiler plate. In reality all of our code no longer uses `requireTransaction` other than by the callers because the caller would know the entire transaction scope and likely would need to wrap multiple ones to create all of the things at the same time.

#### Part four

If we now look at **[`PublicStaticVoidMain.java`](src/momomo/com/example/app/PublicStaticVoidMain.java)** we can find a `static void main` and some code ready to run the entire thing.

```java
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
```

When we run this static void main we will eventually find the **following in our database**:  

![Generated tables](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.tables.v1.2021.04.25.jpg?raw=true)                

   * ***bitcoin table***  
   ![Bitcoin table](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.bitcoin.table.v1.2021.04.25.jpg?raw=true)        
   
   * ***polkadot table***  
   ![Polkadot table](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.polkadot.table.v1.2021.04.25.jpg?raw=true)        
   
   * ***stellar table***  
   ![Stellar table](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.stellar.table.v1.2021.04.25.jpg?raw=true)        
           

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

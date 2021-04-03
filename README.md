<!---
-->

##### Example application currently mainly to showcase our database and transactional libraries using the Hibernate libraries
       
* Provides an overview of the **[`momomo.com.platform.db.base`](https://github.com/momomo/momomo.com.platform.db.base)**, **[`momomo.com.platform.db.base.jpa.session`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**, **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)** libraries, how they are setup and used with a complete and fully working sample application. 
    *  Requires a running `postgreSQL`. We might support an *in memory database* in the future for this sample application. 

##### Dependencies 
* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)** 
* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)**
* **[`momomo.com.platform.db.base`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**
* **[`momomo.com.platform.db.base.jpa`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**
* **[`momomo.com.platform.db.base.jpa.session`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session)**
* **[`momomo.com.platform.db.base.jpa.session.with.postgres`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session.with.postgres)**
* **[`momomo.com.platform.db.transactional`](https://github.com/momomo/momomo.com.platform.db.transactional)**
* **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)**

##### Our other repositories                          

* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)**  
Is essentially what makes the our the core of several of momomo.com's public releases and contains a bunch of Java utility.

* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)**  
Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed and understood intuitive pattern.    
**`Lambda.V1E`**, **`Lambda.V2E`**, **`Lambda.R1E`**, **`Lambda.R2E`**, ...  are used plenty in our libraries.

* **[`momomo.com.platform.Return`](https://github.com/momomo/momomo.com.platform.Return)**  
An intuitive library that allows you to return multiple return values with defined types on the fly from any method rather than being limited to the default maximum of one.

* **[`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)**  
Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with `System.currentTimeMillis()`.

* **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)**  
A library to execute database command in transactions without having to use annotations based on Hibernate libraries. No Spring!

* **[`momomo.com.platform.db.transactional.Spring`](https://github.com/momomo/momomo.com.platform.db.transactional.Spring)**  
A library to execute database command in transactions without having to use annotations based on Spring libraries. 

### Background

The reason for writing this sample application was to showcase the **[`momomo.com.platform.db.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate)** API. 

But now we believe this is the beginning of an entire application platform coming as we continue to make available more and more of our libraries to the public.

### Getting started

The entire thing is really much much simpler to understand and navigate if you just **check out the entire repository**, **navigate and/or run** the examples. But we will try to guide you here as well.

We've decided to develope a **Crypto** related application!

Start by looking at

* **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**  
  Contains code for setting up the postgresql `database` and the Hibernate `SessionFactory` as well as provides a `CryptoTransactional` and `CryptoRepository` which will be used in our examples later. Is separated intentionally to show you the areas of responsibility. This is the implementation used in our running examples.  

* **[`CryptoMinimal.java`](src/momomo/com/example/CryptoMinimal.java)** is very similar to **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** but is densed downed to show you what the minimal working configuration actually would look like.

* **[`CryptoMaximal.java`](src/momomo/com/example/CryptoMaximal.java)** is very similar to **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** but contains more **examples and comments** on some things we can modify when setting up our `database` and the `SessionFactory`.  
  
```java                                               
// This is all code required to get started!

public class CryptoMinimal {
    
    private static final SessionFactory                SESSION_FACTORY = new CryptoSessionConfig().create();
    public  static final CryptoTransactionalRepository repository      = new CryptoTransactionalRepository();
    
    public static final class CryptoDatabase implements $DatabasePostgres {
        @Override public String name() {
            return "crypto_database_name_in_postgres";
        }
        
        @Override public String password() {
            return "postgres";
        }
    }
    
    public static final class CryptoSessionConfig extends $SessionConfig<CryptoDatabase> {
        public CryptoSessionConfig() {
            super(new CryptoDatabase());
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
    
    public static final class CryptoTransactionalRepository implements $SessionFactoryRepository, $TransactionalHibernate {
        @Override public SessionFactory sessionFactory() {
            return SESSION_FACTORY;
        }
    }
}
```  
   
Now that you've seen it, glanced it, consumed it, you may ***proceed***.

---

### Demonstration of the `Transactional` API 

Link to **[`$TransactionalHibernate`](https://github.com/momomo/momomo.com.platform.db.transactional.Hibernate/tree/master/src/momomo/com/db/%24TransactionalHibernate.java)**, **[`$Transactional`](https://github.com/momomo/momomo.com.platform.db.transactional/tree/master/src/momomo/com/db/%24Transactional.java)**, **[`$SessionFactoryRepository`](https://github.com/momomo/momomo.com.platform.db.base.jpa.session/blob/master/src/momomo/com/db/sessionfactory/$SessionFactoryRepository.java)** 

### Part one 

We start by looking at our **first entity** 

**[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)**
```java                                                       
@Entity ... public ... final class Bitcoin implements $Entity {
    
    @Id
    private UUID      id;
    private Timestamp time;
    private double    usd; // Represents the price in usd                  

```                                           

``` ... ```

```java
    public static final Service S = new Service(); public static final class Service {    
       /** 
        * Insert method that creates a bitcoin, then requires a transaction, and then saves it within the transaction, which then commits if it was the one to start it.  
        */                  
        public Bitcoin insert(Timestamp time, double usd) {
            // This "very very expensive" creation need not to run inside the transaction 
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

```java
return Crypto.repository.requireTransaction((tx) -> {
    return Crypto.repository.save(entity);
});
```                                                                                                                            

The `save(entity)` will execute within the `transaction` and when it terminates it will `commit the transaction` if it was the one who `started it`.

The **save call** could really be your own normal logic. If you use `Spring` you would use whatever you where you using, likely an `EntityManager` and `em.save(entity)`, and if `Hibernate` likey `session.saveOrUpdate(entity)`. Here reused our already created and **very capable** repository which eventually will call `session.saveOrUpdate(entity)` as well as ensure that it was saved properly and assigned an id as it should.

> Note!
> 
> To be able to use our repository we currently require that your `entities` implement our empty **[`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java)** interface.
>   
> We can see that this is no longer really required as the interface is indeed empty but this was a safety mechanism for our internal code once to ensure control as means of declaration but we will eventually remove this requirement from our `Repository` implementation to support any entity. 
>
> The Transactional API does not have that requirement, but the repository.save(..), repository.find(...) currently do.    

We can now invoke this method simply as `Bitcoin.S.insert(Time.stamp(), 10000.1)` from anyplace, even a **`static void main`** and we do: 

```java
public static void main(String[] args){
  Bitcoin.S.insert(Time.stamp(), 10000.1); // This will work, from any place. Even the command line, or to run or debug within your editor without any external requirements.    
}
```                                                                                                                                      

This will **trigger** the database generation, scan the entities, setup the `sessionFactory` and get you a transaction and eventually create and **save the data** to the database.s

You can find plenty more examples, some very complex in **[`PUBLICSTATICVOIDMAIN.java`](src/momomo/com/example/PUBLICSTATICVOIDMAIN.java)**.

To populate more entities at once, within one transaction we can do as `Bitcoin.Service#populate()` method: 

```java
Crypto.repository.requireTransaction(() -> {
    insert(Time.stamp(), mul * 100001);
    insert(Time.stamp(), mul * 100002);
    insert(Time.stamp(), mul * 100003);
    insert(Time.stamp(), mul * 100004);
    insert(Time.stamp(), mul * 100005);
});
```

### Part two

You've now seen the **`requireTransaction(()->{})`**, let us see what else can we do where we now focus on showing how things can be made **prettier** by creating a static inner class **[`CryptoService`](src/momomo/com/example/app/Crypto.java#L216)** **at the bottom** **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** with the implementation **being really simple**, and **minimal**: 

```java
public abstract static class CryptoService<T extends $EntityId> extends $Service<T> implements CryptoTransactional { /** That's it! **/ }
```                                                                                                                                      

We now take a look at pieces of code inside class **[`Etherum.Service`](src/momomo/com/example/app/entities/Etherum.java)** which this time around **`extends Crypto.CryptoService<Etherum>`** where **[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)** were plain.

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
 
requireTransaction(() -> {
    return save(entity);
});                              
```

```java
// We can demand a new transaction regardless of an existing one using

Crypto.repository.requireTransaction(() -> {
    return Crypto.repository.save(entity);
});
```

```java
// Now becomes 

requireTransaction(() -> {
    return save(entity);
});
```    

```java
// A read only transaction

List<Polkadot> = supportTransaction(() -> {
    return list(); // super.list() available from the super class $Service 
});   
```

```java
// We can disable auto commit

requireTransaction(($TransactionHibernate transaction) -> {
    // Disable autocommit, so we commit when we want or not at all
    transaction.autocommit(false);

    save(entity);

    transaction.commit();
});
```                                               

```java
// Another way to disable automatic commit
                                                              
requireTransaction((tx) -> {
    save(entity);     
    
   tx.commit();
}, false /** commit false**/ );
```

```java
// We can hook in to do something once the commit is succesful

requireTransaction((tx) -> {
    tx.afterCommit(() -> {
        // Send email perhaps when we exit the transaction after succesfully committing!
 
        // We have now inserted the value in our database successfully!

        // We do not want to send the email unless we actually have 100% inserted the stuff so this is a handy method to use for that 
    });

    save(entity);
});
```            

```java
// Rolling back inside a lambda possible                                                                            

newTransaction((tx) -> {
    save(entity);

    tx.rollback();

    // note, now, the autocommit won't occur, as we have rolled back and basically discarded the entire transaction.  
});
```

```java
// We can return something from inside the transaction, like an entity

Etherum e = requireTransaction(() -> {
    return save(entity); // We repeat the return demo but by returning an entity
});
```

```java
// Return again, showcasing that you can return anything really
 
String returns = requireTransaction(() -> {
    save(entity);

    return "we can return anything from the lambda";
});
```

```java
// A lambda less example, if we do not want to execute things inside a lambda but desire more freedom?
                                           
$TransactionHibernate tx = requireTransaction();
save(entity);
tx.autocommit(false);
tx.afterCommit  (()-> {});
tx.afterRollback(()-> { /* A crime has been committed! Report error to the FBI! */ });
tx.rollback();
tx.commit();
...
```          

```java
// We show that exceptions will bubble up to the caller
                                 
try {
    requireTransaction(() -> {
        throw new IOException();
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. 
    // If there is a rollback exception, a $DatabaseRollbackException will be thrown instead. 
    // Will not commit.  
}
```

```java
// We can return and throw exceptions
                       
try {
    File file = requireTransaction(() -> {
        if ( false ) {
            throw new IOException();
        }
        return new File("");
    });
} catch (IOException exception) {
    // Will bubble the exception to the caller (due to Lambda.VE, Lambda.V1E) after rolling back. 
    // If there is a rollback exception, a $DatabaseRollbackException will be thrown instead.
    // Will not commit.
}
```                                 

```java
// We can get access to the actual `session` and retain 100% control
                   
Session s1 = requireSession();
Session s2 = newSession();
```                                            

```java
// We can build the transaction properties and set various things ourselves

requireOptions()
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
// Similar to previous example but without chaining  
$TransactionOptionsHibernate options = requireOptions();
// ... options.propagation(...)
// ... options.create()
```

```java
// Getting a transaction that we can execute

$TransactionHibernate tx1 = requireOptions()
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
// More options made visible
                                      
requireOptions()
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

In **[`Stellar.Service`](src/momomo/com/example/app/entities/Stellar.java)** we can find:  

```java
/**
 * Bunch of complex and nested transaction.
 * 
 * Take a look at the comments within!
 */ 
public void populate(int multiplier) {
    newTransaction((tx1) -> {
        insert(Time.stamp(), multiplier * 101);
        insert(Time.stamp(), multiplier * 102);
        insert(Time.stamp(), multiplier * 103);
        insert(Time.stamp(), multiplier * 104);
        insert(Time.stamp(), multiplier * 105);
        
        // Start a new transaction within
        newTransaction(tx2 -> {
            insert(Time.stamp(), multiplier * 201);
            insert(Time.stamp(), multiplier * 202);
            insert(Time.stamp(), multiplier * 203);
            insert(Time.stamp(), multiplier * 204);
            insert(Time.stamp(), multiplier * 205);
            
            // Another one
            newTransaction(tx3 -> {
                insert(Time.stamp(), multiplier * 301);
                insert(Time.stamp(), multiplier * 302);
                insert(Time.stamp(), multiplier * 303);
                insert(Time.stamp(), multiplier * 304);
                insert(Time.stamp(), multiplier * 305);
            });
            
            // Continue on the previous last active one (same as tx2) 
            requireTransaction(tx4 -> {
                insert(Time.stamp(), multiplier * 401);
                insert(Time.stamp(), multiplier * 402);
                insert(Time.stamp(), multiplier * 403);
                insert(Time.stamp(), multiplier * 404);
                insert(Time.stamp(), multiplier * 405);
            });
            
            // Neither 201 ... or 401 ... will get into db since that tx rolledback
            tx2.rollback();
            
            // The same as tx1, no issues there. Should be in.   
            requireTransaction(tx5 -> {
                insert(Time.stamp(), multiplier * 501);
                insert(Time.stamp(), multiplier * 502);
                insert(Time.stamp(), multiplier * 503);
                insert(Time.stamp(), multiplier * 504);
                insert(Time.stamp(), multiplier * 505);
                
                // The same as tx1 and tx5, no issues there. Should enter db.    
                requireTransaction(($TransactionHibernate tx6) -> {
                    insert(Time.stamp(), multiplier * 601);
                    insert(Time.stamp(), multiplier * 602);
                    insert(Time.stamp(), multiplier * 603);
                    insert(Time.stamp(), multiplier * 604);
                    insert(Time.stamp(), multiplier * 605);
                    
                    // New transaction, will be in    
                    newTransaction(($TransactionHibernate tx7) -> {
                        insert(Time.stamp(), multiplier * 701);
                        insert(Time.stamp(), multiplier * 702);
                        insert(Time.stamp(), multiplier * 703);
                        insert(Time.stamp(), multiplier * 704);
                        insert(Time.stamp(), multiplier * 705);
                    });
                    
                    // New transaction, won't be in    
                    newTransaction(($TransactionHibernate tx8) -> {
                        insert(Time.stamp(), multiplier * 801);
                        insert(Time.stamp(), multiplier * 802);
                        insert(Time.stamp(), multiplier * 803);
                        insert(Time.stamp(), multiplier * 804);
                        insert(Time.stamp(), multiplier * 805);
                        
                        tx8.rollback();
                    });
                });
            });
        });
    });
}
```

### Part three

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
            $TransactionHibernate tx = requireTransaction();
    
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

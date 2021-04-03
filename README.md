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

### Part 1 

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

We comment on the code in method `insert()` 

```java
// We create the entity

Bitcoin entity = new Bitcoin()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
```                          

```java
// Then we require a write capable 'transaction' which means create a 'new transaction' if there is not already an ongoing one for this thread 

return Crypto.repository.requireTransaction((tx) -> {
    return Crypto.repository.save(entity);
});
```                                                                                                                            

The **`save(entity)`** call will execute within a `transaction` and when it terminates it will **commit the transaction** if it **was the one who started it**.

The **`save()`** call could really be your own normal logic. If you use `Spring` you would use whatever you where you using, likely using an `EntityManager` and calling `em.save(entity)`, and if `Hibernate` likey `session.saveOrUpdate(entity)`. 

Here we used our already created and **capable** repository which will eventually call `session.saveOrUpdate(entity)` and to ensure that it was saved properly, it checks it was assigned an `id` as it should which is not always the case. 

When using our `save()` we will also check to see if the entity has implemented **[`$Entity.Events`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java)** where your entity may implement `beforeSave()` and/or `afterSave()` logic which will be triggered before and/or after a `save()`. We use this sometimes to *set default values** on some fields or generate values based on other fields *to set a third*. 

> Note!
> 
> To be able to use our repository we currently require that your `entities` implement our empty **[`$Entity`](https://github.com/momomo/momomo.com.platform.db.base.jpa/tree/master/src/momomo/com/db/entities/$Entity.java)** interface.
>   
> We can see that this is no longer really required as the interface is indeed empty but this was a safety mechanism for our internal code once to ensure control as means of declaration but we will eventually remove this requirement from our `Repository` implementation to support any entity. 
>
> The Transactional API does not have that requirement, but the **`repository.save(..)`**, **`repository.find(...)`** currently do.    

We can now invoke this method simply as **`Bitcoin.S.insert(Time.stamp(), 10000.1)`** from anyplace, even from a plain **`static void main`**: 

```java                                   
// This will work, from any place. Even the command line, or to run or debug within your editor without any external requirements.

public static void main(String[] args){
  Bitcoin.S.insert(Time.stamp(), 10000.1);    
}
```                                                                                                                                      

This will **trigger** the database generation, **scan** the entities, setup the **`SessionFactory`** and get you a transaction and eventually create and **save** the one entity to the database.

To populate more entities at once, within one transaction we can do as **`Bitcoin.Service#populate()`** method: 

```java
Crypto.repository.requireTransaction(() -> {
    insert(Time.stamp(), 100001);
    insert(Time.stamp(), 100002);
    insert(Time.stamp(), 100003);
    insert(Time.stamp(), 100004);
    insert(Time.stamp(), 100005);
});
```

### Part 2

You've now seen the **`requireTransaction(()->{})`**, let us see what else can we do where we now focus on showing how things can be made **prettier** by creating a static inner class **[`CryptoService`](src/momomo/com/example/app/Crypto.java#L216)** **at the bottom** **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)** with the implementation **being really simple**, and **minimal**: 

```java
public abstract static class CryptoService<T extends $EntityId> extends $Service<T> implements CryptoTransactional { /** That's it! **/ }
```                                                            

Now take a look at **[`Polkadot.java`](src/momomo/com/example/app/entities/Polkadot.java)** for the minimal version of what the **[`Bitcoin.java`](src/momomo/com/example/app/entities/Bitcoin.java)** class could look like.                                                                           

```java                                      
// This you've seen from Bitcoin.java

Crypto.repository.requireTransaction(() -> {
    return Crypto.repository.save(entity);
});

// This is what we do now instead due to inheriting $Service<Etherum>
 
requireTransaction(() -> {
    return save(entity);
});
```

We now take a look at pieces of code inside class **[`Etherum.Service`](src/momomo/com/example/app/entities/Etherum.java)** which also **`extends`** **`Crypto.CryptoService<Etherum>`**.

```java
// Given 

Etherum entity = new Etherum()
    .setId(UUID.randomUUID())
    .setTime(time)
    .setUsd(usd)
;
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

$TransactionHibernate tx = requireOptions()
    .propagation($TransactionOptions.Propagation.NEW)
    .isolation($TransactionOptions.Isolation.REPEATABLE_READ)
    .timeout(1000)
    .create()
;
tx.execute(()-> {
    save(entity);
});

tx.execute(()-> {
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

### Part 3

While in Bitcoin, Etherum, Polkadot services, we required the transaction inside the insert method, in Stellar, we no longer use `requireTransaction()` because we put that burden for the caller to know the call needs a transaction to reduce boiler plate further. 

In reality all of our code no longer uses `requireTransaction` other than by the callers because the caller would know the entire transaction scope and likely would need to make more than one insert across multiple tables to create all of the things at the same time.

Using Spring, that caller would have had to extract those parts to fit neatly into a method, while the caller for us would just wrap them inside a lambda. 

So in **[`Stellar.Service`](src/momomo/com/example/app/entities/Stellar.java)** we can now find: 

```java
public Stellar insert(Timestamp time, double usd) {
    return save( create().setTime(time).setUsd(usd) ); 
}
```

which expects the caller to do the following when calling: 

```java
Crypto.repository.requireTransaction(()-> {
    Stellar.S.insert(Time.stamp(), 999);
}); 
```

In **[`Stellar.Service`](src/momomo/com/example/app/entities/Stellar.java)** we can also find more complex, and working **unrealistic** example:  

```java
/**
 * Bunch of complex and nested transactions. 
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
In **[`Stellar.Service`](src/momomo/com/example/app/entities/Stellar.java)** we also added a couple more methods:

```java
// Return all the historic data within polkadot table

public List<Stellar> historic() {
    return supportTransaction(()-> {
        return super.list();
    });
}            
```

```java
// Return the data within time range

public List<Stellar> range(Timestamp from, Timestamp to) {
    return supportTransaction(()-> {
        return list(criteria()
            .add( Restrictions.ge(Cons.time, from) )
            .add( Restrictions.le(Cons.time, to  ) )
        );
    });
}
```

#### Part four

If we now look at **[`PUBLICSTATICVOIDMAIN`](src/momomo/com/example/PUBLICSTATICVOIDMAIN.java)** we can find a `static void main` and some code ready to run the entire thing.

```java
public static void main(String[] args) {
    Bitcoin.S.populate(1);
    Polkadot.S.populate(1);
    Stellar.S.populate(1);

    {
        $TransactionHibernate tx = Crypto.repository.requireTransaction();
    
        Bitcoin.S.populate(10);
        Polkadot.S.populate(10);
    
        tx.rollback();      // We roll back!
    }
}
```

When we run this static void main we will eventually find the **following in our database**:  

![Generated tables](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.tables.2021.04.03.V1.jpg?raw=true)                

   * ***bitcoin table***  
   ![Bitcoin table](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.bitcoin.table.2021.04.03.V1.jpg?raw=true)        
   
   * ***polkadot table***  
   ![Polkadot table](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.polkadot.table.2021.04.03.V1.jpg?raw=true)        
   
   * ***stellar table***  
   ![Stellar table](https://github.com/momomo/momomo.com.github.statics/blob/master/momomo.com.example.app.Crypto/graphics/database.stellar.table.2021.04.03.V1.jpg?raw=true)        
           

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

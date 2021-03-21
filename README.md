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

* **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**  
Think of this class as your main application class.   
If you where developing a **Tv** application, maybe you would have a **`Tv.java`**.  
If you where developing a **Store**, maybe you would have a **`Store.java`**.  
If you where developing a **Finance**, maybe you would have a **`Finance.java`**.  
&nbsp;  
Now, we've put for simplicity **everything** that is related to configuration or setups, or access to anything inside this class, **[`Crypto.java`](src/momomo/com/example/app/Crypto.java)**.   
**[`Just visit it!`](src/momomo/com/example/app/Crypto.java)**. Things like our       
  
  




### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.

---
layout: manual
active: manual
title: Sbt Neo Dependencies - manual
---

## Installation

If you are using sbt as your build tool, [Maven Central](https://repo1.maven.org/maven2/) is included by default.
As such, you don't need to add any resolvers to your build definition. You can simply add the scamandrill client
in your build definition **libraryDependencies**.

##### For Play Framework 2.5.x

~~~ scala
"io.github.scamandrill" %% "scamandrill" % "{{ site.version25 }}"
~~~

##### For Play Framework 2.4.x

~~~ scala
"io.github.scamandrill" %% "scamandrill" % "{{ site.version24 }}"
~~~


##### For akka-http users

It should be noted that play-ws 2.5.* is built on akka-http. If you can use the version of akka-http included as a
transitive dependency of this library, you should use version {{ site.version25 }}. If not, use {{ site.version24 }}
which uses play-ws 2.4.x which is based on netty.io rather than akka.http.



All versions for this plugin can be found [here]({{ site.baseurl }}/versions).

If you are unsure about where to configure your library dependencies, read [this](http://www.scala-sbt.org/release/docs/Getting-Started/Full-Def.html).

## Configuration

In order to make requests using the mandrill client, you must supply an API key.
There are several ways to make an API key available to the client:


##### Add the API key to application.conf

~~~ scala
mandrill.key = "your mandrill api key"
~~~

_then_

~~~ scala
import io.github.scamandrill.client.Scamandrill
...
val scamandrill = Scamandrill()
val client = scamandrill.get()
~~~


##### Specify a key on a client-by-client basis**

~~~ scala
import io.github.scamandrill.client.Scamandrill
...
val scamandrill = Scamandrill()
val client = scamandrill.getClient("your mandrill api key")
~~~

##### Specify a key in a Configuration class

~~~ scala
import io.github.scamandrill.client.Scamandrill
import play.api.Configuration
...
val scamandrill = Scamandrill(Configuration(
  "mandrill.key" -> "your mandrill api key"
))
val client = scamandrill.getClient()
~~~


## Usage

The following is a very basic example of sending a message using scamandrill.
All api calls are invoked in a similar manner to this example, by passing in a special 
purpose object and recieving a `Future[Try[_]]` in response.

~~~ scala
package io.github.scamandrill.test

import io.github.scamandrill.client.Scamandrill
import play.api.Configuration
import io.github.scamandrill.client.implicits._
import io.github.scamandrill.models._


val scamandrill = Scamandrill(Configuration(
  "mandrill.key" -> "your mandrill api key"
))

/** This is an example of sending an email using scamandrill **/
val result: Future[Try[List[MSendResponse]]] = scamandrill.messagesSend(MSendMessage(
  message = new MSendMsg(
    html = "<p>Example HTML content</p>",
    text = "Example text content",
    subject = "example subject",
    from_email = "message.from_email@example.com",
    from_name = "Example Name",
    to = List(MTo(
      email = "recipient.email@example.com",
      name = "Recipient Name".?
    )),
    headers = MHeaders("Reply-To" -> "message.reply@example.com").?,
    merge = true,
    global_merge_vars = List(MVars("merge1", JsString("merge1 content"))),
    merge_vars = List(
      MMergeVars(
        "recipient.email@example.com",
        List(
          MVars("merge2", "merge2 content")
        )
      )
    ),
    tags = List("password-resets"),
    subaccount = "customer-123".?,
    metadata = MMetadata("website" -> "www.example.com").?,
    recipient_metadata = List(
      MRecipientMetadata(
        "recipient.email@example.com",
        MMetadata("user_id" -> 123456)
      )
    )
  )
))

~~~


## Examples
Examples for every supported API call can be found in the [tests](https://github.com/{{ site.github.author }}/{{ site.github.project }}/tree/master/src/test/scala/io/github/scamandrill/client).
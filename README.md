# pageseeder-webhook-client
Has common logic to handle the requests sent by pageseeder (webhook) and search for a class responsible to handle it (uses Java SPI)

## Your project setup

- firstly, you should create you event handler and the application class should implement this interface org.pageseeder.webhook.client.handler.EventHandler. 

- secondly is to create this file src/main/resources/META-INF/services/org.pageseeder.webhook.client.handler.EventHandler
and add the application event handler should be listed in mentioned file.

- thirdly the Serverlet responsible to receive the webhook request should be add to the application web.xml.

- finally a webhook should be created in pageseeder.

# Example Project

There is a sub-project which is a webapp application called webhook-client-example. It is possible to execute it by 
using gradle->gretty->startApp. The [home page](https://localhost:8444/webhook/home.html?berlioz-reload=true) shows a
list of event received.

However to reveice an event, a webhook should be created on your local Pageseeder.

- url: http://localhost:8999/webhook/receiver.html
- Events Filter: member.*,uri.*

## SPI setup

There are 3 classes created for this case and they are:

- org.pageseeder.webhook.client.example.handler.CustomPingEventHandler
- org.pageseeder.webhook.client.example.handler.MemberEventHandler
- org.pageseeder.webhook.client.example.handler.URIEventHandler

And the config:

- src/main/resources/META-INF/services/org.pageseeder.webhook.client.handler.EventHandler
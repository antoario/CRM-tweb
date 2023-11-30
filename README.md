# CRM-tweb

## Docker

Allora prima di tutto devi aprire la cartella del progetto e fare il build del dockerfile con il comando:

```bash
docker compose build
docker compose up
```

questo costruirà le immagine e i container che sono 4:

- db (postgres)
- [admiter](http://localhost:5050/) 
- [angular](http://localhost:4300/)
- [tomcat](http://localhost:8080/crmtweb/)

> ovviamente non vanno se i container sono spenti (o non ci sono)

⚠️ se tomcat ti dà errore 404 perchè nella tua cartella non c'è la cartella target compilala (ti ho inviato le foto su whatsapp) poi stoppa (il quadrato) e fai ripartire il container di tomcat. vedrai che funziona.

⚠️⚠️⚠️ se fai docker compose quando NON hai la cartella target nel backend non ti dà errore (ma non ci sarà nulla), appena lo riavvii ti dà errore. per risolvere devi creare il target (fare il build) e fare 
```bash
docker-compose down
docker-compose up
```

Guarda docker per capire gli url. Sono già tutti configurati.

## Tomcat

Per tomcat hai già tutto configurato su docker l'unica cosa che devi fare se vuoi lavorare sul tuo pc è andare sulla cartella dove hai installato tomcat e andare a modificare il `context.xml` che si trova in `conf/context.xml` e sostituirlo con questo:

```xml

<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<!-- The contents of this file will be loaded for each web application -->
<Context>

    <!-- Default set of monitored resources. If one of these changes, the    -->
    <!-- web application will be reloaded.                                   -->
    <WatchedResource>WEB-INF/web.xml</WatchedResource>
    <WatchedResource>WEB-INF/tomcat-web.xml</WatchedResource>
    <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>

    <!-- Uncomment this to enable session persistence across Tomcat restarts -->
    <!--
    <Manager pathname="SESSIONS.ser" />
    -->

    <Resource name="jdbc/postgres" auth="Container"
              type="javax.sql.DataSource" driverClassName="org.postgresql.Driver"
              url="jdbc:postgresql://localhost:5432/postgres"
              username="admin" password="admin"
              maxTotal="20" maxIdle="10" maxWaitMillis="-1"/>
</Context>

```

in questo modo ti si connette al server senza quella 💩 che avevamo visto. Se vuoi vedere come connetterlo guarda la classe che hai cambiato ieri, l'ho cambiata io. Così direi che è molto meglio.

Ho tolto il login non so come si faccia ed è troppo tardi per guardarla ora ma non dovrebbe essere complicato, cerca di andare avanti con il resto anche in maniera basilare.

### Guardarlo su tomcat

Tutte le volte che farai una build docker prenderà il file war più recente quindi non dovrai fare nulla. basta buildarlo e stoppare e riavviare il container di tomcat.

### intellij

non ti serve scaricare tomcat smart ma devi impostare la cartella giusta dalle impostazioni ti invio le foto su whatsapp.

## Angular

Non ti preoccupare guardo io

## 😴

Buonanotte
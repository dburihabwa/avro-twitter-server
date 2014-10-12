#   avro-twitter

### 2014-10-13 | Dorian BURIHABWA

Avro-twitter est une implémentation basique d'un service similiare à Twitter reposant sur avro.

##  Construire le projet

Pour compiler le projet, lancez la commande suivante:

```bash
    $ mvn install
```

##  Serveur
Une fois le projet construit, le serveur peut être lancé avec la commande suivante.

```bash
    $ java -jar target/avro-twitter-1.0-SNAPSHOT.jar
```
Le programme se lancera en démarrant un serveur HTTP sur le port 42000.

##  Client

Le client, écrit en python, permet d'accèder à 2 fonctionnalités:

* Envoyer un tweet
* Lister les tweets envoyés par un utilisateur

Pour lancer les commandes suivantes, veuillez vous placer dans le répertoire *src/main/python*.

### Envoyer un tweet

Lancer le client avec *tweet* en argument.

```bash
    $ python client.py tweet
    Please enter your handle :	PrisonMike             
    Please enter your message :	I have stolen
    @PrisonMike	(2014-10-13T01:00Z)	I have stolen

```

### Lister les utilisateurs

Lancer le client avec *list* en argument.

```bash
    $ python client.py list
    Please the handle of the user whose tweets you would like to read :	PrisonMike
    @PrisonMike	(1413152787629)	I have stolen
    @PrisonMike	(1413152798059)	I Have killed
```


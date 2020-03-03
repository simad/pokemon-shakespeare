Ever wondered how your favorite Pokemon would sound like, if described by Shakespeare? Me neither. Well, let's find out anyway :D
#### Prerequisites

Installed: [Docker](https://www.docker.com/), [Java 1.8 or 1.11](https://www.oracle.com/technetwork/java/javase/overview/index.html), [Maven 3.x](https://maven.apache.org/install.html), [git](https://www.digitalocean.com/community/tutorials/how-to-contribute-to-open-source-getting-started-with-git), optional [Docker-Compose](https://docs.docker.com/compose/install/)

#### Steps

##### Clone source code from git
```
$  git clone https://github.com/simad/pokemon-shakespeare .
```

## Run with docker-compose 

Build and start the container by running 

```
$ docker-compose up -d 
```

##### Run unit tests:
```
mvn test
```

##### Run integration tests:
```
mvn integration-test
```

##### Test application

```
$ curl localhost:8080/pokemon/charizard
```

the response should be:
```
{"name":"charizard","description":"Charizard flies 'round the sky in search of powerful opponents. It breathes fire of such most wondrous heat yond 't melts aught. However 't nev'r turns its fiery breath on any opponent weaker than itself."}
```

##### Stop Docker Container:
```
docker-compose down
```

Alternatively to docker compose:
##### Build Docker image
```
$ docker build -t="pokemon-shakespeare" .
```

>Note:if you run this command for first time it will take some time in order to download base image from [DockerHub](https://hub.docker.com/)

##### Run Docker Container
```
$ docker run -p 8080:8080 -it --rm pokemon-shakespeare
```

#####  Stop Docker Container:
```
docker stop `docker container ls | grep "pokemon-shakespeare:*" | awk '{ print $1 }'`
```
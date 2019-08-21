
# Pre-requisities
- Physical or Virtual machine of Linux distribution.
- Install [Docker](https://github.com/docker).
- Install [docker-compose](https://docs.docker.com/get-started/).



# Docker Compose

![N|Solid](https://github.com/docker/compose/raw/master/logo.png?raw=true)

Compose is a tool for defining and running multi-container Docker applications.
With Compose, you use a Compose file to configure your application's services.
Then, using a single command, you create and start all the services
from your configuration. To learn more about all the features of Compose
see [the list of features](https://github.com/docker/docker.github.io/blob/master/compose/overview.md#features).

Compose is great for development, testing, and staging environments, as well as
CI workflows. You can learn more about each case in
[Common Use Cases](https://github.com/docker/docker.github.io/blob/master/compose/overview.md#common-use-cases).

Using Compose is basically a three-step process.

1. Define your app's environment with a `Dockerfile` so it can be
reproduced anywhere.
2. Define the services that make up your app in `docker-compose.yml` so
they can be run together in an isolated environment.
3. Lastly, run `docker-compose up` and Compose will start and run your entire app.

For more information about the Compose file, see the
[Compose file reference](https://github.com/docker/docker.github.io/blob/master/compose/compose-file/compose-versioning.md).

Compose has commands for managing the whole lifecycle of your application:

 * Start, stop and rebuild services
 * View the status of running services
 * Stream the log output of running services
 * Run a one-off command on a service

Installation and documentation
------------------------------

- Full documentation is available on [Docker's website](https://docs.docker.com/compose/).
- Code repository for Compose is on [GitHub](https://github.com/docker/compose).

# PII Engine Installation

To install PII Engine follow the next steps:
1. Create a folder named `sfs` in the root filesystem of your operating system. This folder will be used to upload the desired datasets.
2. Create a folder named piiengine.
3. Copy the the PII Engine [docker-compose.yml](https://github.com/SCiO-systems/CGIAR-BDP-GARDIAN/blob/master/piistack/docker-compose.yml) to the piiengine folder.
3. Copy the  [variables.env](https://github.com/SCiO-systems/CGIAR-BDP-GARDIAN/blob/master/piistack/variables.env) to the piiengine folder.
4. Execute the following command

```sh
$host= {IP address} docker-compose -f {docker-compose.yml path} up
```
 {IP address}: The IP of the VM or physical machine that will host the PII Engine. Keep in mind that PII Engine and all its components are hosted in a single machine.

The Pii Engine image is in [Docker Hub](https://cloud.docker.com/u/sciogardian/repository/docker/sciogardian/piiengine).

# [docker-compose.yml](https://github.com/SCiO-systems/CGIAR-BDP-GARDIAN/blob/master/piistack/docker-compose.yml)

    version: '2.2'
    services:
      zookeeper:
        image: scioquiver/zookeeper:default
        container_name: Zookeeper
        ports:
          - 2181:2181
        environment:
          - ${host}
      elasticsearch:
        image: scioquiver/elasticsearch:ontologies
        container_name: Elastic
        ports:
          - 9200:9200
        environment:
          - ${host}
        network_mode: "host"
      mongo:
        image: mongo:3.4.19
        container_name: Mongo
        ports:
          - 27017:27017
        environment:
          - ${host}
        network_mode: "host"
      piiEngine:
        image: sciogardian/piiengine:0_5.camel
        container_name: piiEngineSfs
        command: ${host}
        network_mode: "host"
        volumes:
          - /sfs:/sfs
      piiUserInterface:
        image: sciogardian/piiuserinterface:0_5.play
        container_name: piiUiSfs
        ports:
          - 9000:9000
          - 9443:9443
        environment:
          - ${host}
        command: ${host}
        depends_on: 
          - elasticsearch
          - mongo
          - piiEngine
          - kafka
      kafka:
        image: scioquiver/kafka:default
        container_name: Kafka
        ports:
          - 9092:9092
        environment:
          KAFKA_ADVERTISED_HOST_NAME: ${host}
          KAFKA_ZOOKEEPER_CONNECT: ${host}:2181
        network_mode: "host"
        depends_on: 
          - zookeeper
    



List of Docker Images
------------------------------
| Component | Location |
| ------ | ------ |
| Elasticsearch | [scioquiver/elasticsearch:ontologies](https://hub.docker.com/r/scioquiver/elasticsearch) |
| Kafka | [scioquiver/kafka:default](https://hub.docker.com/r/scioquiver/kafka) |
| Zookeeper | [scioquiver/zookeeper:default](https://hub.docker.com/r/scioquiver/zookeeper) |
| Mongo | [mongo:3.4.19](https://hub.docker.com/_/mongo) |
| PiiEngine | [sciogardian/piiengine:0_5.camel](https://cloud.docker.com/u/sciogardian/repository/docker/sciogardian/piiengine) |
| PiiUserInterface | [sciogardian/piiuserinterface:0_5.play](https://cloud.docker.com/u/sciogardian/repository/docker/sciogardian/piiuserinterface) |


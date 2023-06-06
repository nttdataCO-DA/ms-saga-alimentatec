# Ejemplo Patron Saga - Cadence Workflow
Este repositorio contiene un proyecto Java + Spring Boot que expone un API Rest para inicializar un Workflow de Cadence que implementa el patron Saga, Cadence cuenta con clases e interfaces que facilitan la implementacion de este patron y ademas mediante Cadence Web se puede visualizar facilmente la ejecucion de cada instancia de flujo y de cada una de sus actividades

### Requisitos

- Java JDK 11 o Superior
- Apache Maven
- Docker Compose
- Git 
- Acceso a Internet

### Instalacion Cadence
Para ejecutar esta aplicacion, es necesario tener instalado [Cadence Workflow](https://cadenceworkflow.io/) de manera local. Para Instalarlo, descargue el archivo [docker-compose.yml](https://github.com/nttdataCO-DA/ms-saga-alimentatec/blob/main/docker-compose.yml) y ejecute el siguiente comando:

```sh
docker-compose up -d
```


Para verificar que Cadence se este ejecutando ingrese a http://localhost:8088/

### Ejecucion del ms
clone el repositorio e importelo como proyecto maven en su IDE Favorito

### Ejecucion del ms
**Compilar el proyecto con el comando:**

```sh
mvn clean install
```
**Ejecutar el proyecto de Spring-boot que contiene el API Rest para crear el pedido**
```sh
mvn spring-boot:run
```
**Ejecutar El workflow con el siguiente comando**
```sh
mvn exec:java -Dexec.mainClass="com.nttdata.da.app.CustomerApplication"
```
### Probar la funcionalidad

Ejecute la siguiente peticion curl o descargue la [coleccion de postman](https://github.com/nttdataCO-DA/ms-saga-alimentatec/blob/main/PatronSaga-Cadence.postman_collection.json)

```sh
curl --location 'http://localhost:8080/orders/async' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "customer_12345",
    "amount": 3000

}'
```
Ingrese a Cadence Web, al dominio Example, ingrese en la ejecucion del workflow para ver el detalle de las actividades


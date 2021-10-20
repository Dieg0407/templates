# About
Proyecto interno que se encarga de ejecutar rutinas diarias relacionadas 
a incidencias de ecommerce variadas.

Usa una conexión a un H2 en memoria para registrar el control de lo ejecutado
en el día  y una conexión a Oracle para ejecutar los procesos.

# Configuration

```yaml

spring:
  jpa:
    database: default
    hibernate:
      ddl-auto: none
  h2:
    console:
      enabled: true
      path: /h2-console
      trace: false
      web-allow-others: false

# Console In memory
console:
  datasource:
    jdbc-url: jdbc:h2:mem:console
    driverClassName: org.h2.Driver
    username: user
    password: pass

# Oracle ecommerce
ecom:
  datasource:
    jdbc-url: jdbc:oracle:thin:@<host>:<port>/<service>
    driverClassName: oracle.jdbc.OracleDriver
    username: <user>
    password: 
```
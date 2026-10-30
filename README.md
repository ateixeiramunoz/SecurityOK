# Spring Security: ejemplos de aplicación web

Proyecto docente que reúne autenticación, vistas Thymeleaf y ejemplos de foro, chat, subida de ficheros y consulta de películas.

## Tecnologías

El [pom.xml](pom.xml) declara Java 21 y Spring Boot 3.5.0, con Spring Security, Spring Web, Thymeleaf, JPA, H2, WebSocket y correo.

## Mapa del código

Las funciones se agrupan dentro de [com.atm.security](src/main/java/com/atm/security/):

- `config`: configuración web y reglas de acceso.
- `entities`, `repositories` y `services`: usuarios y autenticación.
- `foro`: canales e hilos.
- `chat`: mensajes y WebSocket.
- `upload`: almacenamiento de ficheros.
- `email`: envío de correo.
- `APICARATULAS`: ejemplo de búsqueda de películas.

La [configuración de seguridad](src/main/java/com/atm/security/config/MySecurityConfig.java) permite consultar qué rutas son públicas y cuáles requieren autenticación.

## Ejecución local

1. Selecciona JDK 21 e importa el proyecto como Maven.
2. Revisa [application.properties](src/main/resources/application.properties), especialmente la configuración de correo.
3. Arranca `SecurityApplication` desde el IDE o utiliza el wrapper de Maven:

```powershell
.\mvnw.cmd spring-boot:run
```

En una shell compatible, el comando equivalente es `./mvnw spring-boot:run`.

## Alcance

Colección de ejemplos educativos. La presencia de una función no implica que sus reglas de acceso sean adecuadas para desplegarla tal cual en producción.

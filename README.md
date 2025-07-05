Proyecto Perfulandia - Arquitectura de Microservicios
Descripción del Proyecto

Este proyecto consiste en el desarrollo del backend para una tienda e-commerce de perfumes llamada "Perfulandia". La arquitectura está diseñada bajo el paradigma de microservicios, donde cada dominio de negocio principal está desacoplado en su propio servicio independiente.

El objetivo principal fue construir una base sólida y profesional, implementando herramientas clave para la calidad, documentación y mantenibilidad del software, como Swagger/OpenAPI para la documentación de APIs y JUnit/Mockito para la creación de pruebas unitarias robustas.

Arquitectura y Tecnologías

El proyecto se compone de 4 microservicios desarrollados con las siguientes tecnologías:

Lenguaje: Java 17

Framework: Spring Boot 3.x

Build Tools: Maven (para Usuario Service) y Gradle (para los demás)

Base de Datos: MySQL (cada microservicio tiene su propia base de datos aislada)

Documentación API: Swagger / OpenAPI 3

Pruebas: JUnit 5 y Mockito

Microservicios Creados

Usuario Service (Puerto 8080)

Responsabilidad: Gestiona toda la información de los usuarios (CRUD: Crear, Leer, Actualizar, Eliminar).

Es el punto de entrada para la autenticación y gestión de clientes.

Producto Service (Puerto 8084)

Responsabilidad: Administra el catálogo y el inventario de productos.

Controla operaciones como la consulta de productos, creación y la actualización de stock.

Carrito Service (Puerto 8081)

Responsabilidad: Gestiona los carritos de compra de cada usuario.

Permite agregar, listar y eliminar ítems del carrito de un usuario específico.

Pedido Service (Puerto 8083)

Responsabilidad: Orquesta el proceso de finalización de una compra.

Crea un pedido a partir del carrito de un usuario, se comunica con el Producto Service para descontar el stock y con el Carrito Service para vaciarlo.

Instrucciones de Uso Local

Para ejecutar el proyecto en un entorno local, sigue estos pasos:

1. Prerrequisitos

Tener instalado un JDK 17 o superior (no mayor a JDK 23).

Tener instalado y corriendo un servidor de MySQL.

Un IDE como IntelliJ IDEA o VSCode con soporte para Java/Spring.

2. Configuración de la Base de Datos

Cada microservicio requiere su propia base de datos. Ejecuta los siguientes comandos en tu cliente de MySQL:

Generated sql
CREATE DATABASE perfulandia_usuarios_db;
CREATE DATABASE perfulandia_productos_db;
CREATE DATABASE perfulandia_carritos_db;
CREATE DATABASE perfulandia_pedidos_db;

3. Configuración de los Microservicios

Revisa el archivo src/main/resources/application.properties dentro de cada proyecto de microservicio y asegúrate de que las credenciales de la base de datos (spring.datasource.username y spring.datasource.password) coincidan con las de tu configuración local de MySQL.

4. Ejecución

Abre la carpeta del proyecto en IntelliJ IDEA.

Espera a que Maven y Gradle sincronicen todas las dependencias.

Ejecuta cada microservicio de forma individual. Para ello, busca el archivo ...Application.java en cada servicio (ej. UsuarioApplication.java) y ejecútalo.

Documentación de API (Swagger)

Una vez que los microservicios estén en ejecución, puedes acceder a la documentación interactiva de la API para cada uno de ellos a través de los siguientes enlaces:

Usuario Service: http://localhost:8080/swagger-ui.html

Producto Service: http://localhost:8084/swagger-ui.html

Carrito Service: http://localhost:8081/swagger-ui.html

Pedido Service: http://localhost:8083/swagger-ui.html

(Nota: los puertos pueden variar si se modificaron en los archivos application.properties)

Pruebas Unitarias (JUnit + Mockito)

Para garantizar la calidad del código, cada microservicio cuenta con una suite de pruebas unitarias para la capa de servicio.

Para ejecutar las pruebas:

En IntelliJ, navega a la carpeta src/test/java de cualquier microservicio.

Busca la clase de prueba (ej. UsuarioServiceTest.java).

Haz clic derecho sobre el nombre de la clase y selecciona "Run '...ServiceTest'".

Los resultados se mostrarán en la pestaña de Pruebas, indicando si la lógica de negocio funciona como se espera.

Proyecto: Transformación Digital - Perfulandia SPA Este repositorio contiene el desarrollo técnico del sistema basado en microservicios para la empresa Perfulandia SPA, como parte de la Evaluación Parcial 2 de la asignatura Desarrollo Full Stack I. Al igual que un archivo extra para poder manejar dentro de Postman, el cual permite facilmente manejar las funcionalidades de cada microservicio.

Descripción General del Proyecto: El sistema presentado es para resolver los problemas actuales de Perfulandia, el cual maneja sus usuarios, productos, el carro de compras de los usuarios y pedidos. El objetivo de cambiar el modelo original que usaba perfulandia, es para poder mejorar el rendimiento, la escalabilidad y mantenibilidad.

Microservicios Desarrollados:
- UsuarioService: Este microservicio maneja los usuarios, pudiendo crear, borrar y actualizar usuarios.
- ProductoService: Este microservicio maneja los productos, pudiendo agregar, borrar, y actualizarlos.
- CarritoService: Este microservicio maneja los carritos de los usuarios, donde se podran agregar distintos productos, pudiendo agregar productos al carrito, listar los productos en el carro y borrar todos los productos del carrito.
- PedidoService: Este microservicio maneja los pedidos, confirmando los productos que un usuario tiene en su carrito, pudiendo listar los pedidos, confirmarlos, y cambiar el estado de estos entre Generado, Enviado y entregado.

Tecnologías Utilizadas:
- Mysql
- Laragon
- Postman
- Intellij

Configuración de Bases de Datos: Este proyecto fue configurado con MySQL en Largon, con puerbas unitarias en Postman para verificar su funcionalidad, cada microservicio accesible con un puerto distinto para evitar conflictos entre si.

Integrantes del Equipo::
Diego Carrillo/Eric Saavedra - usuarioservice 
Diego Carrillo/Eric Saavedra - productoservice 
_____________________ - CarritoService
_____________________ - PedidoService

La estructura del Repositorio en cada carpeta corresponde a un microservicio separado de forma individual, lo cual facilita su lectura, y evita confusion entre servicios.

perfulandia_dev/microservicios
├── Usuario
├── Producto 
├── Carrito 
├── Pedido


Colaboración en GitHub:
La colaboracion fue bastante fluida y planificada, con el unico problema siendo al momento de la creacion inicial del repositorio, y la subida de los primeros dos servicios ya creados desde antes. Esto es debido
a que el encargado de tal tarea (Eric), carece de mayor experiencia con github, causando multiples complicaciones al inicio, pero fluyendo bien despues de lograr el crear el repositorio y tenerlo listo para el trabajo colaborativo.
El uso de los commits y merges no fue un problema para ninguno de los involucrados, los unicos incovenientes siendo antes del comienzo de la parte colaborativa en github.

Lecciones Aprendidas:
Reflexionar brevemente sobre qué aprendieron durante el desarrollo del proyecto (técnico y en trabajo en equipo).
- Eric: Aprendi bastante sobre como crear y configurar ramas, repositorios y la clonacion de datos entre repositorios como tal.

# Guía rápida de defensa - Proyecto Microservicios

## Idea general del proyecto

El proyecto está dividido en microservicios Spring Boot separados por carpeta. Cada uno tiene su propia base de datos, su propio `pom.xml`, sus controladores REST, su capa de servicio, repositorio, DTO y manejo básico de errores.

La idea que debes explicar en la defensa es esta:
- Cada microservicio resuelve un dominio pequeño.
- Ningún microservicio debe acceder directamente a la base de datos de otro.
- Si un servicio necesita datos de otro, lo correcto es consumir un endpoint REST y usar DTOs para transportar la información.

## Estructura base común de cada microservicio

Todos siguen una estructura parecida:
- `pom.xml`: dependencias Maven.
- `src/main/java`: código Java.
- `src/main/resources/application.properties`: configuración del puerto y la base de datos.
- `src/test/java`: prueba básica de arranque.
- `controller`: recibe peticiones HTTP.
- `service`: contiene la lógica.
- `repository`: accede a la base de datos.
- `model`: entidades JPA.
- `dto`: objetos para entrada/salida y validación.
- `exception`: manejo centralizado de errores.

## Qué decir de cada microservicio

### 1. ms-auth
Qué hace:
- Registro de usuarios.
- Login.
- Manejo base de autenticación y roles.

Qué decir en defensa:
- Este servicio centraliza el acceso.
- Su responsabilidad es autenticar usuarios, no gestionar productos o pacientes.
- Expone endpoints como registro y login.

Qué modificarían normalmente:
- Agregar un campo nuevo al usuario.
- Cambiar validaciones.
- Ajustar la respuesta del login.

### 2. ms-productos
Qué hace:
- CRUD de productos médicos o insumos.

Qué decir en defensa:
- Guarda información del catálogo.
- Usa DTO para validar entrada.
- El controller solo recibe y responde; el service hace la lógica.

Qué modificarían normalmente:
- Nuevo campo como `marca` o `categoria`.
- Búsqueda por nombre.
- Validación de stock mínimo.

### 3. ms-pacientes
Qué hace:
- CRUD de pacientes.
- Registro de datos de contacto.
- Endpoint extra por RUT para que otro servicio lo consulte.

Qué decir en defensa:
- Este microservicio administra los datos del paciente.
- Se usa como servicio base para otras consultas.
- Tiene endpoint por RUT para integración con pedidos.

Qué modificarían normalmente:
- Agregar dirección, edad o sexo.
- Agregar búsqueda por email.
- Cambiar la respuesta de un paciente.

### 4. ms-inventario
Qué hace:
- CRUD de stock físico en bodega.

Qué decir en defensa:
- Controla unidades y ubicación.
- Sirve para saber qué hay disponible.
- Mantiene separación funcional con productos.

Qué modificarían normalmente:
- Campo de ubicación o estado.
- Validación de stock negativo.
- Descuento de stock por compra.

### 5. ms-pedidos
Qué hace:
- CRUD de pedidos.
- Consulta a ms-pacientes por RUT para enriquecer la respuesta.

Qué decir en defensa:
- Este es el ejemplo más importante de interacción entre microservicios.
- No toca la base de datos de pacientes.
- Hace una llamada HTTP al endpoint de pacientes y arma un DTO de respuesta con el paciente incluido.

Qué modificarían normalmente:
- Agregar estado del pedido.
- Agregar fecha.
- Validar que el paciente exista.

### 6. ms-recetas
Qué hace:
- CRUD de recetas médicas.
- Puede servir para validar folios.

Qué decir en defensa:
- Se relaciona con el flujo de comercio médico.
- Puede usarse para validar si una receta está vigente o fue usada.

Qué modificarían normalmente:
- Agregar folio.
- Agregar estado de validación.
- Agregar fecha de vencimiento.

### 7. ms-proveedores
Qué hace:
- CRUD de laboratorios o distribuidores.

Qué decir en defensa:
- Representa a los proveedores del sistema.
- Ayuda a mantener trazabilidad de insumos y medicamentos.

Qué modificarían normalmente:
- Agregar tipo de proveedor.
- Campo región o país.
- Búsqueda por nombre.

### 8. ms-pagos
Qué hace:
- Simulación de transacciones o pagos.

Qué decir en defensa:
- No hace pago real bancario.
- Solo simula el flujo para la evaluación.
- Sirve para asociar un pago a un pedido.

Qué modificarían normalmente:
- Estado del pago.
- Método de pago.
- Validación de monto.

### 9. ms-envios
Qué hace:
- Seguimiento del despacho a domicilio.

Qué decir en defensa:
- Controla envío y estado de entrega.
- Puede conectarse con pedidos.
- Da trazabilidad del delivery.

Qué modificarían normalmente:
- Cambiar estado de envío.
- Agregar fecha de salida o entrega.
- Búsqueda por tracking.

### 10. ms-auditoria
Qué hace:
- Guarda eventos técnicos de compra o acciones importantes.

Qué decir en defensa:
- Sirve para trazabilidad y control.
- Permite saber quién hizo qué y cuándo.
- Es útil para regulación y revisión del sistema.

Qué modificarían normalmente:
- Agregar usuario.
- Agregar acción.
- Agregar fecha y detalle.

## Explicación simple de las capas

### Controller
- Recibe la petición HTTP.
- Valida la entrada con `@Valid`.
- Devuelve una respuesta controlada.

### Service
- Tiene la lógica del negocio.
- Decide qué hacer con los datos.
- Puede consultar repositorios o llamar a otro microservicio.

### Repository
- Habla con la base de datos.
- Normalmente extiende `JpaRepository`.

### Entity o Model
- Representa una tabla de la base de datos.

### DTO
- Sirve para recibir o devolver datos sin exponer todo el modelo.
- También ayuda a validar.

### GlobalExceptionHandler
- Centraliza errores.
- Evita repetir `try/catch` en todos los controllers.

## Qué significa la interacción entre microservicios

El ejemplo de clase era algo así:
- Un servicio A necesita datos de un servicio B.
- A llama a B por HTTP.
- B responde con un DTO.
- A arma su propio DTO de respuesta.

En tu proyecto, la idea sería:
- `ms-pedidos` llama a `ms-pacientes`.
- `ms-pacientes` devuelve el paciente por RUT.
- `ms-pedidos` arma un `PedidoResponse` con el paciente dentro.

Eso es correcto porque:
- no accede directo a la BD de otro MS,
- usa REST,
- y conserva separación funcional.

## Cómo responder si te preguntan por un cambio random

Si te piden algo simple, sigue esta receta:
1. Cambia el DTO.
2. Si corresponde, cambia la entidad.
3. Cambia el service.
4. Ajusta el controller.
5. Prueba en Postman.

Ejemplos de cambios que podrían pedir:
- Agregar un campo nuevo.
- Cambiar un nombre de salida.
- Agregar una validación.
- Crear una búsqueda por RUT o por nombre.
- Mostrar otro dato en el response.

## Ejemplo práctico: cambiar ms-auth para devolver RUT en vez de nombre

Si te pidieran algo como eso, la lógica sería:
- ubicar la entidad de usuario;
- ver qué campo guarda el nombre y qué campo guarda el RUT;
- revisar el DTO o la respuesta del controller;
- cambiar el campo que se envía en la respuesta;
- probar en Postman con el login o registro.

Si el servicio guarda ambos datos, normalmente el cambio es más de respuesta que de base de datos.

## Respuesta corta para defensa

Puedes decir algo como esto:

"Cada microservicio es independiente, tiene su propia base de datos y su propia responsabilidad. Cuando necesito datos de otro servicio, no accedo directo a su BD; consumo su endpoint REST y lo represento con DTOs. Así mantengo separación funcional, bajo acoplamiento y una arquitectura más limpia."

## Mini checklist para estudiar

- Entender qué hace cada microservicio.
- Saber explicar controller, service, repository, entity y DTO.
- Saber probar un CRUD en Postman.
- Entender que la comunicación entre MS es por HTTP.
- Tener claro el ejemplo `ms-pedidos` -> `ms-pacientes`.
- Poder hacer un cambio simple en vivo.

## Qué practicar primero

1. Crear un paciente.
2. Consultarlo por RUT.
3. Crear un pedido usando ese RUT.
4. Ver que el pedido devuelva el paciente embebido.
5. Cambiar un campo pequeño y volver a probar.

---

Si quieres, esta guía la podemos convertir después en una versión más corta tipo "chuleta de defensa" o en un formato más formal para entregar como documento.

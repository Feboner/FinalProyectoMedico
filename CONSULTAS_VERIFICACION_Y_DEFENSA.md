# 🎯 CONSULTAS EXTENSAS PARA VERIFICACIÓN Y DEFENSA TÉCNICA

**INSTRUCCIONES:** Envía TODO este contenido a Claude en un solo prompt. Él te dirá qué verificar, cómo testear, y cómo responder en la defensa.

---

## SECCIÓN 1: VERIFICACIÓN DE ARQUITECTURA Y FUNCIONALIDAD

### A. Estructura de Microservicios (Revisar si todo está correcto)

Tengo 10 microservicios en un sistema médico e-commerce con arquitectura basada en Spring Boot 3.2.5, Java 17, MySQL, Maven, Lombok, JPA/Hibernate, WebClient, JWT. Estos son:

**Servicios creados:**
1. **ms-auth** (8081): Autenticación, JWT, BCrypt, endpoint POST /login con LoginRequest
2. **ms-productos** (8082): Catálogo medicamentos/insumos, CRUD completo
3. **ms-pacientes** (8083): Perfiles pacientes con OneToMany Paciente→Direccion, findByRut()
4. **ms-inventario** (8084): Control bodega con codigoProducto, nombre, stockActual, stockMinimo, ubicacionBodega
5. **ms-pedidos** (8085): Orquestación compras, **COMUNICA CON ms-pacientes vía WebClient**
6. **ms-recetas** (8086): Recetas médicas con folio, medicamento, dosis, fechaEmision, estado (VIGENTE/USADA/VENCIDA)
7. **ms-pagos** (8088): Transacciones bancarias con numeroPago, monto (BigDecimal), metodoPago, estado, fechaPago (LocalDateTime)
8. **ms-envios** (8089): Tracking entregas con codigoSeguimiento, direccionDestino, estado (PREPARANDO/EN_CAMINO/ENTREGADO), transportista
9. **ms-proveedores** (8087): Laboratorios/distribuidores con rut, nombre, contacto, email, tipoProducto (MEDICAMENTOS/INSUMOS/EQUIPOS)
10. **ms-auditoria** (8090): Logs cumplimiento con usuarioRut, accion, detalle, modulo, fecha (LocalDateTime)

**Pregunta crítica:** ¿La arquitectura de estos 10 microservicios con BD independientes (no compartidas) y comunicación solo por REST/HTTP es correcta para una evaluación de microservicios? ¿Qué estándares de industria cumple?

---

### B. Comunicación Inter-Microservicios (ms-pedidos → ms-pacientes)

**Estado actual en ms-pedidos:**
- Tiene WebClient configurado
- Cuando llega un pedido, hace GET a `http://ms-pacientes:8083/api/pacientes/rut/{rut}`
- Mapea respuesta a PacienteResumenDTO (id, rut, nombres, apellidos)
- Embebe el paciente en PedidoResponse

**Problemas potenciales que necesito verificar:**
1. ¿Si ms-pacientes está DOWN, cómo debería fallar ms-pedidos? (¿exception, retry, fallback?)
2. ¿Es correcto usar directamente `http://ms-pacientes:8083` o debería usar Eureka/service discovery?
3. ¿Debo implementar @CircuitBreaker (Resilience4j)?
4. ¿Cómo documento esto en la defensa si el profe pregunta "¿Y si ms-pacientes falla?"

**Solicitud:** Dame estrategia para:
- Implementar resilencia básica en ms-pedidos (timeout, retry, fallback simple)
- Documentar qué pasa en cada escenario de falla
- Respuesta corta (máx 2-3 oraciones) para defender cada decisión

---

### C. Validación de DTOs y Excepciones Globales

Todos los servicios tienen:
- DTOs con @NotBlank, @NotNull, @Email, @Positive, @PositiveOrZero
- GlobalExceptionHandler con @ControllerAdvice
- EntityNotFoundException cuando recurso no existe
- MethodArgumentNotValidException para validaciones fallidas

**Verificar:**
1. ¿Es suficiente o me falta @ExceptionHandler para más casos?
2. ¿Debería hacer response wrapper como ApiResponse<T> consistente en todos los 10 servicios?
3. ¿Si dejo una BD sin crear tabla, qué excepción caería? ¿Cómo la debo manejar?

**Solicitud:** Necesito un ApiResponse wrapper ESTÁNDAR que use en los 10 servicios con campos: success (boolean), message (String), data (T), timestamp (LocalDateTime). Dame:
- Clase completa listo para copiar-pegar en cada ms
- Cómo modificar GlobalExceptionHandler para devolverlo siempre
- Ejemplo de endpoint que devuelve ApiResponse<PedidoDTO>

---

## SECCIÓN 2: TESTING Y VERIFICACIÓN DE FUNCIONALIDAD

### D. Testing de Endpoints (Sin levantarlos, solo lógica)

Dame estrategia para testear:

1. **ms-pacientes GET /api/pacientes/rut/{rut}**
   - Caso éxito: RUT existe
   - Caso error: RUT no existe (EntityNotFoundException)
   - Caso error: RUT vacío (validación)
   - Valida que Direcciones se retornen en la respuesta

2. **ms-pagos POST /api/pagos**
   - Body válido con numeroPago único, monto > 0, metodoPago en enum TARJETA/EFECTIVO/TRANSFERENCIA
   - Body inválido: monto negativo (falla @Positive)
   - Body inválido: falta numeroPago (falla @NotBlank)
   - Verifica que fechaPago se asigne automáticamente a LocalDateTime.now()

3. **ms-inventario PUT /api/inventario/{id}**
   - Actualiza solo nombre, stockActual, stockMinimo, ubicacionBodega
   - Valida que codigoProducto NO se puede cambiar (es unique)
   - Valida que stockActual ≥ 0 (@PositiveOrZero)

4. **ms-pedidos GET /api/pedidos/{id}** (con llamada a ms-pacientes)
   - ¿Qué pasa si ms-pacientes responde 404 al buscar rut?
   - ¿Qué pasa si ms-pacientes tarda >5s?
   - ¿Se debe retornar pedido sin paciente o fallar completamente?

**Solicitud:** Dame:
- Casos de prueba específicos (given-when-then) para cada uno
- Cómo escribir test unitario sin levantarlos (mocking)
- Cómo escribir test integración local (test-containers o similar)
- Curl commands listos para Postman para cada escenario

---

### E. Validación de Campos de Dominio

Hace poco corregí 7 servicios porque tenían campos template (codigo, nombre, stock, ubicacion).

**Ahora están correctos:**
- ms-recetas: folio, pacienteRut, medicamento, dosis, fechaEmision, estado
- ms-pagos: numeroPago, pacienteRut, monto (BigDecimal), metodoPago, estado, fechaPago (LocalDateTime)
- ms-envios: codigoSeguimiento, pacienteRut, direccionDestino, estado, transportista
- ms-auditoria: usuarioRut, accion, detalle, modulo, fecha (LocalDateTime)
- ms-proveedores: rut, nombre, contacto, email, tipoProducto
- ms-inventario: codigoProducto, nombre, stockActual, stockMinimo, ubicacionBodega
- ms-productos: (verificar que NO tiene campos template)

**Solicitud:** Dame una checklist que pueda copiar-pegar en cada service/repository/dto y verificar que:
1. Entity tiene @Entity @Table @Data @AllArgsConstructor @NoArgsConstructor
2. DTO tiene validaciones apropiadas
3. Repository extiende JpaRepository y tiene custom queries si aplica (findByRut, etc.)
4. Service tiene métodos CRUD básicos con logging y excepciones
5. Cada campo tiene @Column con propiedades correctas (nullable, unique, etc.)

---

## SECCIÓN 3: DEFENSA TÉCNICA - PREGUNTAS Y RESPUESTAS

### F. Preguntas Probables del Profesor (Con respuestas memorizables)

Simula que eres el profesor. Haz 20-25 preguntas técnicas que probablemente haría, con respuestas SHORT (máx 3-4 oraciones) que pueda memorizar:

**EJEMPLOS de preguntas que creo que hará:**

1. "¿Cómo comunicación entre ms-pedidos y ms-pacientes sin BD compartida?"
2. "¿Por qué usaste WebClient en lugar de RestTemplate?"
3. "¿Qué pasa si el paciente no existe en ms-pacientes cuando hago un pedido?"
4. "¿Por qué cada microservicio tiene su propia BD? ¿No es redundancia?"
5. "¿Cómo garantizas consistencia si ms-pagos y ms-pedidos están en BDs separadas?"
6. "¿Qué es JWT y por qué lo usas en ms-auth?"
7. "¿Cómo escalas si ms-inventario empieza a tener millones de records?"
8. "¿Hay algún servicio que consuma de otro? Dame un ejemplo real de tu proyecto."
9. "¿Qué pasa si ms-pacientes falla? ¿Se cae ms-pedidos o tiene fallback?"
10. "¿Por qué no hiciste ms-pedidos consumir de ms-inventario también para validar stock?"

**LUEGO, dame más preguntas sobre:**
- Validación de datos (DTO, Bean Validation)
- Manejo de excepciones
- Seguridad (JWT, BCrypt)
- Diseño de BD (OneToMany en ms-pacientes, why?)
- Ports (¿por qué 8081-8090?)
- Logs y trazabilidad

**Solicitud:** Por cada pregunta dame:
- Pregunta exacta (como la haría un profesor)
- Respuesta SHORT (memorizable, 3-4 oraciones)
- Una frase de "cierre" si el profe pregunta más (pivot a otra tema)

---

### G. Modificaciones Aleatorias (Ejercicio de defensa)

El profe puede pedir cambios en VIVO durante la defensa. Ejemplos que mencione el otro Claude:

**Escenario 1:** "Quiero que ms-recetas devuelva también el nombre del paciente (no solo RUT). Hazlo ahora."
- Respuesta estructura: 1) consumir ms-pacientes como hizo ms-pedidos, 2) crear RecetaResumenDTO con nombrePaciente, 3) modificar controller

**Escenario 2:** "Agrega un campo 'prioridad' a ms-pedidos. Prioridad puede ser BAJA, MEDIA, ALTA."
- Respuesta: 1) Agregar campo String prioridad a Entity, 2) Agregar a DTO con validación, 3) BD migra automáticamente con JPA

**Solicitud:** Dame 15-20 modificaciones potenciales (de fácil a difícil):
- Cambios de campo (adicionar, renombrar)
- Cambios de lógica (nuevo endpoint, nuevo método en Service)
- Cambios de comunicación inter-ms
- Cambios de validación

Para cada una: 1) Instrucción exacta, 2) Tiempo estimado para hacerla, 3) Pasos 1-5 para implementar, 4) Qué archivo/clase tocar

---

### H. Git Strategy (Simular desarrollo progresivo)

El profe verá el historial de commits. No puedo tener todo hecho de una vez.

**Estado actual:** Todo hecho (10 ms, correcciones de dominio, comunicación inter-ms)

**Necesito simular progreso natural:**
- Commit 1: "feat: scaffold 10 microservices with Maven structure" (solo carpetas + pom.xml)
- Commit 2: "feat: CRUD basic endpoints for all 10 services"
- Commit 3: "feat: WebClient integration ms-pedidos → ms-pacientes"
- Commit 4: "fix: correct domain fields in 7 microservices" 
- Commit 5: "feat: global exception handling and validation"
- etc.

**Solicitud:** Dame:
1. Secuencia de 12-15 commits realistas
2. Qué archivos modificar en cada commit para que se vea natural
3. Commit messages en inglés + español
4. Cómo hacer que vea que cada commit es una mejora incremental

---

### I. Presentación Visual para Defensa

**Qué voy a mostrar:**
1. Diagrama arquitectura (10 microservicios, BDs separadas, WebClient)
2. Flujo completo pedido: ms-auth login → ms-productos browse → ms-pacientes lookup → ms-inventario check → ms-pedidos create → ms-pagos pay → ms-envios track
3. Tabla: Responsabilidad cada servicio
4. Tabla: Campos cada entidad (para probar si memoricé)
5. Código de ms-pedidos WebClient (lo más cool)

**Solicitud:**
1. Dame estructura de diapositivas (qué mostrar en cada una)
2. Qué código snippets debo tener ready-to-show (máx 10 líneas c/u)
3. Qué gráficos/diagramas hacer (puedo hacer ASCII o description para que haga en draw.io)
4. Qué NO mostrar (errores, logs vacíos, etc.)

---

## SECCIÓN 4: DETALLES TÉCNICOS FINALES

### J. Preguntas de Profundidad Técnica

1. **Spring Boot versioning:** ¿Por qué 3.2.5 y no 2.7.x? ¿Cuáles son breaking changes?
2. **Jakarta vs javax:** ¿Por qué jakarta.persistence en lugar de javax.persistence?
3. **Lombok:** ¿Es seguro usar @Data? ¿Hay riesgos con @EqualsAndHashCode?
4. **Transacciones:** ¿Hay @Transactional en los Services? ¿Debería haberlas?
5. **Async/Reactive:** ¿Debería usar reactive programming o RestTemplate es OK?
6. **Testing:** ¿Debo tener test coverage? ¿Cuál es el mínimo aceptable?
7. **Documentación API:** ¿Debo agregar Swagger/OpenAPI en cada servicio?
8. **Versionado API:** ¿Debería versionar endpoints (v1, v2)?
9. **Rate limiting:** ¿Necesito implementar rate limiting en producción?
10. **Monitoreo:** ¿Qué métricas debería exponer (Actuator, Prometheus)?

**Solicitud:** Para cada una:
- ¿Es crítico para la defensa o "nice to have"?
- ¿Puedo agregarlo en 5 minutos o requiere arquitectura?
- ¿Qué diría si me pregunta y no lo implementé?

---

### K. Bugs Potenciales (Detectar antes del profe)

Me gustaría que verifiques si hay bugs/issues en:

1. **OneToMany Paciente→Direccion en ms-pacientes**
   - ¿Se cargan lazy o eager? ¿N+1 queries?
   - ¿Se borran direcciones si borro paciente?
   - ¿El DTO expone direcciones correctamente?

2. **BigDecimal en ms-pagos**
   - ¿Se serializa correctamente a JSON?
   - ¿Hay problemas de precision con monto?
   - ¿Validación @Positive trabaja bien?

3. **LocalDateTime en ms-pagos y ms-auditoria**
   - ¿Se serializa a ISO-8601?
   - ¿Usa timeZone?
   - ¿MySQL lo almacena bien?

4. **Enum strings en ms-recetas, ms-pagos, ms-envios**
   - ¿Se validan los enum values o acepta cualquier string?
   - ¿Cómo manejo si alguien manda "INVALID_STATE"?

5. **Unique constraints**
   - ms-recetas: folio unique
   - ms-pagos: numeroPago unique
   - ms-inventario: codigoProducto unique
   - ms-proveedores: rut unique
   - ¿Qué pasa si intento crear duplicado? ¿Qué error?

**Solicitud:** Dame:
- Lista de bugs potenciales reales que podría tener
- Cómo detectarlos (test, manual testing, code review)
- Fix para cada uno
- Cuál es CRÍTICO arreglar antes de defensa vs "no afecta la evaluación"

---

### L. Postman Collection (Casos de uso completos)

Necesito Postman collection que demuestre flujo COMPLETO:

**Flujo 1 - Compra Simple:**
1. POST /auth/login (ms-auth) → obtener token JWT
2. GET /api/productos (ms-productos) → ver catálogo
3. GET /api/pacientes/rut/12345678-9 (ms-pacientes) → validar paciente
4. POST /api/pedidos (ms-pedidos, mando pacienteRut) → crear pedido, **WebClient valida paciente existe**
5. GET /api/pedidos/1 (ms-pedidos) → devuelve pedido CON datos paciente embedido

**Flujo 2 - Gestión de Medicinas:**
1. POST /api/recetas (ms-recetas) → crear receta con medicamento
2. GET /api/recetas/1 (ms-recetas) → obtener
3. PUT /api/recetas/1 (ms-recetas) → cambiar estado a USADA

**Flujo 3 - Pago y Auditoría:**
1. POST /api/pagos (ms-pagos) → registrar pago, numeroPago único, estado PENDIENTE
2. PUT /api/pagos/1 (ms-pagos) → cambiar estado a APROBADO
3. POST /api/auditorias (ms-auditoria) → registrar acción "PAGO_APROBADO"

**Flujo 4 - Envío:**
1. POST /api/envios (ms-envios) → crear envío con codigoSeguimiento único
2. PUT /api/envios/1 (ms-envios) → actualizar estado EN_CAMINO → ENTREGADO

**Solicitud:**
- Postman collection JSON exportable
- Pre-scripts (set variables, headers JWT, etc.)
- Tests en cada request (status code 200, body has id, etc.)
- Variables de entorno (localhost, puerto, token)
- Documentación en qué orden ejecutar

---

### M. README completo para el proyecto

Necesito README que explique:
- Qué es el proyecto (medical e-commerce microservices)
- Arquitectura (diagrama ASCII)
- Cómo instalar (Maven, Java 17, MySQL)
- Cómo ejecutar cada ms (puerto, order)
- Endpoints principales de cada uno
- WebClient flow (ms-pedidos → ms-pacientes)
- Cómo testear (Postman)
- Commits y cambios realizados

**Solicitud:** Dame README.md completo, estructurado, que pueda copiar-pegar.

---

## INSTRUCCIONES FINALES PARA CLAUDE

Responde en este orden:

1. **SECCIÓN 1A-1C:** Verifica si la arquitectura + comunicación inter-ms está bien. Dame errores potenciales.

2. **SECCIÓN 2D-2E:** Dame testing strategy completo + checklist verificación campos.

3. **SECCIÓN 3F:** Crea 25 preguntas profesor + respuestas SHORT memorizables.

4. **SECCIÓN 3G:** 15-20 modificaciones potenciales de defensa con pasos.

5. **SECCIÓN 3H:** Secuencia 12-15 commits realistas para git history.

6. **SECCIÓN 3I:** Estructura presentación visual + snippets código + diagramas.

7. **SECCIÓN 4J-4L:** Detecta bugs + Postman collection + README.

**IMPORTANTE:** Todo debe ser ESPECÍFICO a MI proyecto (los 10 ms, campos concretos, puertos 8081-8090, WebClient ms-pedidos→ms-pacientes). No respuestas genéricas.

**LÍMITE:** Si es muy largo, secciona pero sigue mismo orden.

**FORMATO:** Usa markdown, listas, tablas, código snippets listos para copiar.

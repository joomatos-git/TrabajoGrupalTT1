# Simulador de Criaturas: Documentación del Proyecto

## Descripción general

Servicio de simulación de criaturas sobre una cuadrícula 2D. Cada celda puede estar ocupada como máximo por **una criatura**, representada visualmente por un color. La simulación avanza instante a instante hasta que la cuadrícula se llena.

Nosotros desarrollamos el **servicio (backend)**. La capa de presentación la gestiona Java. Este proyecto reemplaza el swagger proporcionado por Javi.

## Tipos de criaturas

| Tipo | Comportamiento |
|------|---------------|
| **Quieta** | No se mueve. Permanece en su celda inicial. |
| **Móvil** | Cada instante se desplaza a una celda adyacente. |
| **Expansiva** | Cada instante genera una copia perfecta en una celda adyacente (comportamiento epidémico). |


## Reglas del sistema

- La cuadrícula se inicializa con **X criaturas de tipo A, Y de tipo B y Z de tipo C** en posiciones aleatorias.
- **No pueden coincidir dos criaturas en la misma casilla.**
- Las criaturas **no mueren**. La simulación termina cuando la cuadrícula está completamente llena.
- **Resolución de conflictos** (dos criaturas quieren ir a la misma casilla): tiene prioridad la de **fila superior** y, en caso de empate, la de **columna más a la derecha**.

## Flujo de uso

1. El usuario elige el número de criaturas de cada tipo.
2. El sistema devuelve un **token** de sesión.
3. Con ese token, el usuario puede realizar peticiones HTTP para consultar el estado de la isla en distintos momentos (pueden pasar intervalos de tiempo entre peticiones).


## Gestión de roles

## Gestión de roles

Los roles rotan cada sprint. Cada sprint tiene un Desarrollador distinto y los otros dos miembros asumen roles de soporte, sin que ningún rol de soporte se repita para la misma persona en todo el proyecto. Raul se incorpora al equipo en el Sprint 2.

| Sprint | Desarrollador | Rol de soporte | Rol de soporte |
|--------|--------------|----------------|----------------|
| Sprint 0 | Joseba | Alicia (Organizadora) | — |
| Sprint 1 | Alicia | Joseba (Tester) | — |
| Sprint 2 | Raul | Joseba (Revisor) | Alicia (Analista) |
| Sprint 3 | Joseba | Alicia (QA) | Raul (Scrum Master) |
| Sprint 4 | Alicia | Raul (Documentador) | Joseba (Arquitecto) |
| Sprint 5 | Raul | Joseba (DevOps) | Alicia (Tester de integración) |
| Sprint 6 | Joseba | Alicia (Gestora de entrega) | Raul (Revisor final) |

## Planificación por sprints

| Sprint | Objetivo principal |
|--------|-------------------|
| **Sprint 0** | Configuración del entorno, Kanban, decisiones tecnológicas |
| **Sprint 1** | Estructura API REST, migración Maven, revisión de tests |
| **Sprint 2** | Implementación bichitos, arreglo tests unitarios, ServiceImpl con lógica real |
| **Sprint 3** | GridLogic (initialize + avanzarInstante), resolución de conflictos |
| **Sprint 4** | Tests de integración, gestión de errores en la API |
| **Sprint 5** | Javadoc, revisión general del código |
| **Sprint 6** | Documentación final, manual de usuario, entrega |

## Sprints

### Sprint 0

- Configuración del tablero **Kanban en GitHub**
- Decisión tecnológica: se usará **Java** (para minimizar formación adicional)
- Configuración del **IDE** y primeras pruebas del entorno
- Reunión con el cliente (Javi) para resolución de dudas sobre requisitos
- Primera distribución de tareas entre los miembros del equipo

### Sprint 1

**Alicia (Desarrolladora)**

- Aprendizaje y documentación del funcionamiento de una API REST con Spring Boot: estructura de controllers, servicios e interfaces, inyección de dependencias y manejo de peticiones HTTP.
- Migración del proyecto a Maven con `pom.xml` configurado para Spring Boot.
- Diseño e implementación de la capa API REST en el paquete `Sistema.API`:
    - `ISimulacionService`: interfaz que define el contrato del servicio (métodos `iniciarSimulacion` y `getEstado`).
    - `SimulacionServiceImpl`: implementación del servicio con gestión de sesiones mediante tokens UUID.
    - `SimulacionController`: controller REST con los endpoints `POST /simulacion/iniciar` y `GET /simulacion/estado`.
    - `ConfiguracionDTO`: objeto de transferencia para recibir la configuración inicial (filas, columnas, número de criaturas por tipo).
    - `EstadoTableroDTO`: objeto de transferencia para devolver el estado del tablero en un instante dado.
    - `RestApplication`: clase principal que arranca el servidor Spring Boot.

**Joseba (Tester)**

- Creación de tests de funcionamiento de cada tipo de criatura.
- Creación de test para la generación del grid inicial, tanto con setSeed como sin ella.
- Lógica del grid diseñada como Placeholder temporal.

**Ambos**

- Puesta en común de dudas e ideas sobre el diseño del sistema: flujo de la simulación, estructura de paquetes, reparto de responsabilidades entre capas y criterios de resolución de conflictos.
- De cara a la implementación del grid. Lista de listas de bichos. Cada lista por un instante de tiempo. Devuelve lista de listas, poblar grid con todo eso. Pregunta bicho por bicho donde está y lo mueve.

### Sprint 2

**Todos**

- Puesta al día de Raúl que se acababa de unir al proyecto, explicando el proyecto en general, metodología de trabajo...
- Puesta en común de dudas e ideas sobre el diseño del sistema, sobre todo debido a la nueva incorporación.
  
**Alicia (Analista)**
- Creación de nuevos tests en el paquete `LogicaNegocio`:
    - `TestBichitos.java`: tests de comportamiento básico de cada tipo de criatura.
    - `TestGeneracionInicial.java`: validación de la generación del grid inicial con y sin semilla (`setSeed`).
    - `TestGrid.java`: tests sobre la estructura y estado del grid.
    - `TestGridLogicStep.java`: tests del avance de instante (`step`), incluyendo casos de movimiento, mitosis y resolución de conflictos.
    - `TestPosicion.java`: tests de la lógica de posicionamiento de criaturas en el grid.
- Revisión de los tests ya existentes con ayuda de Joseba, verificando coherencia de casos de prueba y cobertura de escenarios límite.

**Raúl (Desarrollador)**
- Reestructuración de la arquitectura Maven, reorganizando en los directorios fuente del proyecto (src/main/java y src/test/java) para cumplir estrictamente con el estándar oficial de Maven y Spring Boot, resolviendo problemas de detección de código y garantizando la compatibilidad total entre diferentes IDEs.
- Desarrollo del método central step() en GridLogic.java para gestionar el avance temporal del sistema.
- Implementación de un sistema de ordenación por prioridad antes de procesar cualquier acción, asegurando que las criaturas en filas superiores y, en caso de empate, las situadas más a la derecha, tengan preferencia para ocupar casillas.
- Diseño de un flujo de ejecución en dos etapas para garantizar la integridad del tablero:
    - 1º: Se reservan las posiciones de las criaturas que no se desplazan (Quieto y la célula "madre" de Mitosis), marcando sus casillas como ocupadas en el nuevo instante.
    - 2º: Se procesan los movimientos de las criaturas Móvil y las nuevas copias de Mitosis hacia las celdas que han quedado libres.
- Creación del método helper *obtenerAdyacentesLibres*, que encapsula la lógica de detección de celdas vacías en las cuatro direcciones, verificando siempre los límites de la cuadrícula y la disponibilidad de la celda.

**Joseba (Revisor)**
- GridLogic.step() revisado y cambiado para representar las probabilidades de los distintos movimientos.
- GridLogic.inicializar() revisado y cambiado para que la proporcion de tipos de criaturas no sea equitativo.
- API revisada y modificada para enlazarla al funcionamiento de nuestro sistema. Comprobado que actualmente funciona el sistema de obtención de Token y de PUSH/GET.
- Algunos tests revisados y modificados ante errores mínimos en estos.
- Cambiada la construccion de los objetos gridLogic para permitir grids de tamaños distintos a 10x10 y para permitir un uso mas sencillo de los gridLogic con setSeed. me puedes hacer como un mini resumen para presnetarlo luego en 5 minutillos en el sprint de que hizo cada uno

### Sprint 3

**Alicia (QA)**
- Terminar en testMovil y testMitosis los test `testMovilSoloVaAAdyacentes`, `testMovilNoCruzaDiagonal` y `testHijoSoloPuedeEstarEnAdyacente` `testMitosisNoSeReproducenTodosLosTurnos`

**Raúl (SCRUM Master)**
- Terminar test `testComprobarDistintos` en TestMitosis y TestMovil
- Revisar el formato de salida de la API.

**Joseba (Desarrollador)**
- Modificar el GridLogic como clase para evitar bucles vacíos cuando el sistema está atascado. (Introducida una flag para esto)
- Modificado GridLogic.step() para incluir que los movimientos y la clonación de los respectivos tipos de criatura funcionen con un 50% y 25% de probabilidad en vez de todos los steps.
- Cambiada implementación de la API para que el GET devuelva todos los instantes de golpe en vez de solicitar el instante manualmente.
- Cambiado formato del return de la API para que sea como el formato requerido de (instante,pos_y,pos_x,color)


## Estrategia de pruebas

El proyecto está orientado a **tests**, con cobertura de:

- **Tests unitarios**: validación de comportamiento individual de cada tipo de criatura.
- **Tests de integración**: validación del sistema completo (inicialización de cuadrícula, avance de la simulación, resolución de conflictos, condición de fin).

## Tecnologías

- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.2.4
- **Gestión de dependencias**: Maven
- **Gestión de proyecto**: GitHub Projects (Kanban)
- **Metodología**: Scrum / Kanban con sprints semanales



### Missing por ahora:

- TestMovil falta por tener en cuenta si se mueve solo a contiguas. Tirar algun test mas con ese rollo
- TestMitosis falta por tener en cuenta otras tantas cosillas como q de nuevo solo se multiplica contiguas vacías, o que no se multiplica todos los turnos.
- GridLogic tiene el step efectuando mitosis TODOS los turnos (debería ser probabilidad del 25%) y lo mismo con el movimiento (rollo un 50%)?



&nbsp;

&nbsp;

&nbsp;


NOTAS:

Para comprobar la API.

Comando en terminal: curl -X POST http://localhost:8080/simulacion/iniciar -H "Content-Type: application/json" -d "{\"filas\":10,\"columnas\":10,\"numQuietos\":5,\"numMoviles\":5,\"numMitosis\":3}"

despues te metes a http://localhost:8080/simulacion/estado?token=(lo que te responda en la terminal)

Test que voy a cambiar:
Todo TestGeneracionInicial
Todo TestGridLogicStep
TestMitosis-3 test y setUp
TestMovil-3 test y setUp
TestQuieto- setUp
TestSimulacionController.testGetEstado_respuestaContieneTablero- Tienen que ser getData
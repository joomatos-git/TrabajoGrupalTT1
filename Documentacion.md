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

Los roles rotan cada sprint. Siempre hay un Desarrollador y un rol de soporte distinto. Ningún rol de soporte puede repetirse en todo el proyecto.

| Sprint | Desarrollador | Rol de soporte |
|--------|--------------|----------------|
| Sprint 0 | Joseba | Alicia (Organizadora) |
| Sprint 1 | Alicia | Joseba (Tester) |
| Sprint 2 | Alicia | Joseba (Revisor) |
| Sprint 3 | Joseba | Alicia (Analista) |
| Sprint 4 | Joseba | Alicia (QA) |
| Sprint 5 | Alicia | Joseba (Scrum Master) |
| Sprint 6 | Joseba | Alicia (Documentadora) |

## Planificación por sprints

| Sprint | Objetivo principal |
|--------|--------------------|
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

Joseba

Tests. No estan bien ninguno.

De cara a la implementación del grid. Lista de listas de bichos. Cada lista por un instante de tiempo. Devuelve lista de listas, poblar grid con todo eso. Pregunta bicho por bicho donde está y lo mueve. Para los steps tambien y tal I guess.


**Ambos**

- Puesta en común de dudas e ideas sobre el diseño del sistema: flujo de la simulación, estructura de paquetes, reparto de responsabilidades entre capas y criterios de resolución de conflictos.
- De cara a la implementación del grid. Lista de listas de bichos. Cada lista por un instante de tiempo. Devuelve lista de listas, poblar grid con todo eso. Pregunta bicho por bicho donde está y lo mueve. Para los steps tambien y tal I guess.



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


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

Los roles rotan cada semana. **No se puede repetir el mismo rol en semanas consecutivas** para la misma persona.

| Semana | Desarrollador | Organizador / Tester |
|--------|--------------|----------------------|
| Semana 1 | Joseba | Alicia (Organizadora) |
| Semana 2 | Alicia | Joseba (Tester) |
| Semana 3 | Joseba | Alicia (Tester) |
| Semana 4 | Alicia | Joseba (Tester) |
| Semana 5 | Joseba | Alicia |

## Planificación

### Sprint 0

- [ ] Configuración del tablero **Kanban en GitHub**
- [ ] Decisión tecnológica: se usará **Java** (para minimizar formación adicional)
- [ ] Configuración del **IDE** y primeras pruebas del entorno
- [ ] Reunión con el cliente (Javi) para resolución de dudas sobre requisitos
- [ ] Primera distribución de tareas entre los miembros del equipo

## Estrategia de pruebas

El proyecto está orientado a **tests**, con cobertura de:

- **Tests unitarios**: validación de comportamiento individual de cada tipo de criatura.
- **Tests de integración**: validación del sistema completo (inicialización de cuadrícula, avance de la simulación, resolución de conflictos, condición de fin).

## Tecnologías

- **Lenguaje**: Java
- **Gestión de proyecto**: GitHub Projects (Kanban)
- **Metodología**: Scrum / Kanban con sprints semanales

# Instrucciones

Necesitamos usar Kanban en github, decir que rol desarrolla cada persona y que tienen que hacer, ponerlo para cada semana.

Los roles rotan, cada semana uno cada uno, uno de desarollo y otro otra, no se puede repetir rol

Proyecto orientado a teses, con unitarios e integración

Qué tiene que hacer: simulaciones de pequeñas criaturitas, simulación temporal

Trab individual: Que un cuadrado este pintado de un color es que una criatura habita esa islita.  Cada bichito hace comportamientos distintos, se mueves, quitos, cada instante se replican... No puede haber dos bichos en una casilla

- Quieta

- Cada instante se mueve a una casilla adyacente

- Se expande como una enfermedad, cada instante copia una copia perfecta en una posición adyacente.

Generar inicialmente cuadricula con X,Y,Z de criaturas de cada tipo en posiciones aleatorias. La página web tiene que servir para devolver ese sistema y mostrar su “evolución”.

Los bichos en principio no mueren, se acaba la ejecución cunado se llena la cuadricula

Si dos bichos quieren ir a una misma casilla, nuestra decision: Soluion tomada: Priorizamos por fila y columna, tiene prioridad fila superior y columna derecha.


En el trabajo individual el usuario elegio un número de bichitos, después mandaba un token, y luego ya enseña la isla.  Entre token y token puede pasar mucho tiempo, peticiones http. Luego dev la cuadricula.

Nosotros hacemos servicio, java hace capa presentación

Estamos remplazando el pequeño swagger que os dio Javi.

# Prevision

## Gestion de roles

- Semana1: Desarrollador(Joseba) y Organizador(Alicia)
- Semana2: Desarrollador(Alicia) y 
- Semana3: Desarrollador(Joseba) y
- Semana4: Desarrollador(Alicia) y Tester(Joseba)
- Semana5

## Planificación

## Sprint 0
Empezamo planificación, Kanban.
- Vamos a utilizar Java, para evitar formación en exceso.
- 
Probamos el IDE, damos un poquito de pena (según Javi eramos muy monos).

Hacemos cuestiones sobre requisitos, resolvemos dudas con el cliente. 

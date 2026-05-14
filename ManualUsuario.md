# Manual de Usuario - API Simulador de Criaturas

Este documento describe los pasos necesarios para ejecutar y consumir la API del Simulador de Criaturas. El sistema permite gestionar simulaciones en una cuadrícula 2D, calculando el comportamiento de distintas entidades a lo largo del tiempo.

## 1. Requisitos del Sistema
* **Java 17** o superior.
* **Maven** para la gestión de dependencias y ejecución.
* Un cliente para realizar peticiones HTTP (como **Postman**, **Insomnia**, comandos **cURL** o el uso del **trabajo individual** de la asignatura).

## 2. Puesta en Marcha
Para iniciar el servidor de la API:
1. Abre el proyecto en tu IDE preferido.
2. Localiza la clase `RestApplication.java` en el paquete `Sistema.API`.
3. Ejecuta la clase como una aplicación Java.
4. El servidor se iniciará por defecto en el puerto **50000** (o el puerto configurado en el entorno).

## 3. Uso de la API

La simulación consta de dos pasos: solicitar el inicio de una sesión y consultar los resultados finales.

### A. Iniciar una Simulación
Este endpoint crea una nueva cuadrícula de 10x10, distribuye las criaturas y calcula hasta 50 turnos de movimiento y mitosis.

* **URL:** `http://localhost:50000/Solicitud/Solicitar`
* **Método:** `POST`
* **Cuerpo de la petición (JSON):**
  Debe incluir un array con las cantidades iniciales de cada tipo en el orden: `[Quietos, Móviles, Mitosis]`.
  ```json
  {
    "cantidadesIniciales": [5, 8, 3],
    "nombreEntidades": ["Quieto", "Movil", "Mitosis"]
  }
  ```
* **Respuesta:** Un número entero que actúa como Token de identificación para la simulación (ej. 18459230).

### B. Obtener Resultados
Este endpoint devuelve la traza completa de la simulación. No es necesario pedir instante por instante, ya que el sistema devuelve el historial completo de una vez.

* **URL:** http://localhost:50000/Resultados
* **Método:** POST
* **Parámetros de URL:** tok (el número obtenido en el paso anterior).
* **Ejemplo de URL completa:** http://localhost:50000/Resultados?tok=18459230

### C. Formato de los Datos
La respuesta del endpoint de resultados contiene un objeto con una clave data. Su valor es un String multilínea formateado de la siguiente manera:

* **Primera línea:** Tamaño del lado de la cuadrícula (ej. 10).
* **Resto de líneas:** Estado de cada bicho en el formato instante,y,x,color_hexadecimal.
* Colores usados teniendo en cuenta las diferentes deficiencias visuales:
  *  #FF0000 (Rojo): Criatura Quieta.
  *  #EA63FF (Morado): Criatura Móvil.
  *  #00FF00 (Verde): Criatura Mitosis.

### D. Ejemplo rápido con cURL
* **1. Solicitar simulación**
  ```bash
  curl -X POST http://localhost:50000/Solicitud/Solicitar -H "Content-Type: application/json" -d "{\"cantidadesIniciales\":[5,5,5], \"nombreEntidades\":[\"Q\",\"M\",\"MT\"]}"
  ```
* **2. Obtener resultados (sustituir {TOKEN})**
  ```bash
  curl -X POST "http://localhost:50000/Resultados?tok={TOKEN}"
  ```

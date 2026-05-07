FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el JAR desde tu carpeta local al contenedor
# Ajusta la ruta 'target/...' según dónde se guarde tu JAR
COPY target/simulador-api.jar app.jar

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
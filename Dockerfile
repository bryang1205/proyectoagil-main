# Etapa 1: Construcción del JAR con Maven
FROM eclipse-temurin:21-jdk AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

RUN chmod +x ./mvnw

# Compila el proyecto y empaqueta el JAR
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final con solo el JDK para ejecutar la app
FROM eclipse-temurin:21-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo .jar construido en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto 8082 (para acceder a la aplicación)
EXPOSE 8082


# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]

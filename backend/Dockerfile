# Multi-stage Dockerfile para backend Spring Boot
# Fase de build: usa Maven con JDK 21 para compilar el proyecto
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copiamos solo lo necesario para compilar
COPY pom.xml ./
COPY src ./src

# Compilamos y empaquetamos el JAR (sin tests para acelerar)
RUN mvn -DskipTests package

# Fase de runtime: JRE ligero para ejecutar el JAR
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Creamos usuario no root y carpeta para uploads
RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir -p /app/uploads/productos && \
    chown -R appuser:appgroup /app/uploads

# Copiamos el JAR desde la fase de build
COPY --from=builder /workspace/target/*.jar /app/app.jar

# Render/otras plataformas suelen inyectar PORT; por defecto 8082
ENV PORT=8082
EXPOSE 8082

# Ejecutamos como usuario no root
USER appuser

# Usamos sh -c para que la variable $PORT se expanda correctamente
ENTRYPOINT ["sh","-c","java -jar -Dserver.port=$PORT /app/app.jar"]
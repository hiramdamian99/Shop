# Etapa de compilación
FROM maven:3.8.5-openjdk-17 AS build

# Crear el grupo y el usuario
RUN groupadd --gid 1001 app && \
    useradd --uid 1001 --gid app --home /app app && \
    sed -i -e 's/1001/0/g' /etc/passwd

# Directorio de trabajo y copiar archivos
WORKDIR /app
COPY . .

RUN whoami

# Copiar configuraciones de Maven
RUN mkdir -p /root/.m2/repository
WORKDIR /app/conf
COPY . /root/.m2

# Generar effective-settings y resolver dependencias
WORKDIR /app
RUN mvn -B dependency:resolve clean install -Dmaven.test.skip=true -P dev -s conf/settings.xml

# Etapa de producción
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=build /app/bootloader/target/*.jar app.jar

# Actualizar el puerto según sea necesario
EXPOSE 8600
CMD ["java", "-jar", "app.jar"]
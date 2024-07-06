FROM ubuntu:20.04

# Prevenir solicitudes interactivas durante la instalación de paquetes
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Etc/UTC
ENV USER=root

# Agregar el repositorio de Adoptium y su clave GPG, luego instalar OpenJDK 21
RUN apt-get update && \
    apt-get install -y wget apt-transport-https gnupg && \
    wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | apt-key add - && \
    echo "deb https://packages.adoptium.net/artifactory/deb bionic main" | tee /etc/apt/sources.list.d/adoptium.list && \
    apt-get update && \
    apt-get install -y temurin-21-jdk

# Instalar VNC, XFCE y otras herramientas necesarias
RUN apt-get install -y xfce4 xfce4-goodies tightvncserver curl git

# Instalar aplicaciones adicionales (ejemplo: Firefox y Gedit)
RUN apt-get install -y firefox gedit

# Configurar VNC
RUN mkdir -p /root/.vnc && \
    echo 'password' | vncpasswd -f > /root/.vnc/passwd && \
    chmod 600 /root/.vnc/passwd

# Crear script para iniciar VNC con XFCE
RUN echo '#!/bin/bash\n\
    xrdb $HOME/.Xresources\n\
    startxfce4 &' > /root/.vnc/xstartup && \
    chmod +x /root/.vnc/xstartup

# Copiar la aplicación Spring Boot al contenedor
COPY xss1-0.0.1-SNAPSHOT.jar /root/app/xss1-0.0.1-SNAPSHOT.jar

# Exponer el puerto VNC y el puerto de la aplicación Spring Boot
EXPOSE 5901
EXPOSE 8082

# Comando para iniciar VNC y la aplicación Spring Boot
CMD ["sh", "-c", "USER=root vncserver :1 -geometry 1280x800 -depth 24 && java -jar /root/app/xss1-0.0.1-SNAPSHOT.jar && tail -F /root/.vnc/*.log"]

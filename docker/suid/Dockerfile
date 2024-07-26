FROM ubuntu:20.04

# Prevenir solicitudes interactivas durante la instalación de paquetes
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Etc/UTC
ENV USER=root

# Agregar el repositorio de Adoptium y su clave GPG, luego instalar OpenJDK 21
RUN apt-get update

# Instalar VNC, XFCE y otras herramientas necesarias
RUN apt-get install -y xfce4 xfce4-goodies tightvncserver curl git

# Instalar el emulador de terminal xfce4-terminal
RUN apt-get install -y xfce4-terminal

RUN useradd -m -s /bin/bash user && echo 'user:user' | chpasswd && adduser user sudo

# Configurar VNC
RUN mkdir -p /home/user/.vnc && \
    chown -R user:user /home/user/.vnc

# Crear script para iniciar VNC con XFCE
RUN echo '#!/bin/bash\n\
xrdb $HOME/.Xresources\n\
startxfce4 &' > /home/user/.vnc/xstartup && \
    chmod +x /home/user/.vnc/xstartup && \
    chown user:user /home/user/.vnc/xstartup

# Configurar SUID para el binario env
RUN chmod u+s /usr/bin/env

# Crear el archivo flag.txt en /root/ con el contenido "premio123"
RUN echo "premio123" > /root/flag.txt

# Cambiar a usuario "user" antes de iniciar VNC
USER user
WORKDIR /home/user

# Exponer el puerto VNC y el puerto de la aplicación Spring Boot
EXPOSE 5901
EXPOSE 8082

# Configurar VNC y lanzar aplicaciones en el contenedor
CMD ["sh", "-c", "echo $VNC_PASSWORD | vncpasswd -f > /home/user/.vnc/passwd && chmod 600 /home/user/.vnc/passwd && USER=root vncserver :1 -geometry 1280x800 -depth 24 && tail -F /home/user/.vnc/*.log"]

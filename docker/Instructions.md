1. Create the image with  `docker build -t vnc-lab .`
2. Run container with `docker run -d -p 5901:5901 --name lab1 vnc-lab`
3. Start noVNC service `A:\Documentos\TFGDocker\vnc-lab\noVNC>websockify --web ./ 6080 localhost:5901`
4. Access NoVnc `http://localhost:6080/vnc.html`

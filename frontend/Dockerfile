# Fase 1: Costruisci l'app Angular
# Usa un'immagine Node.js come base per costruire l'app
FROM node:lts as build

# Imposta la directory di lavoro nel container
WORKDIR /app

# Copia il file 'package.json' e 'package-lock.json' (se disponibile)
COPY package*.json /app/

# Installa le dipendenze del progetto
RUN npm install

# Copia i file sorgente del progetto Angular
COPY . /app

# Costruisci l'applicazione per la produzione
RUN npm run build

# Fase 2: Servi l'app con Nginx
# Usa un'immagine Nginx per servire l'app
FROM nginx:bookworm

# Rimuovi i file esistenti in /usr/share/nginx/html
RUN rm -rf /usr/share/nginx/html/*

# Copia il build dell'app Angular nella directory di servizio di Nginx
COPY --from=build /app/dist/frontend/browser/ /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/nginx.conf

# Sposta i file dalla sottocartella 'browser' alla directory principale
# RUN mv /usr/share/nginx/html/browser/* /usr/share/nginx/html/ && \
#     rm -rf /usr/share/nginx/html/browser


# Espone la porta 80
EXPOSE 80

# Quando il container viene eseguito, avvia Nginx per servire l'app
CMD ["nginx", "-g", "daemon off;"]

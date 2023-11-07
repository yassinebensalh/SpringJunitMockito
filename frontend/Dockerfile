# Node container to configure and build the project
    FROM node:18 as angular-container
    WORKDIR /app
    
    COPY frontend/package*.json .
    RUN npm install --legacy-peer-deps
    COPY frontend/ .
    RUN node_modules/.bin/ng build --aot --base-href .

# Apache container to run the built app
    FROM httpd:2.4
    RUN rm -rf /usr/local/apache2/htdocs/*
    # Copy the built app to the apache container
    # make sure to change /app/dist/CORRECT_ANGULAR_PROJECT_NAME
    COPY --from=angular-container /app/dist /usr/local/apache2/htdocs/
    EXPOSE 80

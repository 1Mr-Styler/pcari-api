FROM styl3r/vanilla:latest

RUN ["mkdir", "-p", "/apps/home"] 
RUN ls -la /apps/home
COPY . /apps/home

WORKDIR /apps/home

EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["./docker-entry.sh"]

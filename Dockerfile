FROM postgres:15.3-alpine3.18
EXPOSE 5432

#Variaveis de ambiente
ENV POSTGRES_PASSWORD=abc1234
ENV POSTGRES_USER=jonas
ENV POSTGRES_DB=parquimetro

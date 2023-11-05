FROM amazoncorretto:17-alpine3.17
EXPOSE 8080
WORKDIR /home
# AWS CLI installation commands
#RUN yum install -y curl unzip groff less
#RUN	curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
#RUN	unzip awscliv2.zip
#RUN ./aws/install
#RUN aws --version
RUN mkdir developer && mkdir developer/postech && mkdir developer/postech/parquimetro
COPY . developer/postech/parquimetro
WORKDIR /home/developer/postech/parquimetro
RUN ls -la
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["./mvnw", "spring-boot:run"]


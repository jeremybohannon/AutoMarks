FROM maven

COPY . .

RUN mvn package

CMD java -jar target/gatewayService-*.jar
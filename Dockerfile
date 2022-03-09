FROM maven:3.6.3-openjdk-11 as build
RUN mkdir /scoutpro
COPY . /scoutpro
WORKDIR /scoutpro
RUN mvn clean package

FROM openjdk:11-jre-buster
RUN apt-get update && \
    apt-get install -y gnupg wget curl unzip --no-install-recommends && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list && \
    apt-get update -y && \
    apt-get install -y google-chrome-stable && \
    CHROMEVER=$(google-chrome --product-version | grep -o "[^\.]*\.[^\.]*\.[^\.]*") && \
    DRIVERVER=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROMEVER") && \
    wget -q --continue -P /chromedriver "http://chromedriver.storage.googleapis.com/$DRIVERVER/chromedriver_linux64.zip" && \
    unzip /chromedriver/chromedriver* -d /chromedriver
ENV SCOUTPROWEBDRIVERPATH=/chromedriver/chromedriver
ENV PSML_USERNAME=cvelenidza
ENV PSML_PASSWORD=deoreshimano
RUN mkdir /scoutpro
COPY --from=build /scoutpro/target/scoutpro.jar scoutpro/scoutpro.jar
WORKDIR /scoutpro
EXPOSE 8080
CMD ["java","-jar","scoutpro.jar"]

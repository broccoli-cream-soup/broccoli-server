FROM corretto:17

COPY build/libs/survey-0.0.1-SNAPSHOT.jar survey.jar

ENTRYPOINT java -jar survey.jar
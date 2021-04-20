# org.kie.kogito.kogito-quarkus-archetype - 1.2.0.Final #

# Setup

**1. Clone or download this git repository:**
```
git clone https://gitlab.consulting.redhat.com/intelligent-application-practice/xray/kogito-process
```
**2. For VSCode development install the following extensions:**
  - Kogito DMN
  - Kafka

**3. Run kafdrop (local kafka cluster)**
```
cd kogito-process-master/xray-process/src/test/kafka/
```
With the docker CLI run:
```
docker-compose up -d
```
Check localhost:9000 for the kafdrop dashboard

**4. Configure kafdrop cluster in the VSCode Kafka extension:**

- Navigate to the VSCode Kafka extension in the VSCode context menu (left side navigation menu)
- Click the "+" icon
- Add "localhost:9092" as a Kafka server
- Add topics named "xray" and "processedxray" if they don't already exist


# Running

- Compile and Run

    ```
     cd kogito-process-master/xray-process
     mvn clean package quarkus:dev
    ```

- Native Image (requires JAVA_HOME to point to a valid GraalVM)

    ```
    mvn clean package -Pnative
    ```
  
  native executable (and runnable jar) generated in `target/`

# Test your application

**1. Generate test Kafka messages to feed into the business process:**
- Open xray-process/src/test/kafka/TestXrayEvent.kafka
- Use the Kafka extension to "produce 10 records" on any of the sample message schemas

**2. Open localhost:8080 to view process UI**

**3. Use localhost:8080/swagger-ui to interact with the process OpenAPI**

**4. Check http://localhost:9000/topic/xray to monitor consumed messages**

**5. Check http://localhost:9000/topic/processedxray to monitor produced messages**

# Developing

Add your business assets resources (process definition, rules, decisions) into src/main/resources.

Add your java classes (data model, utilities, services) into src/main/java.

Then just build the project and run.


# Swagger documentation

The exposed service [OpenAPI specification](https://swagger.io/docs/specification) is generated at 
[/q/openapi](http://localhost:8080/q/openapi).

You can visualize and interact with the generated specification using the embbeded [Swagger UI](http://localhost:8080/q/swagger-ui) or importing the generated specification file on [Swagger Editor](https://editor.swagger.io).

In addition client application can be easily generated from the swagger definition to interact with this service.
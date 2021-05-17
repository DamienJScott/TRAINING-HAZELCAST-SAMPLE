# TRAINING-HAZELCAST-SAMPLE
TRAINING-HAZELCAST-SAMPLE

HC-SAMPLE-GATEWAY - gateway

HC-SAMPLE-MS-A - microservice A

HC-SAMPLE-MS-B - microservice B

HC-SAMPLE-MS-A-INSTANCE-2 - the instance 2 of microservice A

REGISTRY - registry

Deploy Guide:

  REGISTRY:
  
     1.run command [npm install] in root directory.
     2.run command [mvn clean install -Dmaven.test.skip=true] in root directory.
     3.run command [npm start] to start the front-end.
     4.run command [mvn] to start the back-end.
     5.Enter localhost:9000 in the browser to enter the page.
     
  HC-SAMPLE-GATEWAY:
  
     1.run command [npm install] in root directory.
     2.run command [mvn clean install -Dmaven.test.skip=true] in root directory.
     3.create the new schema in mysql with any name, then modify the [src\main\resources\config\application-dev.yml] line 47,48,49 to ur scheme name and ur db username&password.
     4.run command [npm start] to start the front-end.
     5.run command [mvn] to start the back-end.
     6.Enter localhost:9010 in the browser to enter the page.
     
  HC-SAMPLE-MS-A / HC-SAMPLE-MS-B / HC-SAMPLE-MS-A-INSTANCE-2:
  
     1.run command [mvn clean install -Dmaven.test.skip=true] in root directory.
     2.create the new schema in mysql with any name, then modify the [src\main\resources\config\application-dev.yml] line 47,48,49 to ur scheme name and ur db username&password.
     3.run command [mvn] to start the back-end.
    

# akka-custom-serialization

This project is based on the Worker Dial example available in [Akka Samples](https://github.com/akka/akka-samples/tree/2.5/akka-sample-cluster-java)

Example application that replaces default Java Serialization with custom implementation for remote messages

This project has examples for `Jackson`, `Protocol Buffers` and `Avro` serializers.

The default configuration uses `Jackson` and has `Java Serialization` turned off. 
The serializers for the other two formats are commented out, but can easily replace the default implementation.


## Running

To run the project, from a shell:

`mvn compile exec:java -Dexec.mainClass="sample.cluster.transformation.TransformationApp"`

This will start the Akka Cluster and start sending messages from the frontend to the backend


## Testing

Property based testing with [ScalaCheck](https://www.scalacheck.org/) has been added to test the
serializer implementations. To run the tests, from a shell:

`mvn clean test`
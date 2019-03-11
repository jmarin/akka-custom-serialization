package sample.cluster.serialization

import akka.serialization.SerializerWithStringManifest

class JacksonSerializerSpec extends SerializerSpec {
  val serializer: SerializerWithStringManifest = new JacksonSerializer
}

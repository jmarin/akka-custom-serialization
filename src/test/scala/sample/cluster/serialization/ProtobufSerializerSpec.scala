package sample.cluster.serialization

import akka.serialization.SerializerWithStringManifest

class ProtobufSerializerSpec extends SerializerSpec {
  override val serializer: SerializerWithStringManifest = new ProtobufSerializer
}

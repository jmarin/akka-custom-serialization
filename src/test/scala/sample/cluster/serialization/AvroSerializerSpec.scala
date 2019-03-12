package sample.cluster.serialization
import akka.serialization.SerializerWithStringManifest

class AvroSerializerSpec extends SerializerSpec {
  override val serializer: SerializerWithStringManifest = new AvroSerializer
}

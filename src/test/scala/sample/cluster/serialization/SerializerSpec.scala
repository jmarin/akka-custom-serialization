package sample.cluster.serialization

import akka.serialization.SerializerWithStringManifest
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import sample.cluster.transformation.TransformationMessages.{
  JobFailed,
  TransformationJob,
  TransformationResult
}
import sample.cluster.serialization.TransformationMessagesGenerator._

abstract class SerializerSpec
    extends FunSuite
    with GeneratorDrivenPropertyChecks
    with Matchers
    with SerializerManifest {

  val serializer: SerializerWithStringManifest

  test("TransformationJob should serialize and deserialize") {
    forAll { job: TransformationJob =>
      val bytes = serializer.toBinary(job)
      val deserialized =
        serializer.fromBinary(bytes,
                              SerializerManifest.TRANSFORMATION_JOB_MANIFEST);
      deserialized should be(job)
    }
  }

  test("TransformationResult should serialize and deserialize") {
    forAll { result: TransformationResult =>
      val bytes = serializer.toBinary(result)
      val deserialized =
        serializer.fromBinary(
          bytes,
          SerializerManifest.TRANSFORMATION_RESULT_MANIFEST);
      deserialized should be(result)
    }
  }

  test("JobFailed should serialize and deserialize") {
    forAll { jobFailed: JobFailed =>
      val bytes = serializer.toBinary(jobFailed)
      val deserialized =
        serializer.fromBinary(bytes, SerializerManifest.JOB_FAILED_MANIFEST);
      deserialized should be(jobFailed)
    }
  }

}

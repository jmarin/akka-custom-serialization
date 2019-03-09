package sample.cluster.serialization

import org.scalatest.{FunSuite, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import sample.cluster.serialization.TransformationMessagesGenerator._
import sample.cluster.transformation.TransformationMessages.{
  JobFailed,
  TransformationJob,
  TransformationResult
}

class JacksonSerializerSpec
    extends FunSuite
    with GeneratorDrivenPropertyChecks
    with Matchers {

  val serializer = new JacksonSerializer

  test("TransformationJob should serialize to and from json") {
    forAll { job: TransformationJob =>
      val bytes = serializer.toBinary(job)
      val deserialized =
        serializer.fromBinary(bytes, serializer.TRANSFORMATION_JOB_MANIFEST);
      deserialized should be(job)
    }
  }

  test("TransformationResult should serialize to and from json") {
    forAll { result: TransformationResult =>
      val bytes = serializer.toBinary(result)
      val deserialized =
        serializer.fromBinary(bytes, serializer.TRANSFORMATION_RESULT_MANIFEST);
      deserialized should be(result)
    }
  }

  test("JobFailed should serialize to and from json") {
    forAll { jobFailed: JobFailed =>
      val bytes = serializer.toBinary(jobFailed)
      val deserialized =
        serializer.fromBinary(bytes, serializer.JOB_FAILED_MANIFEST);
      deserialized should be(jobFailed)
    }
  }

}

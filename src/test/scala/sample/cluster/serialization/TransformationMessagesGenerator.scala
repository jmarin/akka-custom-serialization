package sample.cluster.serialization

import org.scalacheck.{Arbitrary, Gen}
import sample.cluster.transformation.TransformationMessages.{
  JobFailed,
  TransformationJob,
  TransformationResult
}

object TransformationMessagesGenerator {

  implicit val arbitraryTranformationJob: Arbitrary[TransformationJob] =
    Arbitrary(genTransformationJob)

  implicit val arbitraryTransformationResult: Arbitrary[TransformationResult] =
    Arbitrary(genTransformationResult)

  implicit val arbitraryJobFailed: Arbitrary[JobFailed] = Arbitrary(
    genJobFailed)

  def genTransformationJob: Gen[TransformationJob] = {
    for {
      text <- Gen.alphaStr
    } yield new TransformationJob(text)
  }

  def genTransformationResult: Gen[TransformationResult] = {
    for {
      text <- Gen.alphaStr
    } yield new TransformationResult(text)
  }

  def genJobFailed: Gen[JobFailed] = {
    for {
      reason <- Gen.alphaStr
    } yield new JobFailed(reason)
  }

}

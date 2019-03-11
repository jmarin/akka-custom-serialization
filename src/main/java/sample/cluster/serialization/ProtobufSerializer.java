package sample.cluster.serialization;

import akka.serialization.SerializerWithStringManifest;
import com.google.protobuf.InvalidProtocolBufferException;
import sample.cluster.transformation.TransformationMessages.*;
import sample.cluster.serialization.protobuf.Transformationjob;
import sample.cluster.serialization.protobuf.Transformationresult;
import sample.cluster.serialization.protobuf.Jobfailed;

import java.io.NotSerializableException;

public class ProtobufSerializer extends SerializerWithStringManifest implements SerializerManifest {

    @Override
    public int identifier() {
        return 1001;
    }

    @Override
    public String manifest(Object o) {
        if (o instanceof TransformationJob) {
            return TRANSFORMATION_JOB_MANIFEST;
        } else if (o instanceof TransformationResult) {
            return TRANSFORMATION_RESULT_MANIFEST;
        } else if (o instanceof JobFailed) {
            return JOB_FAILED_MANIFEST;
        } else throw new IllegalArgumentException("Unknown type: " + o);
    }

    @Override
    public byte[] toBinary(Object o) {
        if (o instanceof TransformationJob) {
            TransformationJob job = (TransformationJob) o;
            return Transformationjob
                    .TransformationJobMessage
                    .newBuilder()
                    .setText(job.getText())
                    .build()
                    .toByteArray();

        } else if (o instanceof TransformationResult) {
            TransformationResult result = (TransformationResult) o;
            return Transformationresult
                    .TransformationResultMessage
                    .newBuilder()
                    .setText(result.getText())
                    .build()
                    .toByteArray();
        } else if (o instanceof JobFailed) {
            JobFailed failed = (JobFailed) o;
            return Jobfailed
                    .JobFailedMessage
                    .newBuilder()
                    .setReason(failed.getReason())
                    .build()
                    .toByteArray();
        } else {
            throw new IllegalArgumentException("Cannot serialize object: " + o.getClass().getName());
        }

    }

    @Override
    public Object fromBinary(byte[] bytes, String manifest) throws NotSerializableException {
        try {
            if (manifest.equals(TRANSFORMATION_JOB_MANIFEST)) {
                return convert(Transformationjob.TransformationJobMessage.parseFrom(bytes));
            } else if (manifest.equals(TRANSFORMATION_RESULT_MANIFEST)) {
                return convert(Transformationresult.TransformationResultMessage.parseFrom(bytes));
            } else if (manifest.equals(JOB_FAILED_MANIFEST)) {
                return convert(Jobfailed.JobFailedMessage.parseFrom(bytes));
            } else {
                throw new IllegalArgumentException("Unable to handle manifest: " + manifest);
            }
        } catch (InvalidProtocolBufferException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    // fromBinary helpers

    private TransformationJob convert(Transformationjob.TransformationJobMessage message) {
        return new TransformationJob(message.getText());
    }

    private TransformationResult convert(Transformationresult.TransformationResultMessage message) {
        return new TransformationResult(message.getText());
    }

    private JobFailed convert(Jobfailed.JobFailedMessage message) {
        return new JobFailed(message.getReason());
    }


}

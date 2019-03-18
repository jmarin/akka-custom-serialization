package sample.cluster.serialization;

import akka.serialization.SerializerWithStringManifest;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.util.Utf8;
import sample.cluster.transformation.TransformationMessages.*;
import sample.cluster.transformation.serialization.avro.JobFailedPayload;
import sample.cluster.transformation.serialization.avro.TransformationJobPayload;
import sample.cluster.transformation.serialization.avro.TransformationResultPayload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AvroSerializer extends SerializerWithStringManifest implements SerializerManifest {

    @Override
    public int identifier() {
        //NOTE: 0-40 are reserved by internal Akka usage
        return 1002;
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
        DatumWriter<GenericRecord> datumWriter;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            if (o instanceof TransformationJob) {
                TransformationJob job = (TransformationJob) o;
                Schema classSchema = TransformationJobPayload.getClassSchema();
                datumWriter = new GenericDatumWriter<>(classSchema);
                GenericData.Record record = new GenericData.Record(classSchema);
                record.put("text", job.getText());
                BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
                datumWriter.write(record, encoder);
                encoder.flush();
                return stream.toByteArray();

            } else if (o instanceof TransformationResult) {
                TransformationResult result = (TransformationResult) o;
                Schema classSchema = TransformationResultPayload.getClassSchema();
                datumWriter = new GenericDatumWriter<>(classSchema);
                GenericData.Record record = new GenericData.Record(classSchema);
                record.put("text", result.getText());
                BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
                datumWriter.write(record, encoder);
                encoder.flush();
                return stream.toByteArray();
            } else if (o instanceof JobFailed) {
                JobFailed failed = (JobFailed) o;
                Schema classSchema = JobFailedPayload.getClassSchema();
                datumWriter = new GenericDatumWriter<>(classSchema);
                GenericData.Record record = new GenericData.Record(classSchema);
                record.put("reason", failed.getReason());
                BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
                datumWriter.write(record, encoder);
                encoder.flush();
                return stream.toByteArray();
            } else {
                throw new IllegalArgumentException("Cannot serialize object: " + o.getClass().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object fromBinary(byte[] bytes, String manifest) throws NotSerializableException {
        DatumReader<GenericRecord> datumReader;
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
        Object object = null;
        try {
            switch (manifest) {
                case TRANSFORMATION_JOB_MANIFEST:
                    Schema transformationJobSchema = TransformationJobPayload.getClassSchema();
                    datumReader = new GenericDatumReader<>(transformationJobSchema);
                    GenericRecord jobRecord = datumReader.read(null, decoder);
                    String jobText = ((Utf8) jobRecord.get("text")).toString();

                    TransformationJobPayload jobPayload = TransformationJobPayload
                            .newBuilder()
                            .setText(jobText)
                            .build();

                    object = new TransformationJob(jobPayload.getText().toString());
                    break;

                case TRANSFORMATION_RESULT_MANIFEST:
                    Schema transformationResultSchema = TransformationResultPayload.getClassSchema();
                    datumReader = new GenericDatumReader<>(transformationResultSchema);
                    GenericRecord resultRecord = datumReader.read(null, decoder);
                    String resultText = ((Utf8) resultRecord.get("text")).toString();

                    TransformationResultPayload resultPayload = TransformationResultPayload
                            .newBuilder()
                            .setText(resultText)
                            .build();
                    object = new TransformationResult(resultPayload.getText().toString());
                    break;

                case JOB_FAILED_MANIFEST:
                    Schema jobFailedSchema = TransformationResultPayload.getClassSchema();
                    datumReader = new GenericDatumReader<>(jobFailedSchema);
                    GenericRecord jobFailedRecord = datumReader.read(null, decoder);
                    String failedReason = ((Utf8) jobFailedRecord.get("text")).toString();

                    JobFailedPayload jobFailedPayload = JobFailedPayload
                            .newBuilder()
                            .setReason(failedReason)
                            .build();

                    object = new JobFailed(jobFailedPayload.getReason().toString());
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }



}

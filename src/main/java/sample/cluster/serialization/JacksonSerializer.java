package sample.cluster.serialization;

import akka.serialization.SerializerWithStringManifest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sample.cluster.transformation.TransformationMessages.*;

import java.io.IOException;

public class JacksonSerializer extends SerializerWithStringManifest {

    public final String JOB_FAILED_MANIFEST = "JobFailed";
    public final String TRANSFORMATION_JOB_MANIFEST = "TransformationJob";
    public final String TRANSFORMATION_RESULT_MANIFEST = "TransformationResult";


    @Override
    public int identifier() {
        return 1000;
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(o);
            return s.getBytes();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object fromBinary(byte[] bytes, String manifest) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            switch (manifest) {
                case JOB_FAILED_MANIFEST:
                    return mapper.readValue(bytes, JobFailed.class);
                case TRANSFORMATION_JOB_MANIFEST:
                    return mapper.readValue(bytes, TransformationJob.class);
                case TRANSFORMATION_RESULT_MANIFEST:
                    return mapper.readValue(bytes, TransformationResult.class);
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
}

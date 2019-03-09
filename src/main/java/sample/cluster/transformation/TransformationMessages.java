package sample.cluster.transformation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public interface TransformationMessages {

    class TransformationJob implements Serializable {
        private final String text;

        @JsonCreator
        public TransformationJob(@JsonProperty("text") String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "Transformation job (" + text + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TransformationJob that = (TransformationJob) o;
            return Objects.equals(getText(), that.getText());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getText());
        }
    }

    class TransformationResult implements Serializable {
        private final String text;

        @JsonCreator
        public TransformationResult(@JsonProperty("text") String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "TransformationResult(" + text + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TransformationResult that = (TransformationResult) o;
            return Objects.equals(getText(), that.getText());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getText());
        }
    }

    class JobFailed implements Serializable {
        private final String reason;

        @JsonCreator
        public JobFailed(@JsonProperty("reason") String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return "JobFailed(" + reason + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            JobFailed jobFailed = (JobFailed) o;
            return Objects.equals(getReason(), jobFailed.getReason());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getReason());
        }
    }

    public static final String BACKEND_REGISTRATION = "BackedRegistration";

}


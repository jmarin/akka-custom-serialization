package sample.cluster.transformation;

import java.io.Serializable;

public interface TransformationMessages {

    public static class TransformationJob implements Serializable {
        private final String text;

        public TransformationJob(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public static class TransformationResult implements Serializable {
        private final String text;

        public TransformationResult(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "TransformationResult(" + text + ")";
        }
    }

    public static class JobFailed implements Serializable {
        private final String reason;

        public JobFailed(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return "JobFailed(" + reason + ")";
        }
    }

    public static final String BACKEND_REGISTRATION = "BackedRegistration";

}


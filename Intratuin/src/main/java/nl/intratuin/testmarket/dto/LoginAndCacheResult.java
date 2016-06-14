package nl.intratuin.testmarket.dto;

public class LoginAndCacheResult {
        private String message;
        private String accessKey;

        public LoginAndCacheResult(String message, String accessKey) {
            this.message = message;
            this.accessKey = accessKey;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String fullName) {
            this.message = fullName;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }
}

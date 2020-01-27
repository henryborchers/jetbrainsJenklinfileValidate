package jenklint;

public class JenkinsValidation {
    private boolean valid;
    private String responseText;

    public JenkinsValidation(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setResponseText(String response) {
        responseText = response;
    }

    public String getResponseText() {
        return responseText;
    }
}

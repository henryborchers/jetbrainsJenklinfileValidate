package jenklint;

import java.io.IOException;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;


public class JenkinsServer {
    private String serverUrl;

    public JenkinsServer(String serverUrl) {

        this.serverUrl = serverUrl;
    }

    public JenkinsValidation validate(Jenkinsfile jenkinsfile) {
        String response = "";
        JenkinsValidation result;
        boolean isValid;
        try {
            response = requestServerValidation(jenkinsfile.data());
            if (response.contains("Jenkinsfile successfully validated")) {
                isValid = true;
            } else {
                isValid = false;
            }
        } catch (IOException ex) {
            JenkinsValidation errorResult = new JenkinsValidation(false);
            errorResult.setResponseText("Unknown error when validating Jenkinsfile");
            return errorResult;
        }
        result = new JenkinsValidation(isValid);
        result.setResponseText(response);
        return result;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    private String requestServerValidation(String data) throws IOException {
        final String jenkinsValidationUrl = serverUrl + "/pipeline-model-converter/validate";
        final Request jenkinsPipelineFile = Request.Post(jenkinsValidationUrl).bodyForm(Form.form().add("jenkinsfile", data).build());
        final Content returnedContent = jenkinsPipelineFile.execute().returnContent();
        return returnedContent.asString();
    }
}

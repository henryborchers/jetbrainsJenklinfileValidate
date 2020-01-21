package jenklint;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JenklintRunner {
    private final String jenklintPath;
    private final String jenkinsUrl;

    public JenklintRunner(String jenklintPath, String jenkinsUrl) {
        this.jenklintPath = jenklintPath;
        this.jenkinsUrl = jenkinsUrl;
    }

    public String getJenklintPath() {
        return jenklintPath;
    }

    public String getResults(String projectPath) {
        StringBuilder output = new StringBuilder();
        ProcessBuilder b = new ProcessBuilder(jenklintPath);
        Map<String, String> env = b.environment();
        env.put("JENKINS_URL", jenkinsUrl);

        b.directory(new File(projectPath));

        try {
            String line;
            Process p = b.start();
            InputStreamReader inputStreamReader = new InputStreamReader(p.getInputStream(), StandardCharsets.US_ASCII);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            try {
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

            } finally {
                reader.close();
            }

            p.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
            return "Unable to run Jenklint. Check settings";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error Interrupted";
        }
        return output.toString();

    }
}

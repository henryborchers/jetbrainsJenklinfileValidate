package jenklint;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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
//        TODO: replace with process builder
        Runtime rt = Runtime.getRuntime();
        try {
            String jenklintCommand = jenklintPath + " " + jenkinsUrl;
            Process p = rt.exec(jenklintCommand, null, new File(projectPath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderor = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            while ((line = stderor.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = p.waitFor();
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

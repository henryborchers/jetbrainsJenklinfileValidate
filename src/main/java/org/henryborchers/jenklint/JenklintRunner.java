package org.henryborchers.jenklint;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class JenklintRunner {
    private final String JENKLINT_PATH;
    public JenklintRunner(String jenklintPath){
        JENKLINT_PATH = jenklintPath;
    }

    public String getJenklintPath() {
        return JENKLINT_PATH;
    }
    public String getResults(String projectPath){
        StringBuilder output = new StringBuilder();
        Runtime rt = Runtime.getRuntime();
        try {
            Process p = rt.exec(JENKLINT_PATH, null, new File(projectPath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderor = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null){
                output.append(line).append("\n");
            }
            while ((line = stderor.readLine()) != null){
                output.append(line).append("\n");
            }

            int exitVal = p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
            return "Error Interrupted";
        }
        return output.toString();

    }
}

package jenklint;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;


public class Jenkinsfile {
    private VirtualFile jenkinsfile;

    public Jenkinsfile(@NotNull VirtualFile jenkinsfile) {
        this.jenkinsfile = jenkinsfile;
    }

    public VirtualFile getJenkinsfile() {
        return jenkinsfile;
    }

    public String data() {
        try {
            return IOUtils.toString(jenkinsfile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }
}

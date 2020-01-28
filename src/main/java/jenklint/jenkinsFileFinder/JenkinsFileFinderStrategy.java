package jenklint.jenkinsFileFinder;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public interface JenkinsFileFinderStrategy {
    VirtualFile locate(Project project);
}

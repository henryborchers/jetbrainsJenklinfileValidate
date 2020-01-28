package jenklint.jenkinsFileFinder;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

public class FindInProject implements JenkinsFileFinderStrategy {
    @Override
    public VirtualFile locate(Project project) {
        return ProjectRootManager.getInstance(project).getContentRoots()[0].findChild("Jenkinsfile");
    }
}

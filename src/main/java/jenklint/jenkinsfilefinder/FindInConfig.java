package jenklint.jenkinsfilefinder;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

public class FindInConfig implements JenkinsFileFinderStrategy {
    @Override
    public VirtualFile locate(Project project) {
        PropertiesComponent projectInstance = PropertiesComponent.getInstance(project);
        final String jenkinsfile_path = projectInstance.getValue("jenkinsfile");
        if ( jenkinsfile_path == null || jenkinsfile_path.isEmpty()) {
            return null;
        }
        LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
        return localFileSystem.findFileByPath(jenkinsfile_path);
    }
}

package jenklint.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import jenklint.JenkinsServer;
import jenklint.JenkinsValidation;
import jenklint.Jenkinsfile;
import jenklint.ui.JenkinsToolWindow;
import org.jetbrains.annotations.NotNull;


public class ValidateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        if (project == null) {
            return;
        }
        PropertiesComponent projectInstance = PropertiesComponent.getInstance(project);

        JenkinsToolWindow jenkinsToolWindow = ServiceManager.getService(project, JenkinsToolWindow.class);

        final VirtualFile jenkinsfFileFile = ProjectRootManager.getInstance(project).getContentRoots()[0].findFileByRelativePath("Jenkinsfile");
        if (jenkinsfFileFile == null) {
            jenkinsToolWindow.print("Unable to locate a Jenkinsfile");
            return;
        }

        final Jenkinsfile jenkinsfile = new Jenkinsfile(jenkinsfFileFile);
        final String serverUrl = projectInstance.getValue("jenkinsURL");
        if (serverUrl == null) {
            jenkinsToolWindow.print("Jenkins server URL not set");
            return;
        }

        JenkinsServer server = new JenkinsServer(serverUrl);
        jenkinsToolWindow.print("Checking " + jenkinsfFileFile.getPath() + " with " + serverUrl + ".");
        JenkinsValidation validataion = server.validate(jenkinsfile);
        if (validataion.isValid()) {
            jenkinsToolWindow.print("Jenkinsfile is valid");
        } else {
            jenkinsToolWindow.print("Found issues with " + jenkinsfFileFile.getPath());
            jenkinsToolWindow.print(validataion.getResponseText());
        }
    }
}

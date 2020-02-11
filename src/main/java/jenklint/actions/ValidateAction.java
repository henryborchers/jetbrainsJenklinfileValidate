package jenklint.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import jenklint.JenkinsServer;
import jenklint.JenkinsValidation;
import jenklint.jenkinsfilefinder.FindInConfig;
import jenklint.jenkinsfilefinder.FindInProject;
import jenklint.jenkinsfilefinder.JenkinsFileFinderStrategy;
import jenklint.ui.JenkinsToolWindow;
import org.jetbrains.annotations.NotNull;


public class ValidateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);


        if (project == null ) {
            return;
        }

        JenkinsToolWindow jenkinsToolWindow = ServiceManager.getService(project, JenkinsToolWindow.class);
        jenkinsToolWindow.clear();
        final VirtualFile jenkinsfFileFile = getJenkinsFile(project);
        if (jenkinsfFileFile == null) {
            jenkinsToolWindow.print("Unable to locate a Jenkinsfile");
            return;
        }
        if (!jenkinsfFileFile.exists()) {
            jenkinsToolWindow.print("Invalid settings for Jenkinsfile" + jenkinsfFileFile.getPath());
            return;
        }
        PropertiesComponent projectInstance = PropertiesComponent.getInstance(project);
        final String serverUrl = projectInstance.getValue("jenkinsURL");
        if (serverUrl == null) {
            jenkinsToolWindow.print("Jenkins server URL not set");
            return;
        }

        CommandProcessor commandProcessor = CommandProcessor.getInstance();
        commandProcessor.executeCommand(project, new Runnable() {
            @Override
            public void run() {
                FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
                final Document jenkinsfileDocument = fileDocumentManager.getDocument(jenkinsfFileFile);
                if (jenkinsfileDocument == null){
                    return;
                }
                JenkinsServer server = new JenkinsServer(serverUrl);
                jenkinsToolWindow.print("Checking " + jenkinsfFileFile.getPath() + " with " + serverUrl + ".");
                JenkinsValidation validation = server.validateDocument(jenkinsfileDocument);
                if (validation.isValid()) {
                    jenkinsToolWindow.print("Jenkinsfile is valid");
                } else {
                    jenkinsToolWindow.print("Found issues with " + jenkinsfFileFile.getPath());
                    jenkinsToolWindow.print(validation.getResponseText());
                }
            }
        }, "Validating", null);

    }

    private VirtualFile getJenkinsFile(Project project) {
        JenkinsFileFinderStrategy[] strategies = {
            new FindInConfig(),
            new FindInProject(),
        };

        for (JenkinsFileFinderStrategy strategy: strategies) {
            VirtualFile jenkinsfile = strategy.locate(project);
            if (jenkinsfile == null) {
                continue;
            }
            return jenkinsfile;

        }
        return null;
    }
}

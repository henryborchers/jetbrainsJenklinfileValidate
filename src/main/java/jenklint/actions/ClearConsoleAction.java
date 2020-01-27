package jenklint.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import jenklint.ui.JenkinsToolWindow;
import org.jetbrains.annotations.NotNull;

public class ClearConsoleAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        if (project == null) {
            return;
        }

        JenkinsToolWindow jenkinsToolWindow = ServiceManager.getService(project, JenkinsToolWindow.class);
        jenkinsToolWindow.clear();
    }
}

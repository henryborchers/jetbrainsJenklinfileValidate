package jenklint.ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;


public class JenkinsToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        JenkinsToolWindow jenkinsToolWindow = ServiceManager.getService(project, JenkinsToolWindow.class);
        jenkinsToolWindow.initToolWindow(project, toolWindow);

    }
}


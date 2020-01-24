package jenklint;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import jenklint.ui.JenkinsToolWindow;
import org.jetbrains.annotations.NotNull;

public class ValidateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        if(project == null){
            return;
        }
        System.out.println("CLICY!!");
        JenkinsToolWindow jenkinsToolWindow = ServiceManager.getService(project, JenkinsToolWindow.class);
        jenkinsToolWindow.print("CLINKKY");

//        TODO: Open jenkinsfile and send it to the jenkins server, and report back here
    }
}

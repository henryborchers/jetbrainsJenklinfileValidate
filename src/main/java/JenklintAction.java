import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;


public class JenklintAction extends AnAction{
    private String getProjectRoot(Project project){
        ModuleManager manager = ModuleManager.getInstance(project);
        Module module = manager.getModules()[0];
        return module.getProject().getBasePath();
    }
    public JenklintAction(){
        super("Validate Jenkinsfile");
    }
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        String title = "Jenklint result";
        JenklintRunner runner = new JenklintRunner("jenklint");
        String message = runner.getResults(getProjectRoot(project));

        Messages.showMessageDialog(project, message, title, Messages.getInformationIcon());
    }
}

package jenklint;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class JenklintAction extends AnAction {

    private String getProjectRoot(Project project) {
        ModuleManager manager = ModuleManager.getInstance(project);
        Module module = manager.getModules()[0];
        return module.getProject().getBasePath();
    }

    public JenklintAction() {
        super("Run pyjenklint");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        String title = "Jenklint result";
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String jenklint = instance.getValue("jenklint.command_path");
        if (jenklint == null) {
            jenklint = "/home/henry/.local/bin/jenklint";

        }
        PropertiesComponent projectInstance = PropertiesComponent.getInstance(project);
        String jenkinsUrl = projectInstance.getValue("jenkinsURL");

        JenklintRunner runner = new JenklintRunner(jenklint, jenkinsUrl);
        String message = runner.getResults(getProjectRoot(project));

        Messages.showMessageDialog(project, message, title, Messages.getInformationIcon());
    }
}

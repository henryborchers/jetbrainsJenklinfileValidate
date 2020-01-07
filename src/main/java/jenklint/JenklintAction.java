package jenklint;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ide.util.PropertiesComponent;

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
        PropertiesComponent instance = PropertiesComponent.getInstance(project);
        String jenklint = instance.getValue("jenklint.command_path");
//        FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);

//        final VirtualFile f = FileChooser.chooseFile(descriptor, project, null);
        if(jenklint == null){
            jenklint = "/home/henry/.local/bin/jenklint";
            instance.setValue("jenklint.command_path",jenklint);

        }
        JenklintRunner runner = new JenklintRunner(jenklint);
        String message = runner.getResults(getProjectRoot(project));

        Messages.showMessageDialog(project, message, title, Messages.getInformationIcon());
    }
}

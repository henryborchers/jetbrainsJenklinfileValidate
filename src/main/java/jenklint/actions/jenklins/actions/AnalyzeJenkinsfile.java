package jenklint.actions.jenklins.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import jenklint.JenkinsServer;
import jenklint.JenkinsValidation;
import jenklint.ui.JenkinsToolWindow;
import org.jetbrains.annotations.NotNull;

public class AnalyzeJenkinsfile extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = getEventProject(e);
        if (project == null) {
            return;
        }
        CommandProcessor commandProcessor = CommandProcessor.getInstance();
//        TODO: make sure that the console opens
        commandProcessor.executeCommand(project, new Runnable() {
            @Override
            public void run() {
                PsiFile jenkinsfile  = e.getData(LangDataKeys.PSI_FILE);

                if(jenkinsfile == null){
                    return;
                }

                PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
                final Document jenkinsfileDocument = psiDocumentManager.getDocument(jenkinsfile);
                if(jenkinsfileDocument == null){
                    return;
                }

                psiDocumentManager.commitAllDocuments();
                JenkinsToolWindow jenkinsToolWindow = ServiceManager.getService(project, JenkinsToolWindow.class);
                if (jenkinsToolWindow == null){
                    return;
                }
                PropertiesComponent projectInstance = PropertiesComponent.getInstance(project);

                final String serverUrl = projectInstance.getValue("jenkinsURL");
                if (serverUrl == null) {
                    jenkinsToolWindow.print("Jenkins server URL not set");
                    return;
                }
                JenkinsServer server = new JenkinsServer(serverUrl);
                JenkinsValidation validation = server.validateDocument(jenkinsfileDocument);
                jenkinsToolWindow.clear();
                if (validation.isValid()) {
                    jenkinsToolWindow.print("Jenkinsfile is valid");
                } else {
                    jenkinsToolWindow.print("Found issues with " + jenkinsfile.getName());
                    jenkinsToolWindow.print(validation.getResponseText());
                }
            }
        }, "Analysing Jenkinsfile", null);

    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiFile jenkinsfile  = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if(jenkinsfile == null || editor == null){
            e.getPresentation().setEnabled(false);
            return;
        }
        if (!jenkinsfile.getName().equals("Jenkinsfile")){
            e.getPresentation().setEnabled(false);
            return;
        }
        e.getPresentation().setEnabled(true);
    }
}

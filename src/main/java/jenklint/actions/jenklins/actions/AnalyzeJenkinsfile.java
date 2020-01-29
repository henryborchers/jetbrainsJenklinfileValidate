package jenklint.actions.jenklins.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class AnalyzeJenkinsfile extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile jenkinsfile  = e.getData(LangDataKeys.PSI_FILE);
        if(jenkinsfile == null){
            return;
        }
        final String jenkinsfileData = jenkinsfile.getText();
//        TODO: Runs this in the validation server
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiFile jenkinsfile  = e.getData(LangDataKeys.PSI_FILE);
        if(jenkinsfile == null){
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

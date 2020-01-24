package jenklint;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ValidateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("CLICY!!");
//        TODO: Open jenkinsfile and send it to the jenkins server, and report back here
    }
}

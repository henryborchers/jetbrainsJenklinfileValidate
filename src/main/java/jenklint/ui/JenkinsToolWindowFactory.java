package jenklint.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;


public class JenkinsToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {

        JenkinsToolWindow jenkinsToolWindow = new JenkinsToolWindow();

        DefaultActionGroup defaultActionGroup = new DefaultActionGroup();

        SimpleToolWindowPanel filterPanel = new SimpleToolWindowPanel(false);

        ActionManager actionManager = ActionManager.getInstance();
        defaultActionGroup.addAction(actionManager.getAction("MyPlugin.Textboxes"));

        ActionToolbar actionToolbar = actionManager.createActionToolbar(ActionPlaces.CHANGES_VIEW_TOOLBAR, defaultActionGroup, false);

        filterPanel.setToolbar(actionToolbar.getComponent());
        filterPanel.setContent(jenkinsToolWindow.getContent());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        toolWindow.getContentManager().addContent(contentFactory.createContent(filterPanel, "Validate", false));
    }
}


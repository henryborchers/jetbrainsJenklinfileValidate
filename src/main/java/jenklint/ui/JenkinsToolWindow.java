package jenklint.ui;

import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;


public class JenkinsToolWindow implements Disposable {
    private JPanel panel1;
    private ConsoleView consoleView;
    private ContentManager contentManager;

    void initToolWindow(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        contentManager = toolWindow.getContentManager();
        recreateContent(project);
    }

    public void recreateContent(@NotNull Project project) {
        contentManager.removeAllContents(true);
        TextConsoleBuilder builder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        consoleView = builder.getConsole();
        SimpleToolWindowPanel filterPanel = new SimpleToolWindowPanel(false);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        ActionToolbar actionToolbar = createToolbar();
        filterPanel.setToolbar(actionToolbar.getComponent());
        filterPanel.setContent(consoleView.getComponent());


        Content content = contentFactory.createContent(filterPanel, "Validate", false);
        contentManager.addContent(content);
        consoleView.print("STARTED!!!\n", ConsoleViewContentType.NORMAL_OUTPUT);


    }

    private ActionToolbar createToolbar() {
        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup defaultActionGroup = new DefaultActionGroup();
        defaultActionGroup.addAction(actionManager.getAction("JenkinsPlugin.pyjenklint"));
        defaultActionGroup.addAction(actionManager.getAction("JenkinsPlugin.validate"));
        return actionManager.createActionToolbar(ActionPlaces.CHANGES_VIEW_TOOLBAR, defaultActionGroup, false);
    }

    @Override
    public void dispose() {
        Disposer.dispose(consoleView);
    }
}

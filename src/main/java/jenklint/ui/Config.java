package jenklint.ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Config implements Configurable {
    private JPanel rootPanel;
    private JTextField jenkinsUrl;
    private TextFieldWithBrowseButton jenkinsFile;
    public static final String DISPLAY_NAME = "Jenklint";
    private Project project;

    public Config(@NotNull Project project) {
        this.project = project;
        jenkinsFile.setText(PropertiesComponent.getInstance(project).getValue("jenkinsfile"));
        jenkinsUrl.setText(PropertiesComponent.getInstance(project).getValue("jenkinsURL"));

        jenkinsUrl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Selecting Jenkins URL");
            }
        });

        jenkinsFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final FileChooserDescriptor d = FileChooserDescriptorFactory.createSingleFileDescriptor();

                VirtualFile initialFile =
                        StringUtil.isNotEmpty(jenkinsFile.getText()) ? LocalFileSystem.getInstance().findFileByPath(jenkinsFile.getText()) : null;

                VirtualFile file = FileChooser.chooseFile(d, project, initialFile);

                if (file != null) {
                    jenkinsFile.setText(file.getCanonicalPath());
                }
            }
        });
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return rootPanel;
    }

    @Override
    public boolean isModified() {
        PropertiesComponent projectInstance = PropertiesComponent.getInstance(this.project);
        if (!jenkinsFile.getText().equals(projectInstance.getValue("jenkinsfile"))) {
            return true;
        }

        if (!jenkinsUrl.getText().equals(projectInstance.getValue("jenkinsURL"))) {
            return true;
        }

        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance(project).setValue("jenkinsfile", jenkinsFile.getText());
        PropertiesComponent.getInstance(project).setValue("jenkinsURL", jenkinsUrl.getText());
    }
}

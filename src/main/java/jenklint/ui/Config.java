package jenklint.ui;

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
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ide.util.PropertiesComponent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Config implements Configurable {
    private JPanel rootPanel;
    private TextFieldWithBrowseButton jenklintCommand;
    private JTextField JenkinsURL;
    private TextFieldWithBrowseButton jenkinsFile;
    public final String DISPLAY_NAME = "Jenklint";
    private Project project;
    private boolean modified = false;

    //    public Config() {
    public Config(@NotNull Project project) {
        this.project = project;
        jenkinsFile.setText(PropertiesComponent.getInstance().getValue("jenkinsfile"));
        JenkinsURL.setText(PropertiesComponent.getInstance().getValue("jenkinsURL"));
        jenklintCommand.setText(PropertiesComponent.getInstance().getValue("jenklintCommand"));

        jenklintCommand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final FileChooserDescriptor d = FileChooserDescriptorFactory.createSingleFileDescriptor();
                VirtualFile initialFile = StringUtil.isNotEmpty(jenklintCommand.getText()) ? LocalFileSystem.getInstance().findFileByPath(jenklintCommand.getText()) : null;
                VirtualFile file = FileChooser.chooseFile(d, project, initialFile);
                if (file != null) {
                    jenklintCommand.setText(file.getCanonicalPath());
                    modified = true;
                }
            }
        });

        JenkinsURL.addActionListener(new ActionListener() {
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
                    modified = true;
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
        return this.modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance().setValue("jenkinsfile", jenkinsFile.getText());
        PropertiesComponent.getInstance().setValue("jenklintCommand", jenklintCommand.getText());
        PropertiesComponent.getInstance().setValue("jenkinsURL", JenkinsURL.getText());
    }
}

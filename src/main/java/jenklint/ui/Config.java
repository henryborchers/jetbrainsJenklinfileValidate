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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Config implements Configurable {
    private JPanel rootPanel;
    private TextFieldWithBrowseButton jenklintCommand;
    private JTextField textField1;
    public final String DISPLAY_NAME = "Jenklint";
    private Project project;

//    public Config() {
    public Config(@NotNull Project project) {
        this.project = project;
        jenklintCommand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("something");
                final FileChooserDescriptor d = FileChooserDescriptorFactory.createSingleFileDescriptor();
                VirtualFile initialFile = StringUtil.isNotEmpty(jenklintCommand.getText()) ? LocalFileSystem.getInstance().findFileByPath(jenklintCommand.getText()) : null;
                VirtualFile file = FileChooser.chooseFile(d, project, initialFile);
                if (file != null) {
                    jenklintCommand.setText(file.getCanonicalPath());
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
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}

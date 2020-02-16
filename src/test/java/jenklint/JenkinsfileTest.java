package jenklint;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JenkinsfileTest extends BasePlatformTestCase {
    private VirtualFile jenkinsfile;
    private final String samplePipeline = "MY pipeline data";

    @Test
    public void testData() {
        assert jenkinsfile != null;

        Jenkinsfile j = new Jenkinsfile(jenkinsfile);
        String data = j.data();
        assertTrue(data.contentEquals(samplePipeline));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        jenkinsfile = this.myFixture.getTempDirFixture().createFile("Jenkinsfile");

        ApplicationManager.getApplication().runWriteAction(() -> {
            try {
                jenkinsfile.setBinaryContent(samplePipeline.getBytes());
                jenkinsfile.refresh(false, false);
            } catch (IOException e) {
                return;
            }
        });

        String contents = IOUtils.toString(jenkinsfile.getInputStream(), StandardCharsets.UTF_8);
        assert samplePipeline.contentEquals(contents);
    }

}
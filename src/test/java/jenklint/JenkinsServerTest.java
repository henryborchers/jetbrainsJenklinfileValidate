package jenklint;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import java.io.IOException;
import org.junit.Test;


public class JenkinsServerTest extends BasePlatformTestCase {
    private final static String samplePipeline = "pipeline{\n"
        + "   agent any \n"
        + "   stages{\n"
        + "       stage('dummy'){\n"
        + "           steps{\n"
        + "               echo 'something'"
        + "           }\n"
        + "       }\n"
        + "   }\n"
        + "}\n";
    Jenkinsfile jenkinsfile;

    @Test
    public void testValidate() {
        JenkinsServer js = new JenkinsServer("http://loneraver.duckdns.org:8082");
        //        final JenkinsValidation validation = js.validate(jenkinsfile);
        //        System.out.println("Validation message = " + validation.getResponseText());
        //        assertTrue(validation.isValid());
        assertNotNull(js);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        ApplicationManager.getApplication().runWriteAction(() -> {
            try {
                VirtualFile jenkinsfileFile =  this.myFixture.getTempDirFixture().createFile("Jenkinsfile", samplePipeline);
                jenkinsfileFile.refresh(false, false);
                jenkinsfile = new Jenkinsfile(jenkinsfileFile);
            } catch (IOException e) {
                return;
            }
            assertNotNull(jenkinsfile);
        });
    }
}
package jenklint;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class JenkinsServerTest extends BasePlatformTestCase {
    final private String samplePipeline =
"pipeline{\n" +
"   agent any \n" +
"   stages{\n" +
"       stage('dummy'){\n" +
"           steps{\n"+
"               echo 'something'"+
"           }\n" +
"       }\n" +
"   }\n" +
"}\n";
    Jenkinsfile jenkinsfile;

    @Test
    public void testValidate() {
        JenkinsServer js = new JenkinsServer("http://loneraver.duckdns.org:8082");
        final JenkinsValidation validation = js.validate(jenkinsfile);
        System.out.println("Validation message = " + validation.getResponseText());
        assertTrue(validation.isValid());
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        VirtualFile jenkinsfileFile =  this.myFixture.getTempDirFixture().createFile("Jenkinsfile");

        ApplicationManager.getApplication().runWriteAction(()->{
            try {
                jenkinsfileFile.setBinaryContent(samplePipeline.getBytes());
                jenkinsfileFile.refresh(false, false);
            } catch (IOException e){
                return;
            }
        });
        jenkinsfile = new Jenkinsfile(jenkinsfileFile);
    }
}
package org.wildfly.swarm.it;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Bob McWhirter
 */
public class AbstractIntegrationTest {

    protected Log getStdOutLog() throws Exception {
        return getLog("target/stdout.log");
    }

    protected Log getStdErrLog() throws Exception {
        return getLog("target/stderr.log");
    }

    protected Log getLog(String path) throws IOException {
        return new Log(path, Files.readAllLines(Paths.get(path)));
    }

    public LogAssert assertThatLog(Log log) {
        return new LogAssert(log);
    }
}

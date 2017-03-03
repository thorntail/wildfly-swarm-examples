package org.wildfly.swarm.it;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

    public static String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Accept", "*/*");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return content.toString();
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.roothaan.EventSetToken;
import org.roothaan.LoginToken;
import org.roothaan.SyncSettings;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JotihuntSync {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * We are allowed to sync every minute.
     */
    private final long timeBetweenSyncs = TimeUnit.MINUTES.toMillis(1);

    private SyncSettings settings;

    public static void main(String[] args) {
        new JotihuntSync().start();
    }

    public void start() {
        settings = SyncSettings.build();
        LoginToken apikey = getApiKey();
        setEvent(apikey);
        scheduleSync(apikey);
    }

    private void scheduleSync(LoginToken apikey) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(getTask(apikey), 0, timeBetweenSyncs);
    }

    private LoginToken getApiKey() {
        // Login through API

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(settings.getAuthEndpoint()).openConnection();
            conn.setRequestProperty("authenticationUsername", settings.username);
            conn.setRequestProperty("authenticationPassword", settings.password);

            // parse result and return key
            LoginToken token = mapper.readValue(conn.getInputStream(), LoginToken.class);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
        return null;
    }

    private EventSetToken setEvent(LoginToken apiKey) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(settings.getEventSetEndPoint()).openConnection();
            conn.setRequestProperty("authenticationToken", apiKey.token);
            conn.connect();

            // parse result and return key
            EventSetToken token = mapper.readValue(conn.getInputStream(), EventSetToken.class);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
        return null;
    }

    private TimerTask getTask(final LoginToken apikey) {

        return new TimerTask() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(settings.getSyncEndPoint() + "?sessionId=" + URLEncoder.encode(apikey.token, "UTF-8"));
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    String content = new BufferedReader(new InputStreamReader(conn.getInputStream())).lines().collect(Collectors.joining("\n"));
                    if (!content.isEmpty()) {
                        throw new JotihuntSyncException("Expected no content, got: " + content);
                    }
                } catch (IOException | JotihuntSyncException e) {
                    e.printStackTrace();
                } finally {
                    if (null != conn) {
                        conn.disconnect();
                    }
                }
            }
        };
    }
}

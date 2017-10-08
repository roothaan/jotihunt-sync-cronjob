package org.roothaan;

public class SyncSettings {

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String ENDPOINT_KEY = "endpoint";
    private static final String EVENT_ID_KEY = "eventId";
    private static final String SYNC_FREQUENCY_KEY = "syncFrequency";

    public String username;
    public String password;
    public String endpoint;
    public int eventId;
    // In minutes
    public int syncFrequency;

    private String authEndpoint = "/api/auth";
    private String syncEndpoint = "/2013/cronjob/SyncJotihunt.php";
    private String eventSetEndpoint = "/api/event/set/?eventId=";

    public String getAuthEndpoint() {
        return endpoint + authEndpoint;
    }

    public String getSyncEndPoint() {
        return endpoint + syncEndpoint;
    }

    public String getEventSetEndPoint() {
        return endpoint + eventSetEndpoint + eventId;
    }

    public static SyncSettings build() {
        SyncSettings settings = new SyncSettings();
        settings.username = System.getenv(USERNAME_KEY);
        settings.password = System.getenv(PASSWORD_KEY);
        settings.endpoint = System.getenv(ENDPOINT_KEY);
        if (null != System.getenv(SYNC_FREQUENCY_KEY)) {
            settings.syncFrequency = Integer.parseInt(System.getenv(SYNC_FREQUENCY_KEY));
        }
        if (null != System.getenv(EVENT_ID_KEY)) {
            settings.eventId = Integer.parseInt(System.getenv(EVENT_ID_KEY));
        }

        if (null == settings.username && null != System.getProperty(USERNAME_KEY)) {
            settings.username = System.getProperty(USERNAME_KEY);
        }

        if (null == settings.password && null != System.getProperty(PASSWORD_KEY)) {
            settings.password = System.getProperty(PASSWORD_KEY);
        }

        if (null == settings.endpoint && null != System.getProperty(ENDPOINT_KEY)) {
            settings.endpoint = System.getProperty(ENDPOINT_KEY);
        }

        if (0 == settings.eventId && null != System.getProperty(EVENT_ID_KEY)) {
            settings.eventId = Integer.parseInt(System.getProperty(EVENT_ID_KEY));
        }

        if (0 == settings.syncFrequency && null != System.getProperty(SYNC_FREQUENCY_KEY)) {
            settings.syncFrequency = Integer.parseInt(System.getProperty(SYNC_FREQUENCY_KEY));
        }

        return settings;
    }
}

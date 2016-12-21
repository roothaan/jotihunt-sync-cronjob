package org.roothaan;

public class EventSetToken {
    public String sessionId;
    public int eventId;
    public boolean success;

    @Override
    public String toString() {
        return "EventSetToken [sessionId=" + sessionId + ", eventId=" + eventId + ", success=" + success + "]";
    }

}

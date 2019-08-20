package com.kobobook.www.kobobook.util.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CustomLayout extends JsonLayout {
    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent iLoggingEvent) {
        long timestampMillis = iLoggingEvent.getTimeStamp();
        map.put("timestampSeconds", timestampMillis / 1000);
        map.put("timestampNanos", (timestampMillis % 1000) * 1000000);
        map.put("severity", String.valueOf(iLoggingEvent.getLevel()));
        map.put("original_message", iLoggingEvent.getMessage());
        map.remove("message");

        StringTokenizer st = new StringTokenizer(iLoggingEvent.getMessage(), ",");
        Map<String, String> json = new TreeMap<>();
        while (st.hasMoreTokens()) {
            String elmStr = st.nextToken();
            StringTokenizer elmSt = new StringTokenizer(elmStr, ":");
            String key = elmSt.nextToken();
            String value = elmSt.nextToken();
            json.put(key, value);
        }
        String msg;
        try {
            msg = new ObjectMapper().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        map.put("jsonpayload", json);
    }
}

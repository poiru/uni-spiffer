/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Serializable key-value store. Values are stored internally as strings, but
 * helper methods are provided for integers as well.
 * 
 * The serialized form resembles:
 *   key1=value1
 *   key2=value2
 * .. and so on.
 */
public class SettingManager {
    private final HashMap<String, String> mSettingMap = new HashMap<>();

    public SettingManager() {
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry setting : mSettingMap.entrySet()) {
            sb.append(setting.getKey());
            sb.append("=");
            sb.append(setting.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void deserialize(String str) {
        final String[] lines = str.split("\n");
        for (String line : lines) {
            final String[] lineParts = line.split("=");
            if (lineParts.length == 2) {
                mSettingMap.put(lineParts[0].toLowerCase(), lineParts[1]);
            }
        }
    }

    /**
     * Gets the value of a setting as a string.
     * 
     * @return String value corrosponding to |key|. If |key| does not exist,
     * returns |defaultValue| instead.
     */
    String getSetting(String key, String defaultValue) {
        final String value = mSettingMap.get(key.toLowerCase());
        return (value == null) ? defaultValue : value;
    }

    /**
     * Gets the value of a setting as a converted integer value.
     *
     * @return String value corrosponding to |key|. If |key| does not exist,
     * returns |defaultValue| instead.
     */
    int getSetting(String key, int defaultValue) {
        final String value = mSettingMap.get(key.toLowerCase());
        return (value == null) ? defaultValue : Integer.parseInt(value);
    }

    void setSetting(String key, String value) {
        mSettingMap.put(key.toLowerCase(), value);
    }

    void setSetting(String key, int value) {
        mSettingMap.put(key.toLowerCase(), Integer.toString(value));
    }
}

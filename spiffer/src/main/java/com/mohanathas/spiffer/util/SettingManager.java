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
     * @param key Name of the setting.
     * @param defaultValue Return value to use if the given key is not found.
     * @return String value for the given key.
     */
    public String getSetting(String key, String defaultValue) {
        final String value = mSettingMap.get(key.toLowerCase());
        return (value == null) ? defaultValue : value;
    }

    /**
     * Gets the value of a setting coverted to an integer.
     *
     * @param key Name of the setting.
     * @param defaultValue Return value to use if the given key is not found.
     * @return Integer converted value for the given key.
     */
    public int getSetting(String key, int defaultValue) {
        final String value = mSettingMap.get(key.toLowerCase());
        return (value == null) ? defaultValue : Integer.parseInt(value);
    }

    /**
     * Gets the value of a setting coverted to a boolean.
     *
     * @param key Name of the setting.
     * @param defaultValue Return value to use if the given key is not found.
     * @return Boolean converted value for the given key.
     */
    public boolean getSetting(String key, boolean defaultValue) {
        final String value = mSettingMap.get(key.toLowerCase());
        return (value == null) ? defaultValue : Boolean.parseBoolean(value);
    }

    public void setSetting(String key, String value) {
        mSettingMap.put(key.toLowerCase(), value);
    }

    public void setSetting(String key, int value) {
        mSettingMap.put(key.toLowerCase(), Integer.toString(value));
    }

    public void setSetting(String key, boolean value) {
        mSettingMap.put(key.toLowerCase(), Boolean.toString(value));
    }
}

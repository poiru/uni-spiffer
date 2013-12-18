/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the SettingManager class.
 */
public class SettingManagerTest {
    public SettingManagerTest() {
    }

    @Test
    public void testSerialize() {
        final SettingManager sm = new SettingManager();
        assertEquals("", sm.serialize());

        sm.setSetting("a", "1");
        assertEquals("a=1\n", sm.serialize());

        sm.setSetting("b", 2);
        final String str = sm.serialize();
        assertTrue(str.contains("a=1\n") && str.contains("b=2\n"));
    }

    @Test
    public void testDeserialize() {
        final SettingManager sm1 = new SettingManager();
        sm1.deserialize("");
        assertEquals("", sm1.serialize());

        sm1.deserialize("a=1\n");
        assertEquals("1", sm1.getSetting("a", ""));

        final SettingManager sm2 = new SettingManager();
        sm1.deserialize("a=1\nb=  2\n");
        assertEquals("1", sm1.getSetting("a", ""));
        assertEquals("  2", sm1.getSetting("b", ""));
    }

    @Test
    public void setAndGetSetting() {
        final SettingManager sm = new SettingManager();
        sm.setSetting("a", "1");
        sm.setSetting("b", 2);
        assertEquals("1", sm.getSetting("a", ""));
        assertEquals(2, sm.getSetting("b", 0));

        sm.setSetting("b", 3);
        assertEquals("3", sm.getSetting("b", ""));
    }

    @Test
    public void getSettingDefaultValue() {
        final SettingManager sm = new SettingManager();
        assertEquals("1", sm.getSetting("a", "1"));
        assertEquals(2, sm.getSetting("b", 2));
    }
}

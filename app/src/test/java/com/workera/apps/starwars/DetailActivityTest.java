package com.workera.apps.starwars;

import org.junit.Test;

import static org.junit.Assert.*;

public class DetailActivityTest {

    @Test
    public void convertTo2dec() {

        double value = 172.456677;
        value = value*100;
        value = Math.round(value);
        value = value/100;
        assertEquals(172.46, value, 0);

    }
}
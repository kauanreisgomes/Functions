package com.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FunctionsTest {
    @Test
    void testExecuteCommandLine() {

    }

    @Test
    void testJsonReader() {

    }

    @Test
    void testAddvalues() {

    }

    @Test
    void testCreateFileTemp() {

    }

    @Test
    void testFiltro() {

    }

    @Test
    void testFormat() {

    }

    @Test
    void testGetJSONfromweb() {
        Object[] p = {"http://localhost:8080/versoes/1",1};
        //assertEquals(true, Functions.getJSONfromweb(p).get(0));
        p[0] = "";
        assertEquals(new ArrayList<>(), Functions.getJSONfromweb(p));
    }

    @Test
    void testGetvalues() {

    }

    @Test
    void testIsCNPJ() {

    }

    @Test
    void testIsCPF() {

    }

    @Test
    void testIsNull() {

    }

    @Test
    void testIsNull2() {

    }

    @Test
    void testParse() {

    }

    @Test
    void testParseDate() {

    }

    @Test
    void testVerify() {

    }
}

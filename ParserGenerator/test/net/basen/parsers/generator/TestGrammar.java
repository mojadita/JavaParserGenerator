/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.generator;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class TestGrammar {
    
    Grammar<Symbols> iut;
    
    @Test
    public void testInitialProperties() {
        iut = new Grammar<Symbols>(Symbols.class);
        assertTrue(iut.getNonTerminals().isEmpty());
        for (Symbols s: Symbols.values())
            assertTrue(iut.getTerminals().contains( s ));
        assertEquals("", iut.toString());
    }

}
 /* TestGrammar */
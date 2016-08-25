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
    
    @SuppressWarnings( "unused" )
    @Test
    public void testSimpleGrammar() {
        iut = new Grammar<Symbols>(Symbols.class);
        Grammar<Symbols>.Rule r1 = iut.new Rule(Symbols.expr, Symbols.expr, Symbols.OR, Symbols.term),
                              r2 = iut.new Rule(Symbols.expr, Symbols.term),
                              r3 = iut.new Rule(Symbols.term, Symbols.term, Symbols.AND, Symbols.factor),
                              r4 = iut.new Rule(Symbols.term, Symbols.factor),
                              r5 = iut.new Rule(Symbols.factor, Symbols.NEGATION, Symbols.factor),
                              r6 = iut.new Rule(Symbols.factor, Symbols.LEFT_PAREN, Symbols.expr, Symbols.RIGHT_PAREN),
                              r7 = iut.new Rule(Symbols.factor, Symbols.IDENTIFIER);
        
        assertTrue( iut.getNonTerminals().contains( Symbols.expr ) );
        assertTrue( iut.getNonTerminals().contains( Symbols.term ) );
        assertTrue( iut.getNonTerminals().contains( Symbols.factor ) );
        assertFalse( iut.getNonTerminals().contains( Symbols.AND ) );
        assertFalse( iut.getNonTerminals().contains( Symbols.IDENTIFIER ) );
        assertFalse( iut.getNonTerminals().contains( Symbols.NEGATION ) );
        assertFalse( iut.getNonTerminals().contains( Symbols.LEFT_PAREN ) );
        assertFalse( iut.getNonTerminals().contains( Symbols.RIGHT_PAREN ) );
        
        assertFalse( iut.getTerminals().contains( Symbols.expr ) );
        assertFalse( iut.getTerminals().contains( Symbols.term ) );
        assertFalse( iut.getTerminals().contains( Symbols.factor ) );
        assertTrue( iut.getTerminals().contains( Symbols.AND ) );
        assertTrue( iut.getTerminals().contains( Symbols.IDENTIFIER ) );
        assertTrue( iut.getTerminals().contains( Symbols.NEGATION ) );
        assertTrue( iut.getTerminals().contains( Symbols.LEFT_PAREN ) );
        assertTrue( iut.getTerminals().contains( Symbols.RIGHT_PAREN ) );
        
        
    }

}
 /* TestGrammar */
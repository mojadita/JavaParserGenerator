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
import static net.basen.parsers.generator.Symbols.*;

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
        assertEquals("Grammar[\n"
            + "nonTerminals: {},\n"
            + "terminals: {}\n"
            + "]", iut.toString());
    }
    
    @Test
    public void testSimpleGrammar() {
        iut = new Grammar<Symbols>(Symbols.class);
        iut.add(iut.new Rule(expr, expr, OR, term));
        iut.add(iut.new Rule(expr, term));
        iut.add(iut.new Rule(term, term, AND, factor));
        iut.add(iut.new Rule(term, factor));
        iut.add(iut.new Rule(factor, NEGATION, factor));
        iut.add(iut.new Rule(factor, LEFT_PAREN, expr, RIGHT_PAREN));
        iut.add(iut.new Rule(factor, IDENTIFIER));

        assertTrue( iut.getNonTerminals().contains( expr ) );
        assertTrue( iut.getNonTerminals().contains( term ) );
        assertTrue( iut.getNonTerminals().contains( factor ) );
        assertFalse( iut.getNonTerminals().contains( AND ) );
        assertFalse( iut.getNonTerminals().contains( IDENTIFIER ) );
        assertFalse( iut.getNonTerminals().contains( NEGATION ) );
        assertFalse( iut.getNonTerminals().contains( LEFT_PAREN ) );
        assertFalse( iut.getNonTerminals().contains( RIGHT_PAREN ) );
        
        assertFalse( iut.getTerminals().contains( expr ) );
        assertFalse( iut.getTerminals().contains( term ) );
        assertFalse( iut.getTerminals().contains( factor ) );
        assertTrue( iut.getTerminals().contains( AND ) );
        assertTrue( iut.getTerminals().contains( IDENTIFIER ) );
        assertTrue( iut.getTerminals().contains( NEGATION ) );
        assertTrue( iut.getTerminals().contains( LEFT_PAREN ) );
        assertTrue( iut.getTerminals().contains( RIGHT_PAREN ) );
        assertEquals( "Grammar[\n" + 
                      "Rule[0]: expr ::= expr OR term;\n" +
                      "Rule[1]: expr ::= term;\n" +
                      "Rule[2]: term ::= term AND factor;\n" +
                      "Rule[3]: term ::= factor;\n" +
                      "Rule[4]: factor ::= NEGATION factor;\n" +
                      "Rule[5]: factor ::= LEFT_PAREN expr RIGHT_PAREN;\n" +
                      "Rule[6]: factor ::= IDENTIFIER;\n" +
                      "nonTerminals: {expr, term, factor},\n" +
                      "terminals: {NEGATION, AND, OR, LEFT_PAREN, RIGHT_PAREN, IDENTIFIER}\n" +
                      "]", iut.toString());
    }

}
 /* TestGrammar */
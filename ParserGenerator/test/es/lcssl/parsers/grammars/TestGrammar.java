/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package es.lcssl.parsers.grammars;

import static es.lcssl.parsers.grammars.Symbols.AND;
import static es.lcssl.parsers.grammars.Symbols.IDENTIFIER;
import static es.lcssl.parsers.grammars.Symbols.LEFT_PAREN;
import static es.lcssl.parsers.grammars.Symbols.NEGATION;
import static es.lcssl.parsers.grammars.Symbols.OR;
import static es.lcssl.parsers.grammars.Symbols.RIGHT_PAREN;
import static es.lcssl.parsers.grammars.Symbols.expr;
import static es.lcssl.parsers.grammars.Symbols.factor;
import static es.lcssl.parsers.grammars.Symbols.term;
import static es.lcssl.parsers.grammars.Symbols.unused;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIn.in;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import es.lcssl.parsers.grammars.Grammar;

/**
 * Test class for {@link Grammar} class.
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class TestGrammar {

    Grammar<Symbols> iut;

    Grammar<Symbols>.Rule r0, r1, r2, r3, r4, r5, r6;

    @Before
    public void before() {
        iut = new Grammar<Symbols>( Symbols.class );
        iut.add( r0 = iut.new Rule( expr, expr, OR, term ) );
        iut.add( r1 = iut.new Rule( expr, term ) );
        iut.add( r2 = iut.new Rule( term, term, AND, factor ) );
        iut.add( r3 = iut.new Rule( term, factor ) );
        iut.add( r4 = iut.new Rule( factor, NEGATION, factor ) );
        iut.add( r5 = iut.new Rule( factor, LEFT_PAREN, expr, RIGHT_PAREN ) );
        iut.add( r6 = iut.new Rule( factor, IDENTIFIER ) );
    }

    @Test
    public void testInitialProperties() {
        iut = new Grammar<Symbols>( Symbols.class );
        assertTrue( iut.getNonTerminals().isEmpty() );
        assertEquals( "Grammar[\n" + "]", iut.toString() );
    }

    @Test
    public void testSimpleGrammar() {

        assertThat( expr, is( in( iut.getNonTerminals() ) ) );
        assertThat( term, is( in( iut.getNonTerminals() ) ) );
        assertThat( factor, is( in( iut.getNonTerminals() ) ) );
        assertThat( AND, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( IDENTIFIER, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( NEGATION, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( LEFT_PAREN, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( RIGHT_PAREN, is( not( in( iut.getNonTerminals() ) ) ) );

        assertThat( expr, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( term, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( factor, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( AND, is( in( iut.getTerminals() ) ) );
        assertThat( IDENTIFIER, is( in( iut.getTerminals() ) ) );
        assertThat( NEGATION, is( in( iut.getTerminals() ) ) );
        assertThat( LEFT_PAREN, is( in( iut.getTerminals() ) ) );
        assertThat( RIGHT_PAREN, is( in( iut.getTerminals() ) ) );
        assertThat( iut.toString(),
                    is( equalTo( "Grammar[\n"
                    	+ "Rules:\n"
                        + "\tR[0]: <expr> ::= <expr> OR <term>;\n"
                        + "\tR[1]: <expr> ::= <term>;\n"
                        + "\tR[2]: <term> ::= <term> AND <factor>;\n"
                        + "\tR[3]: <term> ::= <factor>;\n"
                        + "\tR[4]: <factor> ::= NEGATION <factor>;\n"
                        + "\tR[5]: <factor> ::= LEFT_PAREN <expr> RIGHT_PAREN;\n"
                        + "\tR[6]: <factor> ::= IDENTIFIER;\n"
                        + "Nonterminals: {expr, term, factor}\n"
                        + "Terminals: {NEGATION, AND, OR, LEFT_PAREN, RIGHT_PAREN, IDENTIFIER}\n"
                        + "]" ) ) );
        assertThat( r0, is( in( iut.getRuleSet( expr ) ) ) );
        assertThat( r1, is( in( iut.getRuleSet( expr ) ) ) );
        assertThat( r2, is( in( iut.getRuleSet( term ) ) ) );
        assertThat( r3, is( in( iut.getRuleSet( term ) ) ) );
        assertThat( r4, is( in( iut.getRuleSet( factor ) ) ) );
        assertThat( r5, is( in( iut.getRuleSet( factor ) ) ) );
        assertThat( r6, is( in( iut.getRuleSet( factor ) ) ) );
        assertThat( iut.getRuleSet( AND ), is( empty() ) );
        assertThat( iut.getRuleSet( IDENTIFIER ), is( empty() ) );
        assertThat( iut.getRuleSet( NEGATION ), is( empty() ) );
        assertThat( iut.getRuleSet( LEFT_PAREN ), is( empty() ) );
        assertThat( iut.getRuleSet( RIGHT_PAREN ), is( empty() ) );
    }

    @Test
    public void testModifyGrammar() {
        assertThat( expr, is( in( iut.getNonTerminals() ) ) );
        assertThat( term, is( in( iut.getNonTerminals() ) ) );
        assertThat( factor, is( in( iut.getNonTerminals() ) ) );
        assertThat( AND, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( IDENTIFIER, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( NEGATION, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( LEFT_PAREN, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( RIGHT_PAREN, is( not( in( iut.getNonTerminals() ) ) ) );

        assertThat( expr, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( term, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( factor, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( AND, is( in( iut.getTerminals() ) ) );
        assertThat( IDENTIFIER, is( in( iut.getTerminals() ) ) );
        assertThat( NEGATION, is( in( iut.getTerminals() ) ) );
        assertThat( LEFT_PAREN, is( in( iut.getTerminals() ) ) );
        assertThat( RIGHT_PAREN, is( in( iut.getTerminals() ) ) );
        assertThat( iut.toString(),
                    is( equalTo( "Grammar[\n"
                    	+ "Rules:\n"
                        + "\tR[0]: <expr> ::= <expr> OR <term>;\n"
                        + "\tR[1]: <expr> ::= <term>;\n"
                        + "\tR[2]: <term> ::= <term> AND <factor>;\n"
                        + "\tR[3]: <term> ::= <factor>;\n"
                        + "\tR[4]: <factor> ::= NEGATION <factor>;\n"
                        + "\tR[5]: <factor> ::= LEFT_PAREN <expr> RIGHT_PAREN;\n"
                        + "\tR[6]: <factor> ::= IDENTIFIER;\n"
                        + "Nonterminals: {expr, term, factor}\n"
                        + "Terminals: {NEGATION, AND, OR, LEFT_PAREN, RIGHT_PAREN, IDENTIFIER}\n"
                        + "]" ) ) );
        assertThat( r0, is( in( iut.getRuleSet( expr ) ) ) );
        assertThat( r1, is( in( iut.getRuleSet( expr ) ) ) );
        assertThat( r2, is( in( iut.getRuleSet( term ) ) ) );
        assertThat( r3, is( in( iut.getRuleSet( term ) ) ) );
        assertThat( r4, is( in( iut.getRuleSet( factor ) ) ) );
        assertThat( r5, is( in( iut.getRuleSet( factor ) ) ) );
        assertThat( r6, is( in( iut.getRuleSet( factor ) ) ) );
        assertThat( iut.getRuleSet( AND ), is( empty() ) );
        assertThat( iut.getRuleSet( IDENTIFIER ), is( empty() ) );
        assertThat( iut.getRuleSet( NEGATION ), is( empty() ) );
        assertThat( iut.getRuleSet( LEFT_PAREN ), is( empty() ) );
        assertThat( iut.getRuleSet( RIGHT_PAREN ), is( empty() ) );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfNonTerminals() {
        iut.getNonTerminals().add( unused );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfTerminals() {
        iut.getTerminals().add( unused );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfRuleSetWithUnusedSymbol() {
        iut.getRuleSet( unused ).add( r4 );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfRuleSetWithNonTerminal() {
        assertTrue( iut.getNonTerminals().contains( expr ) );
        iut.getRuleSet( expr ).add( r4 );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfRuleSetWithTerminal() {
        assertTrue( iut.getTerminals().contains( AND ) );
        iut.getRuleSet( AND ).add( r4 );
    }

    @Test
    public void testSymbolClass() {
        assertSame( Symbols.class, iut.getSymbolClass() );
    }

} /* TestGrammar */
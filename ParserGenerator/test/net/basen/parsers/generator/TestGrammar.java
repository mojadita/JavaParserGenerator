/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.generator;

import static net.basen.parsers.generator.Symbols.AND;
import static net.basen.parsers.generator.Symbols.IDENTIFIER;
import static net.basen.parsers.generator.Symbols.LEFT_PAREN;
import static net.basen.parsers.generator.Symbols.NEGATION;
import static net.basen.parsers.generator.Symbols.OR;
import static net.basen.parsers.generator.Symbols.RIGHT_PAREN;
import static net.basen.parsers.generator.Symbols.expr;
import static net.basen.parsers.generator.Symbols.factor;
import static net.basen.parsers.generator.Symbols.term;
import static net.basen.parsers.generator.Symbols.unused;
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

/**
 * 
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
        assertEquals( "Grammar[\n" + "nonTerminals: {},\n" + "terminals: {}\n"
            + "]", iut.toString() );
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
                        + "Rule[0]: expr ::= expr OR term;\n"
                        + "Rule[1]: expr ::= term;\n"
                        + "Rule[2]: term ::= term AND factor;\n"
                        + "Rule[3]: term ::= factor;\n"
                        + "Rule[4]: factor ::= NEGATION factor;\n"
                        + "Rule[5]: factor ::= LEFT_PAREN expr RIGHT_PAREN;\n"
                        + "Rule[6]: factor ::= IDENTIFIER;\n"
                        + "nonTerminals: {expr, term, factor},\n"
                        + "terminals: {NEGATION, AND, OR, LEFT_PAREN, RIGHT_PAREN, IDENTIFIER}\n"
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
                        + "Rule[0]: expr ::= expr OR term;\n"
                        + "Rule[1]: expr ::= term;\n"
                        + "Rule[2]: term ::= term AND factor;\n"
                        + "Rule[3]: term ::= factor;\n"
                        + "Rule[4]: factor ::= NEGATION factor;\n"
                        + "Rule[5]: factor ::= LEFT_PAREN expr RIGHT_PAREN;\n"
                        + "Rule[6]: factor ::= IDENTIFIER;\n"
                        + "nonTerminals: {expr, term, factor},\n"
                        + "terminals: {NEGATION, AND, OR, LEFT_PAREN, RIGHT_PAREN, IDENTIFIER}\n"
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
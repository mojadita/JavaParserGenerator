/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package es.lcssl.parsers.grammars;

import static es.lcssl.parsers.grammars.Symbols.EXPRESSION;
import static es.lcssl.parsers.grammars.Symbols.FACTOR;
import static es.lcssl.parsers.grammars.Symbols.IDENT;
import static es.lcssl.parsers.grammars.Symbols.LEFT_PAREN;
import static es.lcssl.parsers.grammars.Symbols.MINUS;
import static es.lcssl.parsers.grammars.Symbols.MULT;
import static es.lcssl.parsers.grammars.Symbols.PLUS;
import static es.lcssl.parsers.grammars.Symbols.RIGHT_PAREN;
import static es.lcssl.parsers.grammars.Symbols.TERM;
import static es.lcssl.parsers.grammars.Symbols.UNUSED;
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
 * Test class for {@link Grammar} class.
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class TestGrammar {

    Grammar<Symbols> iut;

    Grammar<Symbols>.Rule r0, r1, r2, r3, r4, r5, r6;

    @Before
    public void before() {
        iut = new Grammar<Symbols>( Symbols.class );
        iut.add( r0 = iut.new Rule( EXPRESSION, EXPRESSION, PLUS, TERM ) );
        iut.add( r1 = iut.new Rule( EXPRESSION, TERM ) );
        iut.add( r2 = iut.new Rule( TERM, TERM, MULT, FACTOR ) );
        iut.add( r3 = iut.new Rule( TERM, FACTOR ) );
        iut.add( r4 = iut.new Rule( FACTOR, MINUS, FACTOR ) );
        iut.add( r5 = iut.new Rule( FACTOR, LEFT_PAREN, EXPRESSION, RIGHT_PAREN ) );
        iut.add( r6 = iut.new Rule( FACTOR, IDENT ) );
    }

    @Test
    public void testInitialProperties() {
        iut = new Grammar<Symbols>( Symbols.class );
        assertTrue( iut.getNonTerminals().isEmpty() );
        assertEquals( "Grammar[\n" + "]", iut.toString() );
    }

    @Test
    public void testSimpleGrammar() {

        assertThat( EXPRESSION, in( iut.getNonTerminals() ) );
        assertThat( TERM, in( iut.getNonTerminals() ) );
        assertThat( FACTOR, in( iut.getNonTerminals() ) );
        assertThat( MULT, not( in( iut.getNonTerminals() ) ) );
        assertThat( IDENT, not( in( iut.getNonTerminals() ) ) );
        assertThat( MINUS, not( in( iut.getNonTerminals() ) ) );
        assertThat( LEFT_PAREN, not( in( iut.getNonTerminals() ) ) );
        assertThat( RIGHT_PAREN, not( in( iut.getNonTerminals() ) ) );

        assertThat( EXPRESSION, not( in( iut.getTerminals() ) ) );
        assertThat( TERM, not( in( iut.getTerminals() ) ) );
        assertThat( FACTOR, not( in( iut.getTerminals() ) ) );
        assertThat( MULT, in( iut.getTerminals() ) );
        assertThat( IDENT, in( iut.getTerminals() ) );
        assertThat( MINUS, in( iut.getTerminals() ) );
        assertThat( LEFT_PAREN, in( iut.getTerminals() ) );
        assertThat( RIGHT_PAREN, in( iut.getTerminals() ) );
        assertThat( iut.toString(),
                    equalTo( "Grammar[\n"
                    	+ "Rules:\n"
                        + "\tR[0]: <EXPRESSION> ::= <EXPRESSION> PLUS <TERM>;\n"
                        + "\tR[1]: <EXPRESSION> ::= <TERM>;\n"
                        + "\tR[2]: <TERM> ::= <TERM> MULT <FACTOR>;\n"
                        + "\tR[3]: <TERM> ::= <FACTOR>;\n"
                        + "\tR[4]: <FACTOR> ::= MINUS <FACTOR>;\n"
                        + "\tR[5]: <FACTOR> ::= LEFT_PAREN <EXPRESSION> RIGHT_PAREN;\n"
                        + "\tR[6]: <FACTOR> ::= IDENT;\n"
                        + "Nonterminals: {EXPRESSION, TERM, FACTOR}\n"
                        + "Terminals: {PLUS, MINUS, MULT, LEFT_PAREN, RIGHT_PAREN, IDENT}\n"
                        + "]" ) );
        assertThat( r0, in( iut.getRules( EXPRESSION ) ) );
        assertThat( r1, in( iut.getRules( EXPRESSION ) ) );
        assertThat( r2, in( iut.getRules( TERM ) ) );
        assertThat( r3, in( iut.getRules( TERM ) ) );
        assertThat( r4, in( iut.getRules( FACTOR ) ) );
        assertThat( r5, in( iut.getRules( FACTOR ) ) );
        assertThat( r6, in( iut.getRules( FACTOR ) ) );
        assertThat( iut.getRules( MULT ), empty() );
        assertThat( iut.getRules( IDENT ), empty() );
        assertThat( iut.getRules( MINUS ), empty() );
        assertThat( iut.getRules( LEFT_PAREN ), empty() );
        assertThat( iut.getRules( RIGHT_PAREN ), empty() );
    }

    @Test
    public void testModifyGrammar() {
        assertThat( EXPRESSION, is( in( iut.getNonTerminals() ) ) );
        assertThat( TERM, is( in( iut.getNonTerminals() ) ) );
        assertThat( FACTOR, is( in( iut.getNonTerminals() ) ) );
        assertThat( MULT, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( IDENT, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( MINUS, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( LEFT_PAREN, is( not( in( iut.getNonTerminals() ) ) ) );
        assertThat( RIGHT_PAREN, is( not( in( iut.getNonTerminals() ) ) ) );

        assertThat( EXPRESSION, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( TERM, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( FACTOR, is( not( in( iut.getTerminals() ) ) ) );
        assertThat( MULT, is( in( iut.getTerminals() ) ) );
        assertThat( IDENT, is( in( iut.getTerminals() ) ) );
        assertThat( MINUS, is( in( iut.getTerminals() ) ) );
        assertThat( LEFT_PAREN, is( in( iut.getTerminals() ) ) );
        assertThat( RIGHT_PAREN, is( in( iut.getTerminals() ) ) );
        assertThat( iut.toString(),
                    is( equalTo( "Grammar[\n"
                    	+ "Rules:\n"
                        + "\tR[0]: <EXPRESSION> ::= <EXPRESSION> PLUS <TERM>;\n"
                        + "\tR[1]: <EXPRESSION> ::= <TERM>;\n"
                        + "\tR[2]: <TERM> ::= <TERM> MULT <FACTOR>;\n"
                        + "\tR[3]: <TERM> ::= <FACTOR>;\n"
                        + "\tR[4]: <FACTOR> ::= MINUS <FACTOR>;\n"
                        + "\tR[5]: <FACTOR> ::= LEFT_PAREN <EXPRESSION> RIGHT_PAREN;\n"
                        + "\tR[6]: <FACTOR> ::= IDENT;\n"
                        + "Nonterminals: {EXPRESSION, TERM, FACTOR}\n"
                        + "Terminals: {PLUS, MINUS, MULT, LEFT_PAREN, RIGHT_PAREN, IDENT}\n"
                        + "]" ) ) );
        assertThat( r0, is( in( iut.getRules( EXPRESSION ) ) ) );
        assertThat( r1, is( in( iut.getRules( EXPRESSION ) ) ) );
        assertThat( r2, is( in( iut.getRules( TERM ) ) ) );
        assertThat( r3, is( in( iut.getRules( TERM ) ) ) );
        assertThat( r4, is( in( iut.getRules( FACTOR ) ) ) );
        assertThat( r5, is( in( iut.getRules( FACTOR ) ) ) );
        assertThat( r6, is( in( iut.getRules( FACTOR ) ) ) );
        assertThat( iut.getRules( MULT ), is( empty() ) );
        assertThat( iut.getRules( IDENT ), is( empty() ) );
        assertThat( iut.getRules( MINUS ), is( empty() ) );
        assertThat( iut.getRules( LEFT_PAREN ), is( empty() ) );
        assertThat( iut.getRules( RIGHT_PAREN ), is( empty() ) );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfNonTerminals() {
        iut.getNonTerminals().add( UNUSED );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfTerminals() {
        iut.getTerminals().add( UNUSED );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfRuleSetWithUnusedSymbol() {
        iut.getRules( UNUSED ).add( r4 );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfRuleSetWithNonTerminal() {
        assertTrue( iut.getNonTerminals().contains( EXPRESSION ) );
        iut.getRules( EXPRESSION ).add( r4 );
    }

    @Test( expected = UnsupportedOperationException.class )
    public void testImmutabilityOfRuleSetWithTerminal() {
        assertTrue( iut.getTerminals().contains( MULT ) );
        iut.getRules( MULT ).add( r4 );
    }

    @Test
    public void testSymbolClass() {
        assertSame( Symbols.class, iut.getSymbolClass() );
    }

} /* TestGrammar */
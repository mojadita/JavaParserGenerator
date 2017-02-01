/*
 * $Id$ Author: Luis Colorado <lcu@basen.net> Date: Aug 25, 2016 12:58:49 PM.
 * Project: ParserGenerator Package: net.basen.parsers.generator Disclaimer: (C)
 * 2016 LUIS COLORADO. All rights reserved.
 */

package net.basen.parsers.parser;

import static net.basen.parsers.parser.Symbols.AND;
import static net.basen.parsers.parser.Symbols.IDENTIFIER;
import static net.basen.parsers.parser.Symbols.LEFT_PAREN;
import static net.basen.parsers.parser.Symbols.OR;
import static net.basen.parsers.parser.Symbols.RIGHT_PAREN;
import static net.basen.parsers.parser.Symbols.expr;
import static net.basen.parsers.parser.Symbols.term;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import net.basen.parsers.generator.Grammar;

/**
 * @author {@code <>}.
 *
 */
public class TestMarkedRule {

    RuleMarks<Symbols> iut;

    Grammar<Symbols> grammar;

    Grammar<Symbols>.Rule r0, r1;

    RuleMarks<Symbols>[] array;

    @SuppressWarnings( "unchecked" )
    @Before
    public void before() {
        grammar = new Grammar<Symbols>( Symbols.class );
        grammar.add( r0 = grammar.new Rule( expr, expr, OR, term ) );
        grammar.add( r1 = grammar.new Rule( expr, term ) );

        array =
            new RuleMarks[] { new RuleMarks<Symbols>( r0 ).addMarks(),
                new RuleMarks<Symbols>( r0 ).addMarks( 0 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 0, 1 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 0, 1, 2 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 0, 1, 2, 3 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 0, 2 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 0, 2, 3 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 0, 3 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 1 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 1, 2 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 1, 2, 3 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 1, 3 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 2 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 2, 3 ),
                new RuleMarks<Symbols>( r0 ).addMarks( 3 ), };
    }

    @Test
    public void testSingleMark() {
        iut = new RuleMarks<Symbols>( r0 );
        assertNotNull( iut );
        iut.addMarks( 0 );
        assertEquals( "expr ::= . expr OR term;", iut.toString() );
    }

    @Test
    public void testFinalMark() {
        iut = new RuleMarks<Symbols>( r0 );
        iut.addMarks( r0.size() );
        assertEquals( "expr ::= expr OR term . ;", iut.toString() );
    }

    @Test
    public void testMultipleMarks() {
        iut = new RuleMarks<Symbols>( r0 );
        assertNotNull( iut );
        iut.addMarks( 0, 2 );
        assertEquals( "expr ::= . expr OR . term;", iut.toString() );
    }

    @Test
    public void testMultipleRuleMarksSameRule() {
        RuleMarks<Symbols> rm0 = new RuleMarks<Symbols>( r0 ).addMarks( 0, 1 ), rm1 =
            new RuleMarks<Symbols>( r0 ).addMarks( 0, 2 );
        assertEquals( "expr ::= . expr . OR term;", rm0.toString() );
        assertEquals( "expr ::= . expr OR . term;", rm1.toString() );
    }

    @Test( expected = IndexOutOfBoundsException.class )
    public void testArrayOutOfBoundsUnder() {
        new RuleMarks<Symbols>( r0 ).addMarks( 0, -1, 1 );
    }

    @Test( expected = IndexOutOfBoundsException.class )
    public void testArrayOutOfBoundsAbove() {
        new RuleMarks<Symbols>( r0 ).addMarks( 0, 10, 1 );
    }

    @Test
    public void testMarkOrderIndependence() {
        RuleMarks<Symbols> rm0 =
            new RuleMarks<Symbols>( grammar.new Rule( expr,
                                                      IDENTIFIER,
                                                      LEFT_PAREN,
                                                      expr,
                                                      RIGHT_PAREN,
                                                      AND,
                                                      LEFT_PAREN,
                                                      expr,
                                                      RIGHT_PAREN ) ).addMarks( 3,
                                                                                2,
                                                                                5 );
        assertEquals( "expr ::= IDENTIFIER LEFT_PAREN . expr . RIGHT_PAREN AND . "
                          + "LEFT_PAREN expr RIGHT_PAREN;",
                      rm0.toString() );
    }

    @Test
    public void testMarkOrder() {
        for( int i = 0; i < array.length; i++ ) {
            for( int j = 0; j < array.length; j++ ) {
                if( i < j ) {
                    assertTrue( "array[" + i + "] < array[" + j + "] failed",
                                array[i].compareTo( array[j] ) < 0 );
                } else if( i > j ) {
                    assertTrue( "array[" + i + "] > array[" + j + "] failed",
                                array[i].compareTo( array[j] ) > 0 );
                } else { /* i == j */
                    assertTrue( "array[" + i + "] == array[" + j + "] failed",
                                array[i].compareTo( array[j] ) == 0 );
                }
            }
        }
    }

    @Test
    public void testEquals() {
        for( int i = 0; i < array.length; i++ ) {
            for( int j = i; j < array.length; j++ ) {
                String label = "array[" + i + "].equals(array[" + j + "])";
                if( i == j ) {
                    assertTrue( label, array[i].equals( array[j] ) );
                } else {
                    assertFalse( label, array[i].equals( array[j] ) );
                }
            }
        }
    }

    @Test( expected = IllegalArgumentException.class )
    public void testMarkOrderWithDifferentRules() {
        RuleMarks<Symbols> rm0 = new RuleMarks<Symbols>( r0 ).addMarks( 0 ), rm1 =
            new RuleMarks<Symbols>( r1 ).addMarks( 0 );
        assertTrue( rm0.compareTo( rm1 ) == 0 ); // should throw the exception.
    }

} /* TestMarkedRule */
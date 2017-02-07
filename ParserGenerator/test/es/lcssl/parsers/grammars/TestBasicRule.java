/* $Id$
 * Author: Luis Colorado <lcu@basen.net>
 * Date: Aug 25, 2016 12:58:49 PM.
 * Project: ParserGenerator
 * Package: net.basen.parsers.generator
 * Disclaimer: (C) 2016 LUIS COLORADO.  All rights reserved.
 */

package es.lcssl.parsers.grammars;

import static org.junit.Assert.*;
import static org.hamcrest.number.OrderingComparison.*;
import static org.hamcrest.CoreMatchers.*;
import static es.lcssl.parsers.grammars.Symbols.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import es.lcssl.parsers.grammars.BasicRule;

/**
 * @author  {@code <>}.
 *
 */
public class TestBasicRule {
	
	BasicRule<Symbols> iut;
	
	@Test(expected=NullPointerException.class)
	public void testNoLhs() {
		iut = new BasicRule<Symbols>(null);
		assertNotNull(iut);
		assertEquals("R0: null ::= /* empty */;", iut.toString()); // throws the exception.
	}
	
	@Test
	public void testPopulate1() {
		iut = new BasicRule<Symbols>(EXPRESSION);
		iut.add(EXPRESSION);
		iut.add(PLUS);
		iut.add(TERM);
		assertEquals("EXPRESSION ::= EXPRESSION PLUS TERM;", iut.toString());
	}
	
	@Test
	public void testPopulate2() {
		iut = new BasicRule<Symbols>(TERM, TERM, MULT, FACTOR);
		assertEquals("TERM ::= TERM MULT FACTOR;", iut.toString());
	}
	
	@Test
	public void testPopulate3() {
		iut = new BasicRule<Symbols>(FACTOR, new ArrayList<Symbols>(
				Arrays.asList(LEFT_PAREN, EXPRESSION, RIGHT_PAREN)));
		assertEquals("FACTOR ::= LEFT_PAREN EXPRESSION RIGHT_PAREN;", iut.toString());
	}
	
	@Test
	public void testCreateSeveralRules() {
		BasicRule<Symbols> R0 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, TERM);
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, TERM);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(TERM, TERM, MULT, FACTOR);
		BasicRule<Symbols> R3 = new BasicRule<Symbols>(TERM, FACTOR);
		BasicRule<Symbols> R4 = new BasicRule<Symbols>(FACTOR, MINUS, FACTOR);
		BasicRule<Symbols> R5 = new BasicRule<Symbols>(FACTOR, LEFT_PAREN, EXPRESSION, RIGHT_PAREN);
		BasicRule<Symbols> R6 = new BasicRule<Symbols>(FACTOR, IDENT);
		assertEquals("EXPRESSION ::= EXPRESSION PLUS TERM;", R0.toString());
		assertEquals("EXPRESSION ::= TERM;", R1.toString());
		assertEquals("TERM ::= TERM MULT FACTOR;", R2.toString());
		assertEquals("TERM ::= FACTOR;", R3.toString());
		assertEquals("FACTOR ::= MINUS FACTOR;", R4.toString());
		assertEquals("FACTOR ::= LEFT_PAREN EXPRESSION RIGHT_PAREN;", R5.toString());
		assertEquals("FACTOR ::= IDENT;", R6.toString());
	}
	
	@Test
	public void testComparisonsEqualTo() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, TERM);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, TERM);
		assertThat(R1, equalTo(R2));
		assertNotSame(R1, R2);
	}
	
	@Test
	public void testComparisonsLessByLHS() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, TERM);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(TERM, EXPRESSION, PLUS, TERM);
		assertThat(R1, is(lessThan(R2)));
	}
	
	@Test
	public void testComparisonsLessByRHSSameLength() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, TERM);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, MULT, TERM);
		assertThat(R1, is(lessThan(R2)));
	}
	
	@Test
	public void testComparisonsLessByRHSSameLengthLastSymbol() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, EXPRESSION);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, PLUS, TERM);
		assertThat(R1, is(lessThan(R2)));
	}
	
	@Test
	public void testComparisonsLessByRHSDifferentLength() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION);
        BasicRule<Symbols> R2 = new BasicRule<Symbols>(EXPRESSION, EXPRESSION, MULT, EXPRESSION);
		assertThat(R1, is(lessThan(R2)));
	}
	
	@Test
	public void testComparisonsLessByRHSDifferentLength2() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(EXPRESSION, TERM, MULT, EXPRESSION);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(EXPRESSION, TERM);
		assertThat(R1, is(greaterThan(R2)));
	}
}
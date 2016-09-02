/* $Id$
 * Author: Luis Colorado <lcu@basen.net>
 * Date: Aug 25, 2016 12:58:49 PM.
 * Project: ParserGenerator
 * Package: net.basen.parsers.generator
 * Disclaimer: (C) 2016 LUIS COLORADO.  All rights reserved.
 */

package net.basen.parsers.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static net.basen.parsers.generator.Symbols.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author  {@code <>}.
 *
 */
public class TestRule {
	
	BasicRule<Symbols> iut;
	
	@Test
	public void testEmptyConstructor() {
		iut = new BasicRule<Symbols>();
		assertNotNull(iut);
		iut.setLhs(expr);
		assertEquals("expr: /* empty */;", iut.toString());
	}
	
	@Test
	public void testNoLhs() {
		iut = new BasicRule<Symbols>();
		assertNotNull(iut);
		assertEquals("null: /* empty */;", iut.toString());
	}
	
	@Test
	public void testPopulate1() {
		iut = new BasicRule<Symbols>();
		iut.setLhs(expr);
		iut.add(expr);
		iut.add(OR);
		iut.add(term);
		assertEquals("expr: expr OR term;", iut.toString());
	}
	
	@Test
	public void testPopulate2() {
		iut = new BasicRule<Symbols>(term, term, AND, factor);
		assertEquals("term: term AND factor;", iut.toString());
	}
	
	@Test
	public void testPopulate3() {
		iut = new BasicRule<Symbols>(factor, new ArrayList<Symbols>(
				Arrays.asList(LEFT_PAREN, expr, RIGHT_PAREN)));
		assertEquals("factor: LEFT_PAREN expr RIGHT_PAREN;", iut.toString());
	}
	
	@Test
	public void testCreateSeveralRules() {
		BasicRule<Symbols> R1 = new BasicRule<Symbols>(expr, expr, OR, term);
		BasicRule<Symbols> R2 = new BasicRule<Symbols>(expr, term);
		BasicRule<Symbols> R3 = new BasicRule<Symbols>(term, term, AND, factor);
		BasicRule<Symbols> R4 = new BasicRule<Symbols>(term, factor);
		BasicRule<Symbols> R5 = new BasicRule<Symbols>(factor, NEGATION, factor);
		BasicRule<Symbols> R6 = new BasicRule<Symbols>(factor, LEFT_PAREN, expr, RIGHT_PAREN);
		BasicRule<Symbols> R7 = new BasicRule<Symbols>(factor, IDENTIFIER);
		assertEquals("expr: expr OR term;", R1.toString());
		assertEquals("expr: term;", R2.toString());
		assertEquals("term: term AND factor;", R3.toString());
		assertEquals("term: factor;", R4.toString());
		assertEquals("factor: NEGATION factor;", R5.toString());
		assertEquals("factor: LEFT_PAREN expr RIGHT_PAREN;", R6.toString());
		assertEquals("factor: IDENTIFIER;", R7.toString());
	}
}
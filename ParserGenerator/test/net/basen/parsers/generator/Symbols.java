/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.generator;


enum Symbols implements Symbol<Symbols> {
	expr, term, factor, 
	NEGATION, AND, OR, LEFT_PAREN, RIGHT_PAREN, IDENTIFIER,
} /* Symbols */
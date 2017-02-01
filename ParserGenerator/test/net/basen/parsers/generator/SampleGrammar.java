/**
 * $Id: $
 *
 * Copyright (C) 2017 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.generator;

import static net.basen.parsers.generator.SampleGrammar.Symbols.COMMA;
import static net.basen.parsers.generator.SampleGrammar.Symbols.EOF;
import static net.basen.parsers.generator.SampleGrammar.Symbols.IDENT;
import static net.basen.parsers.generator.SampleGrammar.Symbols.LEFT_PAREN;
import static net.basen.parsers.generator.SampleGrammar.Symbols.RIGHT_PAREN;
import static net.basen.parsers.generator.SampleGrammar.Symbols.expr;
import static net.basen.parsers.generator.SampleGrammar.Symbols.list;
import static net.basen.parsers.generator.SampleGrammar.Symbols.start;

/**
 * Sample grammar for the compilation tests.
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class SampleGrammar {

    enum Symbols {
        start,
        expr,
        list,
        IDENT,
        LEFT_PAREN,
        RIGHT_PAREN,
        COMMA,
        EOF,
    }

    public static Grammar<Symbols> g = new Grammar<Symbols>( Symbols.class );

    public static Grammar<Symbols>.Rule 
            r0 = g.new Rule( start, expr ),
            r1 = g.new Rule( expr, IDENT ), 
            r2 = g.new Rule( expr, IDENT, LEFT_PAREN, expr, RIGHT_PAREN ),
            r3 = g.new Rule( list, expr ),
            r4 = g.new Rule( list, list, COMMA, expr );

    /**
     * main program.
     * 
     * @param args unused.
     */
    public static void main( String[] args ) {
        System.out.println(g);
    }

} /* SampleGrammar */
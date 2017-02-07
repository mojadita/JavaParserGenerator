/**
 * $Id: $
 *
 * Copyright (C) 2017 BaseN.
 *
 * All rights reserved.
 */
package es.lcssl.parsers.grammars;

import static es.lcssl.parsers.grammars.Symbols.COMMA;
import static es.lcssl.parsers.grammars.Symbols.DIV;
import static es.lcssl.parsers.grammars.Symbols.EXPRESSION;
import static es.lcssl.parsers.grammars.Symbols.FACTOR;
import static es.lcssl.parsers.grammars.Symbols.IDENT;
import static es.lcssl.parsers.grammars.Symbols.LEFT_PAREN;
import static es.lcssl.parsers.grammars.Symbols.LIST;
import static es.lcssl.parsers.grammars.Symbols.MINUS;
import static es.lcssl.parsers.grammars.Symbols.MUL;
import static es.lcssl.parsers.grammars.Symbols.MULT;
import static es.lcssl.parsers.grammars.Symbols.NUM;
import static es.lcssl.parsers.grammars.Symbols.PLUS;
import static es.lcssl.parsers.grammars.Symbols.RIGHT_PAREN;
import static es.lcssl.parsers.grammars.Symbols.START;
import static es.lcssl.parsers.grammars.Symbols.SUM;
import static es.lcssl.parsers.grammars.Symbols.TERM;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Sample grammar for the compilation tests.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class SampleGrammar {


    public static Grammar<Symbols> g = new Grammar<Symbols>( Symbols.class );

    public static Grammar<Symbols>.Rule 
        r0 = g.new Rule( START,             EXPRESSION ),
        r1 = g.new Rule( EXPRESSION,        EXPRESSION, SUM, TERM ),
        r2 = g.new Rule( EXPRESSION,        TERM ),
        r3 = g.new Rule( EXPRESSION,        SUM, TERM ),
        r4 = g.new Rule( SUM,               PLUS ),
        r5 = g.new Rule( SUM,               MINUS ),
        r6 = g.new Rule( TERM,              TERM, MUL, FACTOR ),
        r7 = g.new Rule( TERM,              FACTOR ),
        r8 = g.new Rule( MUL,               MULT ),
        r9 = g.new Rule( MUL,               DIV ),
        r10 = g.new Rule( FACTOR,           LEFT_PAREN, EXPRESSION, RIGHT_PAREN ),
        r11 = g.new Rule( FACTOR,           NUM ),
        r12 = g.new Rule( FACTOR,           IDENT ),
        r13 = g.new Rule( FACTOR,           IDENT, LEFT_PAREN, LIST, RIGHT_PAREN ),
        r14 = g.new Rule( LIST,             LIST, COMMA, EXPRESSION ),
        r15 = g.new Rule( LIST,             EXPRESSION );
        
    /**
     * main program.
     * 
     * @param args
     *            unused.
     */
    public static void main( String[] args ) {
        System.out.println( g );
        Map<Symbols, Set<Symbols>> first = new EnumMap<Symbols, Set<Symbols>>(Symbols.class);
        for (Symbols s: g.getNonTerminals()) {
            Set<Symbols> set = EnumSet.noneOf( Symbols.class );
            System.out.print("first(" + s + ") = {");
            for (Grammar<Symbols>.Rule sr: g.getRules(s)) {
                
            }
            System.out.println("};");
        }
    }

} /* SampleGrammar */
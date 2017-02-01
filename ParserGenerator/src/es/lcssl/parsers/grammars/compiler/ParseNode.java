/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package es.lcssl.parsers.grammars.compiler;

import java.util.ArrayList;

import es.lcssl.parsers.grammars.Token;
import es.lcssl.parsers.grammars.Grammar.Rule;

/**
 * This is the final result of a parse. The syntax tree describing the input.
 * Nodes are annotated (by means of the {@link Token} value) with the actual
 * input from the source.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class ParseNode<S extends Enum<S>>
    extends ArrayList<ParseNode<S>> {

    private static final long serialVersionUID = -2697023240518291643L;

    private Token<S, ?> m_leftSymbol;

    /**
     * Getter for the {@code Token<S,?>} {@code leftSymbol} attribute. The
     * {@code leftSymbol} attribute represents the left part of the satisfied
     * {@link Rule}.
     * 
     * @return the {@code Token<S,?>} value of the {@code leftSymbol} attribute.
     */
    public Token<S, ?> getLeftSymbol() {
        return m_leftSymbol;
    }

    /**
     * Setter for the {@code Token<S,?>} {@code leftSymbol} attribute. The
     * {@code leftSymbol} attribute represents the left part of the satisfied
     * {@link Rule}.
     * 
     * @param leftSymbol
     *            the leftSymbol to set {@code leftSymbol} attribute to.
     */
    public void setLeftSymbol( Token<S, ?> leftSymbol ) {
        m_leftSymbol = leftSymbol;
    }

} /* ParseTree */
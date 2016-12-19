/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.common;

import net.basen.parsers.generator.Grammar;
import net.basen.parsers.generator.Grammar.Rule;
import net.basen.parsers.parser.CompiledGrammar;
import net.basen.parsers.parser.ParseTree;


/**
 * This is the ListenerEvent class.  This event is used when the parser
 * accepts a {@link Rule} in a {@link Grammar} as a result of scanning
 * input.
 * 
 * This object is immutable.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class ListenerEvent<S extends Enum<S>> {
    private final CompiledGrammar<S>.State.Transition m_transition;
    private final ParseTree<S> m_parseTree;
    
    /**
     * Default constructor for this class.
     * @param transition is the {@link CompiledGrammar.State.Transition} that
     * generated this event.
     * @param parseTree is the {@link ParseTree} accepted up to this event.
     */
    public ListenerEvent(CompiledGrammar<S>.State.Transition transition, ParseTree<S> parseTree) {
        m_transition = transition;
        m_parseTree = parseTree;
    }

    /**
     * Getter for the {@code CompiledGrammar<S>.State.Transition} {@code transition} attribute.
     * @return the {@code CompiledGrammar<S>.State.Transition} value of the {@code transition} attribute.
     */
    public CompiledGrammar<S>.State.Transition getTransition() {
        return m_transition;
    }

    /**
     * Getter for the {@code ParseTree} {@code parseTree} attribute.
     * @return the {@code ParseTree} value of the {@code parseTree} attribute.
     */
    public ParseTree<S> getParseTree() {
        return m_parseTree;
    }
    
} /* ListenerEvent */
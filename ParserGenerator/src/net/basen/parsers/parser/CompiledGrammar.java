/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.parser;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.basen.parsers.common.Listener;
import net.basen.parsers.common.ListenerEvent;
import net.basen.parsers.generator.BasicRule;
import net.basen.parsers.generator.Token;

/**
 * This class allows to execute a compiled parser over a {@link Iterable}
 * {@code <}{@link Symbol}{@code <S>>}.
 * 
 * It allows to registration of listeners to events on reducing rules, so those
 * listeners can act upon the matching of parsing tree structures.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class CompiledGrammar<S extends Enum<S>>
    implements Serializable {

    private static final long serialVersionUID = -2450808883964818073L;

    private final Class<S> m_symbolClass;

    private int m_nextId = 0;

    private final List<State> m_states;

    /**
     * This class represents a {@code CompiledGrammar} {@link State}.
     * 
     * @author Luis Colorado {@code <lcu@basen.net>}
     */
    public class State {
        private final int id = m_nextId++;

        private final Map<S, Transition> m_nextMap =
            new EnumMap<S, Transition>( m_symbolClass );

        public class Transition {

            private final S m_input;

            private final State m_to;

            private final BasicRule<S> m_rule;

            private final List<Listener<S>> m_listeners;

            public Transition( S input, State to ) {
                m_input = input;
                m_to = to;
                m_rule = null;
                m_listeners = new ArrayList<Listener<S>>( 0 );
            }

            public Transition( S input, State to, BasicRule<S> rule ) {
                m_input = input;
                m_to = to;
                m_rule = rule;
                m_listeners = new ArrayList<Listener<S>>( 0 );
            }

            /**
             * Getter for the {@code S} {@code input} attribute.
             * 
             * @return the {@code S} value of the {@code input} attribute.
             */
            public S getInput() {
                return m_input;
            }

            /**
             * Getter for the {@code S} {@code from} attribute.
             * 
             * @return the {@code S} value of the {@code from} attribute.
             */
            public State getFrom() {
                return State.this;
            }

            /**
             * Getter for the {@code S} {@code to} attribute.
             * 
             * @return the {@code S} value of the {@code to} attribute.
             */
            public State getTo() {
                return m_to;
            }

            /**
             * Getter for the {@code BasicRule<S>} {@code rule} attribute.
             * 
             * @return the {@code BasicRule<S>} value of the {@code rule}
             *         attribute.
             */
            public BasicRule<S> getRule() {
                return m_rule;
            }

            /**
             * Getter for the {@code List<Listener>} {@code listeners}
             * attribute.
             * 
             * @return the {@code List<Listener>} value of the {@code listeners}
             *         attribute.
             */
            public List<Listener<S>> getListeners() {
                return m_listeners;
            }

            public void registerAcceptListener( Listener<S> theListener ) {
                m_listeners.add( theListener );
            }

            public void fireAcceptListenerEvent( ParseNode<S> parseTree ) {
                ListenerEvent<S> theEvent =
                    new ListenerEvent<S>( this, parseTree );
                for( Listener<S> l: m_listeners )
                    l.accept( theEvent );
            }
        } // public class Transition

        public State() {
            m_states.add( this );
            // the next assertion must hold always.
            assert ( this == m_states.get( id ) );
        }

        /**
         * Getter for the {@code int} {@code id} attribute.
         * 
         * @return the {@code int} value of the {@code id} attribute.
         */
        public int getId() {
            return id;
        }

        /**
         * Getter for the {@code Map<S,Transition>} {@code nextMap} attribute.
         * 
         * @return the {@code Map<S,Transition>} value of the {@code nextMap}
         *         attribute.
         */
        public Map<S, Transition> getNextMap() {
            return m_nextMap;
        }
    } // public class State

    public CompiledGrammar( Class<S> symbolClass ) {
        m_symbolClass = symbolClass;
        m_states = new ArrayList<State>();
    }

    /**
     * Getter for the {@code Class<S>} {@code symbolClass} attribute.
     * 
     * @return the {@code Class<S>} value of the {@code symbolClass} attribute.
     */
    public Class<S> getSymbolClass() {
        return m_symbolClass;
    }

    /**
     * Getter for the {@code int} {@code nextId} attribute.
     * 
     * @return the {@code int} value of the {@code nextId} attribute.
     */
    public int getNextId() {
        return m_nextId;
    }

    /**
     * Getter for the {@code List<State>} {@code states} attribute.
     * 
     * @return the {@code List<State>} value of the {@code states} attribute.
     */
    public List<State> getStates() {
        return m_states;
    }

    /**
     * Method to parse some {@link CompiledGrammar} over some input and get a
     * {@link ParseNode} tree corresponding to the input. This is an automaton
     * based, bottom-up, non-recursive parser.  
     * 
     * @param input
     *            {@link Iterator} representing the input source to be parsed
     *            for.
     * @param initial
     *            is the main root symbol of the parsing. This identifies the
     *            initial symbol of the Grammar. As a grammar needs the initial
     *            symbol only to initialize the state acceptor, it is passed
     *            here as a parameter, so we can use different starting points
     *            to parse different things.
     * @return The {@link ParseNode} with the input structure.
     * @throws ParsingError
     *             If parsing fails.
     */
    public ParseNode<S> parse( Iterator<Token<S, ?>> input, State st, S target )
        throws ParsingError {
        ArrayDeque<Token<S, ?>> stack = new ArrayDeque<Token<S, ?>>();

        while( input.hasNext() ) {
            Token<S, ?> symbol = input.next();
            if (symbol.)

        }
        // we must have the target symbol on top of the stack.
        if( top == null || top.getNext() != null || top.getKey() != target )
            throw new ParsingError( "Input exhausted while parsing" );
        return top;
    }

} /* CompiledGrammar */
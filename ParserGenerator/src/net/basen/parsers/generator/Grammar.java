/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a context free grammar to implement a context free
 * language. The grammar is implemented as a {@link HashSet} of {@link Rule}s,
 * which indeed disallows to have the same rule several times. The Grammar
 * implements the {@link Serializable} interface, so it can be sent over network
 * connections or stored on permanent storage.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class Grammar<S extends Enum<S>>
    extends TreeSet<Grammar<S>.Rule>
    implements Serializable {

    private static final long serialVersionUID = 2764301811517596578L;

    private final Class<S> m_symbolClass;

    private final Map<S, Set<Rule>> m_rules;

    private final EnumSet<S> m_terminals; // the set of terminals for this grammar.

    private final EnumSet<S> m_nonTerminals; // use both as we can have non used symbols in the grammar.

    /**
     * Rule extends the behaviour of {@link BasicRule} by forcing all rules in
     * the {@link Grammar} instance to belong to this class. This protects a
     * Grammar to have alien {@link BasicRule}s or to share rules with other
     * different instances of the {@link Grammar} class. The Rule instance knows
     * the {@link Grammar} it belongs to, so you can navigate from the
     * {@link Rule} to the {@link Grammar} instance, and access all the grammar
     * instances from there.
     * 
     * @author Luis Colorado {@code <lcu@basen.net>}
     */
    public class Rule
        extends BasicRule<S> {

        private static final long serialVersionUID = 7740054138474881809L;

        /**
         * This constructor accepts a left hand side symbol and a {@link List}
         * of right hand side. The use of {@link List} is forced by the fact
         * that the order of right hand symbols is important.
         * 
         * @param lhs
         *            the left hand symbol of the {@link Rule}.
         * @param rhs
         *            the {@link List}{@code <S>} of symbols that compose the
         *            right hand side of this {@link Rule}.
         */
        public Rule( S lhs, List<S> rhs ) {
            super( lhs, rhs );
            addToMaps();
        }

        /**
         * Varargs version of the {@link #Grammar(Enum, List)} constructor.
         * 
         * @param lhs
         *            the {@code <S>} symbol for the left hand side of the
         *            {@link Rule}.
         * @param rhs
         *            the {@code <S>} symbols for the right hand side of the
         *            {@link Rule}.
         */
        @SafeVarargs
        public Rule( S lhs, S... rhs ) {
            super( lhs, rhs );
            addToMaps();
        }

        /**
         * This method conforms the Maps of terminal and non terminal symbols of
         * the {@link Grammar}. For the left hand side, the method adds the
         * symbol to the {@link Set} of nonterminals and eliminates it from the
         * set of terminal symbols.
         * 
         * Finally, the set of terminals and non terminals is conformed of all
         * the symbols that appear in the {@link Grammar} (and this hasn't to be
         * the whole set of enumerated values, because some symbols can be in no
         * set of both)
         */
        private void addToMaps() {
            S lft = getLhs();
            if( lft == null )
                return;
            m_nonTerminals.add( lft );
            m_terminals.remove( lft );
            for( S s: this )
                if( !m_nonTerminals.contains( s ) )
                    m_terminals.add( s );
            Set<Rule> st = m_rules.get( lft );
            if( st == null ) {
                st = new TreeSet<Rule>();
                m_rules.put( lft, st );
            }
            st.add( this );
        }

        /**
         * Getter for the {@link Grammar} {@code grammar} attribute.
         * 
         * @return the {@link Grammar}{@code <S>} value of the {@code grammar}
         *         attribute.
         */
        public Grammar<S> getGrammar() {
            return Grammar.this;
        }
    } // class Rule

    /**
     * Default constructor for the {@link Grammar} instance.
     * 
     * @param cl
     *            the {@link Class} reference of the symbols used on this
     *            {@link Grammar}.
     */
    public Grammar( Class<S> cl ) {
        m_symbolClass = cl;
        m_terminals = EnumSet.noneOf( cl );
        m_nonTerminals = EnumSet.noneOf( cl );
        m_rules = new EnumMap<S, Set<Rule>>( cl );
    }

    /**
     * Getter for the class reference to the symbols of this {@link Grammar}.
     * This is useful in related classes that have to construct objects that
     * require the class reference to the symbols type.
     * 
     * @return the {@link Class} reference of the enumeration symbols for this
     *         {@link Grammar}.
     */
    public Class<S> getSymbolClass() {
        return m_symbolClass;
    }

    /**
     * Getter for the {@link Set} of non terminal symbols in this
     * {@link Grammar}.
     * 
     * @return an {@link EnumSet}{@code <S>} representing the set of non
     *         terminals registered in this {@link Grammar} instance.
     */
    public Set<S> getNonTerminals() {
        return Collections.unmodifiableSet( m_nonTerminals );
    }

    /**
     * Getter for the {@link Set} of terminal symbols in this {@link Grammar}.
     * The result set is immutable.
     * 
     * @return an immutable version of the terminal symbols set of this
     *         {@link Grammar}.
     */
    public Set<S> getTerminals() {
        return Collections.unmodifiableSet( m_terminals );
    }

    /**
     * Getter for the {@link Set} of rules in this {@link Grammar} for the
     * symbol specified. The returned set is immutable.
     * 
     * @param sym
     *            is the symbol for which the ruleset is to be returned.
     * @return the {@link Set} of rules that have {@code sym} as left hand
     *         symbol.
     */
    public Set<Rule> getRuleSet( S sym ) {
        Set<Rule> res = m_rules.get( sym );
        return res == null ? Collections.emptySet()
            : Collections.unmodifiableSet( res );
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() + "[\n" );
        int i = 0;
        for( BasicRule<S> r: this ) {
            sb.append( r.getClass().getSimpleName() + "[" + i + "]: " + r
                + "\n" );
            i++;
        }
        sb.append( "nonTerminals:" );
        i = 0;
        sb.append( " {" );
        for( S s: m_nonTerminals ) {
            sb.append( i++ == 0 ? "" : ", " );
            sb.append( s.toString() );
        }
        sb.append( "},\n" + "terminals:" );
        i = 0;
        sb.append( " {" );
        for( S s: m_terminals ) {
            sb.append( i++ == 0 ? "" : ", " );
            sb.append( s.toString() );
        }
        sb.append( "}\n" + "]" );
        return sb.toString();
    }

} /* Grammar */
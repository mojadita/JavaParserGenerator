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
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class Grammar<S extends Enum<S>>
    extends ArrayList<Grammar<S>.Rule>
    implements Serializable 
{

    private static final long serialVersionUID = 2764301811517596578L;
    
    Map<S, Set<Rule>> m_rules;
    EnumSet<S> m_terminals;
    EnumSet<S> m_nonTerminals;  // use both as we can have non used symbols in the grammar.
    
    public class Rule extends BasicRule<S> {

        private static final long serialVersionUID = 7740054138474881809L;
        
        public Rule( S lhs, List<S> rhs ) {
            super( lhs, rhs );
            addToMaps();
        }

        private void addToMaps() {
            S lft = getLhs();
            if (lft == null) return;
            m_nonTerminals.add( lft );
            m_terminals.remove( lft );
            for (S s: this) 
                if (!m_nonTerminals.contains( s )) 
                    m_terminals.add( s );
            Set<Rule> st = m_rules.get( lft );
            if (st == null) {
                st = new TreeSet<Rule>();
                m_rules.put( lft, st );
            }
            st.add( this );
        }

		@SafeVarargs
        public Rule( S lhs, S... rhs ) {
            super( lhs, rhs );
            addToMaps();
        }
    } // class Rule
    
    public Grammar(Class<S> cl) {
        m_terminals = EnumSet.noneOf( cl );
        m_nonTerminals = EnumSet.noneOf( cl );
        m_rules = new EnumMap<S, Set<Rule>>( cl );
    }
    
    public EnumSet<S> getNonTerminals() {
        return m_nonTerminals;
    }
    
    public EnumSet<S> getTerminals() {
        return m_terminals;
    }
    
    public Set<Rule> getRuleSet(S sym) {
    	Set<Rule> res = m_rules.get(sym);
    	return res == null ? Collections.emptySet() : res;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName() + "[\n");
        int i = 0;
        for (BasicRule<S> r: this) {
            sb.append( r.getClass().getSimpleName() + "[" + i + "]: " + r + "\n");
            i++;
        }
        sb.append( "nonTerminals:" );
        i = 0;
        sb.append( " {" );
        for (S s: m_nonTerminals) {
            sb.append( i++ == 0 ? "" : ", " );
            sb.append( s.toString() );
        }
        sb.append( "},\n"
            + "terminals:" );
        i = 0;
        sb.append(" {");
        for (S s: m_terminals) {
            sb.append( i++ == 0 ? "" : ", " );
            sb.append( s.toString() );
        }
        sb.append( "}\n"
            + "]" );
        return sb.toString();
    }

} /* Grammar */
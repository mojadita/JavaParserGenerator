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
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class Grammar<S extends Enum<S>&Symbol<S>>
    extends ArrayList<BasicRule<S>>
    implements Serializable 
{

    private static final long serialVersionUID = 2764301811517596578L;
    
    Map<S, Map<String, Rule>> m_rules;
    EnumSet<S> m_terminals;
    EnumSet<S> m_nonTerminals;
    
    public class Rule extends BasicRule<S> {

        private static final long serialVersionUID = 7740054138474881809L;

        private void addToMaps() {
            S lft = getLhs();
            m_nonTerminals.add( lft );
            m_terminals.remove( lft );
            for (S s: this) 
                if (!m_nonTerminals.contains( s )) 
                    m_terminals.add( s );
            Map<String, Rule> lm = m_rules.get( lft );
            if (lm == null) {
                lm = new TreeMap<String, Grammar<S>.Rule>();
                m_rules.put( lft, lm );
            }
            lm.put( toString(), this );
        }
        
        public Rule( S lhs, List<S> rhs ) {
            super( lhs, rhs );
            addToMaps();
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
        m_rules = new EnumMap<S, Map<String,Rule>>( cl );
    }
    
    EnumSet<S> getNonTerminals() {
        return m_nonTerminals;
    }
    
    EnumSet<S> getTerminals() {
        return m_terminals;
    }

} /* Grammar */
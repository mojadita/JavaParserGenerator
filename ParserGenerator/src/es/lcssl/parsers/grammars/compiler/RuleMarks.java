/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package es.lcssl.parsers.grammars.compiler;

import java.util.Iterator;
import java.util.TreeSet;

import es.lcssl.parsers.grammars.BasicRule;

/**
 * This represents a marking on a {@link BasicRule}, representing parsing state.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class RuleMarks<S extends Enum<S>>
    extends TreeSet<Integer>
    implements Comparable<RuleMarks<S>> {

    private static final long serialVersionUID = 8513738629676450750L;

    private final BasicRule<S> m_rule;

    public RuleMarks( BasicRule<S> rule )
        throws NullPointerException {
        if( rule == null )
            throw new NullPointerException( RuleMarks.class
                + ": rule cannot be null" );
        m_rule = rule;
    }

    public RuleMarks<S> addMarks( Integer... theMarks ) {
        for( Integer i: theMarks ) {
            if( i < 0 || i > m_rule.size() )
                throw new ArrayIndexOutOfBoundsException( i );
            add( i );
        }
        return this;
    }

    public RuleMarks<S> deleteMarks( Integer... theMarks ) {
        for( Integer i: theMarks ) {
            if( i < 0 || i > size() )
                throw new ArrayIndexOutOfBoundsException( i );
            remove( i );
        }
        return this;
    }

    /**
     * Getter for the {@code BasicRule<S>} {@code rule} attribute.
     * 
     * @return the {@code BasicRule<S>} value of the {@code rule} attribute.
     */
    public BasicRule<S> getRule() {
        return m_rule;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        sb.append( m_rule.getLhs() + " ::=" ); // can raise NullPointerException.
        if( isEmpty() )
            sb.append( " /* empty */" );
        else
            for( S s: m_rule ) {
                sb.append( contains( i++ ) ? " . " : " " );
                sb.append( s );
            }
        sb.append( contains(i) ? " . ;" : ";" ); // past last element
        return sb.toString();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if( o == null || !(o instanceof RuleMarks))
            return false;
        RuleMarks<S> other = (RuleMarks<S>) o;
        return m_rule.equals( other.m_rule ) && super.equals( other );
    }

    @Override
    public int hashCode() {
        return m_rule.hashCode() + super.hashCode();
    }

    /**
     * Comparison based on the lexicographical order of the set elements.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( RuleMarks<S> o ) {
        if( o == null )
            throw new NullPointerException( "compareTo(null)" );
        int res = getRule().compareTo( o.getRule() );
        if( res != 0 ) return res;
        Iterator<Integer> i1, i2;
        for( i1 = iterator(), i2 = o.iterator(); i1.hasNext() && i2.hasNext(); ) {
            res = i1.next() - i2.next();
            if( res != 0 )
                return res;
        }
        return i1.hasNext() ? +1 : i2.hasNext() ? -1 : 0;
    }

} /* MarkedRule */
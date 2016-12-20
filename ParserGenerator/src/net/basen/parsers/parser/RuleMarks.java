/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.parser;

import java.util.Iterator;
import java.util.TreeSet;
import net.basen.parsers.generator.BasicRule;

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
            if( i < 0 || i >= m_rule.size() )
                throw new ArrayIndexOutOfBoundsException( i );
            System.out.println( this + ": adding new mark [" + i + "]" );
            add( i );
        }
        return this;
    }

    public RuleMarks<S> deleteMarks( Integer... theMarks ) {
        for( Integer i: theMarks ) {
            if( i < 0 || i >= size() )
                throw new ArrayIndexOutOfBoundsException( i );
            System.out.println( this + ": deleting mark [" + i + "]" );
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
        sb.append( ";" );
        return sb.toString();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if( o == null || !(o instanceof RuleMarks))
            return false;
        return m_rule == ( (RuleMarks<S>) o ).m_rule && super.equals( o );
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
        if( getRule() != o.getRule() )
            throw new IllegalArgumentException( "[" + this + "] and [" + o
                + "] don't mark the same rule" );
        Iterator<Integer> i1, i2;
        for( i1 = iterator(), i2 = o.iterator(); i1.hasNext() && i2.hasNext(); ) {
            int res = i1.next() - i2.next();
            if( res != 0 )
                return res;
        }
        return i1.hasNext() ? +1 : i2.hasNext() ? -1 : 0;
    }

} /* MarkedRule */
/*
 * $Id$ Author: Luis Colorado <lcu@basen.net> Date: Aug 25, 2016 11:52:40 AM.
 * Project: ParserGenerator Package: net.basen.parsers.generator Disclaimer: (C)
 * 2016 LUIS COLORADO. All rights reserved.
 */

package net.basen.parsers.generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Luis Colorado {@code <lcu@basen.net>}.
 *
 */
public class BasicRule<S extends Enum<S>>
    extends ArrayList<S>
    implements Comparable<BasicRule<S>> {
    private static final long serialVersionUID = 6212726366672386484L;

    private S m_lhs;

    public BasicRule() {
    }

    @SafeVarargs
    public BasicRule( S lhs, S... rhs ) {
        super( rhs.length );
        m_lhs = lhs;
        for( S s: rhs )
            add( s );
    }

    public BasicRule( S lhs, Collection<S> rhs ) {
        m_lhs = lhs;
        addAll( rhs );
    }

    /**
     * Getter for the {@code S} {@code lhs} attribute.
     * 
     * @return the {@code S} {@code lhs}.
     */
    public S getLhs() {
        return m_lhs;
    }

/**
	 * Setter for the {@code S} {@code lhs.
	 * @param lhs the {@code S} {@code lhs} to set.
	 */
    public void setLhs( S lhs ) {
        m_lhs = lhs;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( m_lhs + " ::=" ); // can raise NullPointerException.
        if( isEmpty() )
            sb.append( " /* empty */" );
        else
            for( S s: this )
                sb.append( " " + s );
        sb.append( ";" );
        return sb.toString();
    }

    /**
     * Compares two {@link BasicRule}s in lexicographical order.
     * 
     * @param o
     *            the other object to compare.
     * @return the method returns {@code < 0} when {@code this} is less than
     *         {@code o}, {@code 0} for equality, and {@code > 0} if
     *         {@code this} is greater than {@code o}
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( BasicRule<S> o ) {
        if( o == null )
            return +1;
        int res =
            getLhs() == null ? o.getLhs() == null ? 0 : -1
                : getLhs().compareTo( o.getLhs() );
        if( res != 0 )
            return res;
        int i, this_size = size(), other_size = o.size();
        for( i = 0; i < this_size && i < other_size; i++ ) {
            res = get( i ).compareTo( o.get( i ) );
            if( res != 0 )
                return res;
        }
        if( i < this_size )
            return +1;
        if( i < other_size )
            return -1;
        return 0;
    }
} /* BasicRule */
/* $Id$
 * Author: Luis Colorado <lcu@basen.net>
 * Date: Aug 25, 2016 11:52:40 AM.
 * Project: ParserGenerator
 * Package: net.basen.parsers.generator
 * Disclaimer: (C) 2016 LUIS COLORADO.  All rights reserved.
 */

package net.basen.parsers.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Colorado {@code <lcu@basen.net>}.
 *
 */
public class BasicRule<S extends Symbol<S>> 
	extends ArrayList<S>
{
	private static long next_id = 0;
	private static final long serialVersionUID = 6212726366672386484L;
	private long m_id;
	private S m_lhs;
	
	public BasicRule() {
		m_id = next_id++;
	}
	
	@SafeVarargs
	public BasicRule(S lhs, S... rhs) {
		super(rhs.length);
		m_id = next_id++;
		m_lhs = lhs;
		for(S s: rhs) add(s);
	}

	public BasicRule(S lhs, List<S> rhs) {
		m_id = next_id++;
		m_lhs = lhs;
		addAll(rhs);
	}

	/**
	 * Getter for the {@code long} {@code id} attribute.
	 * @return the {@code long} {@code id}.
	 */
	public long getId() {
		return m_id;
	}

	/**
	 * Getter for the {@code S} {@code lhs} attribute.
	 * @return the {@code S} {@code lhs}.
	 */
	public S getLhs() {
		return m_lhs;
	}

	/**
	 * Setter for the {@code S} {@code lhs.
	 * @param lhs the {@code S} {@code lhs} to set.
	 */
	public void setLhs(S lhs) {
		m_lhs = lhs;
	}
	
	@Override
    public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(m_lhs + ":");  // can raise NullPointerException.
		if (isEmpty())
			sb.append(" /* empty */");
		else for (S s: this)
			sb.append(" " + s);
		sb.append(";");
		return sb.toString();
	}
}
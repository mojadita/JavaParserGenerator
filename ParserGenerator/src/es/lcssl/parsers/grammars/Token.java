/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package es.lcssl.parsers.grammars;

import java.io.Serializable;
import java.util.Map.Entry;

import es.lcssl.parsers.grammars.compiler.CompiledGrammar;

/**
 * This is a token, as returned by a scanner to be feed into
 * a {@link CompiledGrammar} to parse some input.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class Token<S extends Enum<S>, V>
    implements Entry<S, V>, Serializable {
    
    private static final long serialVersionUID = -1971759809784055355L;
    
    private final S m_key;
    private V m_value; // specific token value returned by the scanner.
    
    public Token(S key, V value) {
        m_key = key; m_value = value;
    }
    
    @Override
    public S getKey() {
        return m_key;
    }
    
    @Override
    public V getValue() {
        return m_value;
    }
    
    @Override
    public V setValue( V value ) {
        V old_value = m_value;
        m_value = value;
        return old_value;
    }
    
} /* Token */
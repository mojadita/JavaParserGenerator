/**
 * $Id: $
 *
 * Copyright (C) 2016 BaseN.
 *
 * All rights reserved.
 */
package net.basen.parsers.parser;

/**
 * Parsing error.
 * 
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public class ParsingError
    extends Exception {

    private static final long serialVersionUID = 2699859788079367023L;
    
    private final ParseNode<?> m_partialTree;

    public ParsingError() {
        super();
        m_partialTree = null;
    }

    public ParsingError( String message ) {
        super( message );
        m_partialTree = null;
    }
    
    public ParsingError( ParseNode<?> partialTree ) {
        m_partialTree = partialTree;
    }
    
    public ParsingError( String message, ParseNode<?> partialTree ) {
        super( message );
        m_partialTree = partialTree;
    }

    /**
     * Getter for the {@code ParseNode<?>} {@code partialTree} attribute.
     * @return the {@code ParseNode<?>} value of the {@code partialTree} attribute.
     */
    public ParseNode<?> getPartialTree() {
        return m_partialTree;
    }

} /* ParsingError */
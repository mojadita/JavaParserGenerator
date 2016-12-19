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

    public ParsingError() {
        super();
    }

    public ParsingError( String message ) {
        super( message );
    }

} /* ParsingError */
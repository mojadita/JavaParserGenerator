/* Project: ParserGenerator
 * Author: Luis Colorado <luiscoloradourcola@gmail.com>
 * Date: 2016.12.19 14.30
 * Copyright (C) 2016 LUIS COLORADO.
 *
 * All rights reserved.  This file is open source as ruled by the
 * BSD License
 */
package es.lcssl.parsers.common;

import es.lcssl.parsers.grammars.Grammar;
import es.lcssl.parsers.grammars.compiler.ParseNode;

/**
 * Interface to register acceptors for {@link Grammar} rule matchers.
 * @author Luis Colorado {@code <lcu@basen.net>}
 */
public interface Listener<S extends Enum<S>> {

	/**
     * Accepting method for {@link Listener}s of {@link Grammar} rule
     * matchers.  This method will be called when a matching rule is
     * accepted by the parser.
     * 
     * @param event is the {@link ListenerEvent} that triggers the
     * accept call. It is composed of the {@link Transition} that triggers
     * the call and the {@link ParseNode} collected up to this parsing
     * state.  From this information, and navigating, all the Grammar info
     * can be obtained.
     */
    void accept(ListenerEvent<S> event);

} /* Listener */
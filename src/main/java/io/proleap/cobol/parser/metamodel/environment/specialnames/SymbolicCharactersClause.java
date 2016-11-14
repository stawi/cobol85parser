/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.parser.metamodel.environment.specialnames;

import io.proleap.cobol.parser.metamodel.CobolDivisionElement;

public interface SymbolicCharactersClause extends CobolDivisionElement {

	enum Type {
		AlphaNumeric, National
	}

	Type getType();

	void setType(Type type);
}

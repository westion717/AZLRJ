package Zeson.AZLRJ.parsec.action;

import Zeson.AZLRJ.common.AbstractParsec;

public interface BinOperatorSemanticAction {

	Object doAction(AbstractParsec op, Object left, Object right);

}

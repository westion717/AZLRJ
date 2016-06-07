package Zeson.AZLRJ.parsec;

import javax.tools.Diagnostic.Kind;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.EOFStream;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;

public class IntegerParsec extends AbstractParsec {

	private ParsecLiteralSemanticAction literalSemanticAction;

	public IntegerParsec(ParsecLiteralSemanticAction literalSemanticAction) {
		super();
		this.literalSemanticAction = literalSemanticAction;
	}

	public IntegerParsec() {
		super();
	}

	@Override
	public ParsedResult internalParse(Source inputString) {

		StringBuilder str = new StringBuilder();

		char c;

		while (true) {
			try {
				c = inputString.getCurrentChar();
			} catch (EOFStream e) {
				break;
			}
			if (!Character.isDigit(c))
				break;
			str.append(c);
			inputString.peek(1);
		}

		if (str.length() == 0) {
			ParsedResult p = new FailedResult(Kind.ERROR, this,
					inputString.pos, inputString.pos, inputString.pos,
					inputString.line, inputString.column, null,
					"expected a integer");
			return p;
		}

		SuccessResult parsedResult = new SuccessResult(null, inputString);

		if (this.literalSemanticAction != null) {
			parsedResult.setResultObject(this.literalSemanticAction.doAction(
					str.toString(), inputString));
		}
		inputString.column += str.length();

		return parsedResult;

	}
}

package Zeson.AZLRJ.parsec;

import javax.tools.Diagnostic.Kind;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.EOFStream;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;

public class TermParsec extends AbstractParsec {

	private String name;

	private ParsecLiteralSemanticAction literalSemanticAction;

	public TermParsec(String name,
			ParsecLiteralSemanticAction literalSemanticAction) {
		super();
		this.name = name;
		this.literalSemanticAction = literalSemanticAction;
	}

	@Override
	public ParsedResult internalParse(Source inputString) {

		Source dup = inputString.fork();

		String str;
		try {
			str = parseString(inputString);
		} catch (EOFStream e) {
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos, inputString.line,
					inputString.column, FailedResult.ErrorCode.EOF,
					"end of file");
		}

		if (str == null)
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos, inputString.line,
					inputString.column, null, name
							+ " can not be started with `"
							+ inputString.getLastChar() + "`");

		SuccessResult parsedResult = new SuccessResult(null, inputString);

		if (this.literalSemanticAction != null) {
			parsedResult.setResultObject(this.literalSemanticAction.doAction(
					str, dup));
		}

		return parsedResult;

	}

	public String parseString(Source inputString) throws EOFStream {
		StringBuilder str = new StringBuilder();

		char c;
		c = inputString.getCurrentChar();

		if (!Character.isUnicodeIdentifierStart(c))
			return null;

		str.append(c);
		inputString.peek(1);

		c = inputString.getCurrentChar();

		while (Character.isUnicodeIdentifierPart(c)) {
			str.append(c);
			inputString.peek(1);
			c = inputString.getCurrentChar();
		}
		inputString.column += str.length();
		return str.toString();
	}

}

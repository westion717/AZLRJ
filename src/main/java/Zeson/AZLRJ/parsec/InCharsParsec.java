package Zeson.AZLRJ.parsec;

import java.util.HashSet;
import java.util.Set;

import javax.tools.Diagnostic.Kind;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.EOFStream;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;

public final class InCharsParsec extends AbstractParsec {

	private ParsecLiteralSemanticAction charSemanticAction;

	private Set<Character> chars = new HashSet<Character>();

	public InCharsParsec(ParsecLiteralSemanticAction charSemanticAction,
			char... charas) {
		super();
		this.charSemanticAction = charSemanticAction;
		for (Character character : charas) {
			this.chars.add(character);
		}
	}

	public void addChars(char c) {
		this.chars.add(c);
	}

	public InCharsParsec(char... charas) {
		super();
		for (Character character : charas) {
			this.chars.add(character);
		}
	}

	@Override
	public ParsedResult internalParse(Source inputString) {
		char c;
		try {
			c = inputString.getCurrentChar();
		} catch (EOFStream e) {
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos, inputString.line,
					inputString.column, FailedResult.ErrorCode.EOF,
					"end of file");
		}

		if (!this.chars.contains(c))
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos, inputString.line,
					inputString.column, null, "current char is `" + c + "`");

		SuccessResult parsedResult = new SuccessResult(null, inputString);

		if (this.charSemanticAction != null) {
			parsedResult.setResultObject(this.charSemanticAction.doAction(c
					+ "", inputString));
		}
		inputString.peek(1);
		return parsedResult;
	}
}

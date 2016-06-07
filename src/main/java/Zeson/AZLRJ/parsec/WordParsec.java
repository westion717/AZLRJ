package Zeson.AZLRJ.parsec;

import javax.tools.Diagnostic.Kind;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.EOFStream;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;

public class WordParsec extends AbstractParsec {

	private ParsecLiteralSemanticAction literalSemanticAction;

	private String txt;

	public WordParsec(ParsecLiteralSemanticAction literalSemanticAction,
			String txt) {
		super();
		this.literalSemanticAction = literalSemanticAction;
		this.txt = txt;
	}

	public WordParsec(String txt) {
		super();
		this.txt = txt;
	}

	@Override
	public ParsedResult internalParse(Source inputString) {
		String str;
		try {
			str = inputString.getCurrentLengthStr(txt.length());
		} catch (EOFStream e) {
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos, inputString.line,
					inputString.column, FailedResult.ErrorCode.EOF,
					"end of file");
		}

		if (str == null)
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos + txt.length(),
					inputString.line, inputString.column,
					FailedResult.ErrorCode.EOF, "expected `" + txt + "`");

		if (!str.equals(txt))
			return new FailedResult(Kind.ERROR, this, inputString.pos,
					inputString.pos, inputString.pos + txt.length(),
					inputString.line, inputString.column, null, "expected `"
							+ txt + "`, but actual is " + str);

		SuccessResult parsedResult = new SuccessResult(null, inputString);

		if (this.literalSemanticAction != null) {
			parsedResult.setResultObject(this.literalSemanticAction.doAction(
					txt, inputString));
		}
		inputString.peek(txt.length());
		inputString.column += txt.length();
		return parsedResult;

	}
}

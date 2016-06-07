package Zeson.AZLRJ.parsec;

import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecObjectSemanticAction;

public final class EmptyOrOneParsec extends AbstractParsec {

	private AbstractParsec parsec;
	private ParsecObjectSemanticAction semanticAction;

	public EmptyOrOneParsec(AbstractParsec parsec,
			ParsecObjectSemanticAction semanticAction) {
		super();
		this.parsec = parsec;
		this.semanticAction = semanticAction;
	}

	public EmptyOrOneParsec(AbstractParsec parsec) {
		super();
		this.parsec = parsec;
	}

	public void setParsec(AbstractParsec parsec) {
		this.parsec = parsec;
	}

	public void setSemanticAction(ParsecObjectSemanticAction semanticAction) {
		this.semanticAction = semanticAction;
	}

	@Override
	public ParsedResult internalParse(Source inputString) {

		ParsedResult tmpresult = this.parsec.parse(inputString);
		SuccessResult result = null;

		if (tmpresult.getClass() == FailedResult.class)
			return new SuccessResult(null, inputString);

		Assume.assumeTrue(tmpresult.getClass() == SuccessResult.class);

		result = (SuccessResult) tmpresult;

		if (this.semanticAction != null)
			result.setResultObject(semanticAction.doAction(result
					.getResultObject()));
		return result;
	}

}

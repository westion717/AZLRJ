package Zeson.AZLRJ.parsec;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecObjectsSemanticAction;

public final class ClosureOrPlusParsec extends AbstractParsec {

	private AbstractParsec parsec;
	private ParsecObjectsSemanticAction semanticAction;
	private boolean isPlus = false;

	public void setParsec(AbstractParsec parsec) {
		this.parsec = parsec;
	}

	public void setSemanticAction(ParsecObjectsSemanticAction semanticAction) {
		this.semanticAction = semanticAction;
	}

	public ClosureOrPlusParsec(AbstractParsec parsec, boolean isPlus) {
		super();
		this.parsec = parsec;
		this.isPlus = isPlus;
	}

	public ClosureOrPlusParsec(AbstractParsec parsec,
			ParsecObjectsSemanticAction semanticAction, boolean isPlus) {
		super();
		this.parsec = parsec;
		this.semanticAction = semanticAction;
		this.isPlus = isPlus;
	}

	@Override
	public ParsedResult internalParse(Source inputString) {
		SuccessResult result = new SuccessResult(null, inputString);
		List<Object> resultObjects = new ArrayList<Object>();
		SuccessResult lastResult = null;
		boolean isFirst = true;
		while (true) {
			lastResult = result;

			ParsedResult tmpresult = this.parsec.parse(result
					.getRestInputString());
			if (tmpresult.getClass() == FailedResult.class) {

				if (isFirst == true && isPlus == true)
					return tmpresult;

				/*
				 * if (AbstractParsec.failMsg.getPosition() > result
				 * .getRestInputString().pos) return new FailedResult();
				 */
				else {
					SuccessResult finalResult = new SuccessResult(null,
							lastResult.getRestInputString());
					if (this.semanticAction != null)
						finalResult.setResultObject(semanticAction
								.doAction(resultObjects));

					return finalResult;
				}
			}

			Assume.assumeTrue(tmpresult.getClass() == SuccessResult.class);
			result = (SuccessResult) tmpresult;
			resultObjects.add(result.getResultObject());

			isFirst = false;

		}
	}

}

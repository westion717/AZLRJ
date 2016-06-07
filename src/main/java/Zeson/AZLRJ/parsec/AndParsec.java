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

public final class AndParsec extends AbstractParsec {

	private List<AbstractParsec> parsecs = new ArrayList<AbstractParsec>();
	private ParsecObjectsSemanticAction semanticActions;

	@SafeVarargs
	public AndParsec(AbstractParsec... iParsecs) {
		super();

		for (AbstractParsec iParsec : iParsecs) {
			this.parsecs.add(iParsec);
		}

	}

	@SafeVarargs
	public AndParsec(ParsecObjectsSemanticAction semanticActions,
			AbstractParsec... iParsecs) {
		super();
		this.semanticActions = semanticActions;
		for (AbstractParsec iParsec : iParsecs) {
			this.parsecs.add(iParsec);
		}

	}

	@Override
	public ParsedResult internalParse(Source inputString) {

		List<Object> resultObjects = new ArrayList<Object>(parsecs.size());

		SuccessResult result = new SuccessResult(null, inputString);

		for (AbstractParsec iParsec : parsecs) {
			ParsedResult tmpresult = iParsec.parse(result.getRestInputString());

			if (tmpresult.getClass() == FailedResult.class) {
				return tmpresult;
			}

			Assume.assumeTrue(tmpresult.getClass() == SuccessResult.class);

			result = (SuccessResult) tmpresult;

			resultObjects.add(result.getResultObject());
		}

		SuccessResult parsedResult = new SuccessResult(null,
				result.getRestInputString());

		if (this.semanticActions != null) {
			parsedResult.setResultObject(this.semanticActions
					.doAction(resultObjects));
		}

		return parsedResult;
	}

	public void addParsec(AbstractParsec parsec) {

		parsecs.add(parsec);

	}

	public void setSemanticActions(ParsecObjectsSemanticAction semanticActions) {
		this.semanticActions = semanticActions;
	}

}

package Zeson.AZLRJ.parsec;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.ParsecObjectSemanticAction;

public final class OrParsec extends AbstractParsec {

	private final List<AbstractParsec> parsecs = new ArrayList<AbstractParsec>();
	private ParsecObjectSemanticAction semanticAction;

	@SafeVarargs
	public OrParsec(ParsecObjectSemanticAction semanticAction,
			AbstractParsec... iParsecs) {
		super();

		this.semanticAction = semanticAction;

		for (AbstractParsec iParsec : iParsecs) {
			this.parsecs.add(iParsec);
		}

	}

	@SafeVarargs
	public OrParsec(AbstractParsec... iParsecs) {
		super();

		for (AbstractParsec iParsec : iParsecs) {
			this.parsecs.add(iParsec);
		}

	}

	@Override
	public ParsedResult internalParse(Source inputString) {

		ParsedResult result = null;
		// FailedResult fail = null;

		for (AbstractParsec iParsec : parsecs) {
			result = iParsec.parse(inputString);
			if (result.getClass() == SuccessResult.class) {
				SuccessResult parsedResult = (SuccessResult) result;

				if (this.semanticAction != null) {
					parsedResult.setResultObject(this.semanticAction
							.doAction(parsedResult.getResultObject()));
				}

				return parsedResult;

			}

			Assume.assumeTrue(result.getClass() == FailedResult.class);

			/*
			 * if (fail == null || ((FailedResult) result).getPosition() > fail
			 * .getPosition()) fail = (FailedResult) result; //
			 * System.out.println(fail);
			 */

		}

		return new FailedResult();

	}

	public void addParsec(AbstractParsec parsec) {
		parsecs.add(parsec);
	}

}

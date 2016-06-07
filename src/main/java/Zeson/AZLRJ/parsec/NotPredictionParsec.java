package Zeson.AZLRJ.parsec;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.common.SuccessResult;

public class NotPredictionParsec extends AbstractParsec {

	AbstractParsec parsec;

	public NotPredictionParsec(AbstractParsec parsec) {
		super();
		this.parsec = parsec;
	}

	@Override
	public ParsedResult internalParse(Source inputString) {
		Source dup = inputString.fork();
		ParsedResult result = parsec.parse(dup);

		if (result.getClass() == FailedResult.class) {
			return new SuccessResult(null, inputString);
		}
		return new FailedResult();
	}

}

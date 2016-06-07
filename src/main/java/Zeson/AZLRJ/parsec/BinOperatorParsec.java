package Zeson.AZLRJ.parsec;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.action.BinOperatorSemanticAction;

public final class BinOperatorParsec extends AbstractParsec {

	private Integer currentPrec = null;
	private AbstractParsec currentOp = null;

	private BinOperatorSemanticAction binOperatorSemanticAction;

	private AbstractParsec parsec;

	private Map<AbstractParsec, Integer> operatorPrecedence = new LinkedHashMap<>();

	public BinOperatorParsec(AbstractParsec parsec,
			BinOperatorSemanticAction action) {
		super();
		this.parsec = parsec;
		this.binOperatorSemanticAction = action;
	}

	public void addOperator(AbstractParsec opParsec, Integer precedence) {

		if (opParsec == null || precedence == null)
			throw new RuntimeException("either opParsec or precedence is null");
		operatorPrecedence.put(opParsec, precedence);
	}

	@Override
	public ParsedResult internalParse(Source inputString) {

		ParsedResult LHS = parsec.parse(inputString);
		if (LHS.getClass() == FailedResult.class)
			return LHS;
		Assume.assumeTrue(LHS.getClass() == SuccessResult.class);

		return ParseBinOpRHS(Integer.MIN_VALUE, (SuccessResult) LHS);
	}

	private Source getOp(Source s) {
		for (Entry<AbstractParsec, Integer> entry : operatorPrecedence
				.entrySet()) {
			ParsedResult result = entry.getKey().parse(s);
			if (result.getClass() == SuccessResult.class) {
				currentPrec = entry.getValue();
				currentOp = entry.getKey();

				Assume.assumeTrue(result.getClass() == SuccessResult.class);

				return ((SuccessResult) result).getRestInputString();
			}
		}
		return null;
	}

	private ParsedResult ParseBinOpRHS(Integer ExprPrec, SuccessResult LHS) {
		// 3*4-3*2+2*4/2
		// If this is a binop, find its precedence.
		while (true) {
			Source dupSource = LHS.getRestInputString().fork();
			Source next = getOp(dupSource);
			if (next == null) {
				return LHS;
			}

			// If this is a binop that binds at least as tightly as the current
			// binop,
			// consume it, otherwise we are done.
			Assume.assumeNotNull(currentPrec);
			int TokPrec = currentPrec;

			Assume.assumeNotNull(ExprPrec);
			if (TokPrec < ExprPrec)
				return LHS;

			Assume.assumeNotNull(currentOp);
			// Okay, we know this is a binop.
			AbstractParsec BinOp = currentOp;

			// Parse the primary expression after the binary operator.
			ParsedResult tmpRHS = parsec.parse(next);
			if (tmpRHS.getClass() == FailedResult.class)
				return tmpRHS;

			Assume.assumeTrue(tmpRHS.getClass() == SuccessResult.class);
			SuccessResult RHS = (SuccessResult) tmpRHS;
			// If BinOp binds less tightly with RHS than the operator after RHS,
			// let
			// the pending operator take RHS as its LHS.
			Source dup = RHS.getRestInputString().fork();

			getOp(dup);

			Assume.assumeNotNull(currentPrec);
			int NextPrec = currentPrec;

			if (TokPrec < NextPrec) {
				tmpRHS = ParseBinOpRHS(TokPrec + 1, RHS);
				if (tmpRHS.getClass() == FailedResult.class)
					return tmpRHS;

				Assume.assumeTrue(tmpRHS.getClass() == SuccessResult.class);
				RHS = (SuccessResult) tmpRHS;
			}

			// Merge LHS/RHS.
			LHS = new SuccessResult(binOperatorSemanticAction.doAction(BinOp,
					LHS.getResultObject(), RHS.getResultObject()),
					RHS.getRestInputString());
		}
	}

}

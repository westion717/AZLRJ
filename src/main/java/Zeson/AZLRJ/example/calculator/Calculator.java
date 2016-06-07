package Zeson.AZLRJ.example.calculator;

import java.util.List;

import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.AndParsec;
import Zeson.AZLRJ.parsec.BinOperatorParsec;
import Zeson.AZLRJ.parsec.IntegerParsec;
import Zeson.AZLRJ.parsec.OrParsec;
import Zeson.AZLRJ.parsec.action.BinOperatorSemanticAction;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.parsec.action.ParsecObjectsSemanticAction;
import Zeson.AZLRJ.parser.WordBuilder;
import Zeson.AZLRJ.utils.TestTool;

public class Calculator {

	private static final IntegerParsec _integer = new IntegerParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Integer doAction(String word, Source source) {
					System.out.println("num=" + word + "," + source.line + ":"
							+ source.column);

					return Integer.parseInt(word);
				}
			});
	private static final WordBuilder wb = WordBuilder
			.get('\n', '\t', ' ', '\r');

	private static final AbstractParsec add = wb.createParsec("+");
	private static final AbstractParsec sub = wb.createParsec("-");

	private static final AbstractParsec mul = wb.createParsec("*");
	private static final AbstractParsec div = wb.createParsec("/");
	private static final AbstractParsec left = wb.createParsec("(");
	private static final AbstractParsec right = wb.createParsec(")");

	private static final AbstractParsec Int = wb.createParsec(_integer);

	private static final OrParsec F = new OrParsec(Int);

	private static final BinOperatorSemanticAction cal_action = new BinOperatorSemanticAction() {

		@Override
		public Object doAction(AbstractParsec op, Object left, Object right) {

			Assume.assumeNotNull(left, right);
			Assume.assumeTrue("left must be a integer",
					left.getClass() == Integer.class);
			Assume.assumeTrue("right must be a integer",
					right.getClass() == Integer.class);

			String opStr = null;
			int resInteger = 0;

			if (op == add) {
				opStr = "+";
				resInteger = (Integer) left + (Integer) right;
			} else if (op == sub) {
				opStr = "-";
				resInteger = (Integer) left - (Integer) right;
			} else if (op == mul) {
				opStr = "*";
				resInteger = (Integer) left * (Integer) right;
			} else if (op == div) {
				opStr = "/";
				resInteger = (Integer) left / (Integer) right;
			}

			System.out.println("[" + opStr + " " + left + " " + right + " "
					+ "]");

			return resInteger;

		}
	};

	private static final BinOperatorParsec E = new BinOperatorParsec(F,
			cal_action);

	private static String text = "3*(3-(6)* 2)+2 *4/2";
	private static StringBuffer sb = new StringBuffer(text);
	private static Source source = new Source(sb);
	static {
		F.addParsec(new AndParsec(new ParsecObjectsSemanticAction() {

			@Override
			public Object doAction(List<Object> resultObjects) {

				Assume.assumeNotNull(resultObjects.get(1));

				return resultObjects.get(1);
			}
		}, left, E, right));

		E.addOperator(add, 10);
		E.addOperator(sub, 10);
		E.addOperator(mul, 20);
		E.addOperator(div, 20);
	}

	public static void main(String args[]) {

		TestTool.assertEqual(new Integer(-23), E.parse(source));

	}
}

package Zeson.AZLRJ.test.parsec;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.InCharsParsec;
import Zeson.AZLRJ.parsec.action.ParsecCharSemanticAction;
import Zeson.AZLRJ.utils.TestTool;

public class InCharsParsecTest extends ParsecBaseTest {

	private final InCharsParsec inCharsParsec = new InCharsParsec(
			new ParsecCharSemanticAction() {

				@Override
				public Object doAction(char character, Source source) {

					return character + "";
				}
			}, '\t', ' ', '\n', '\r');

	private static String text = " \t \r \n \t";

	@BeforeClass
	public static void init() {
		sb.append(text);

	}

	@Test
	public void test01() {

		TestTool.assertValue(inCharsParsec, source, " ", "\t", " ", "\r", " ",
				"\n", " ", "\t");
	}
}

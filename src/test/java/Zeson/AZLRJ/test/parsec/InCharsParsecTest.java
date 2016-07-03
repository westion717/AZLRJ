package Zeson.AZLRJ.test.parsec;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.InCharsParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.utils.TestTool;

public class InCharsParsecTest extends ParsecBaseTest {

	private final InCharsParsec inCharsParsec = new InCharsParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					return word;
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

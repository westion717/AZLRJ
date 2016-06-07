package Zeson.AZLRJ.test.parsec;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.IntegerParsec;
import Zeson.AZLRJ.parsec.OrParsec;
import Zeson.AZLRJ.parsec.WordParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.utils.TestTool;

public class OrParsecTest extends ParsecBaseTest {

	private static final IntegerParsec num1 = new IntegerParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {

					return Integer.parseInt(word);
				}
			});
	private static final WordParsec add = new WordParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					return null;
				}
			}, "+");

	private static final OrParsec or = new OrParsec(num1, add);

	private static String text = "4+4444";

	@BeforeClass
	public static void init() {
		sb.append(text);
	}

	@Test
	public void test01() {
		TestTool.assertValue(or, source, new Integer(4), null,
				new Integer(4444));

	}
}

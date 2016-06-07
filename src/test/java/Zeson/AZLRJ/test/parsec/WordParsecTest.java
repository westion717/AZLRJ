package Zeson.AZLRJ.test.parsec;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.WordParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.utils.TestTool;

public class WordParsecTest extends ParsecBaseTest {

	private final WordParsec add = new WordParsec("+");

	private final WordParsec sub = new WordParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					assertEquals("-", word);
					return null;
				}
			}, "-");

	private final WordParsec num = new WordParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {

					assertEquals("4444", word);
					return Integer.parseInt(word);
				}
			}, "4444");

	private static String text = "+-4444";

	@BeforeClass
	public static void init() {
		sb.append(text);
	}

	@Test
	public void test01() {

		TestTool.assertEqual(null, add.parse(source));
	}

	@Test
	public void test02() {

		TestTool.assertEqual(null, sub.parse(source));
	}

	@Test
	public void test03() {
		TestTool.assertEqual(null, num.parse(source));
		;

	}

}

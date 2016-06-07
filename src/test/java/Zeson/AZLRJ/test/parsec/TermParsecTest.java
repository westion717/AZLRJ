package Zeson.AZLRJ.test.parsec;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.TermParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.utils.TestTool;

public class TermParsecTest extends ParsecBaseTest {

	private final TermParsec identifierParsec = new TermParsec("identifier",
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					assertEquals("iamident", word);

					return word;
				}
			});

	private static String text = "iamident";

	@BeforeClass
	public static void init() {
		sb.append(text);

	}

	@Test
	public void test01() {

		TestTool.assertEqual("iamident", identifierParsec.parse(source));

	}
}

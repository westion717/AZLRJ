package Zeson.AZLRJ.test.parsec;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.parsec.IntegerParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;

public class IntegerParsecTest extends ParsecBaseTest {

	private final IntegerParsec integerParsec = new IntegerParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					assertEquals("4444", word);

					return Integer.parseInt(word);
				}
			});

	private static String text = "4444";

	@BeforeClass
	public static void init() {
		sb.append(text);

	}

	@Test
	public void test01() {
		ParsedResult result = integerParsec.parse(source);

		assertEquals(new Integer(4444),
				((SuccessResult) result).getResultObject());

	}

}

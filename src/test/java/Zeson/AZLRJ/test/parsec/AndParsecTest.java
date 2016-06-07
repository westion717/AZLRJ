package Zeson.AZLRJ.test.parsec;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.AndParsec;
import Zeson.AZLRJ.parsec.WordParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.parsec.action.ParsecObjectsSemanticAction;
import Zeson.AZLRJ.utils.TestTool;

public class AndParsecTest extends ParsecBaseTest {

	private static final WordParsec num1 = new WordParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {

					assertEquals("4", word);
					return Integer.parseInt(word);
				}
			}, "4");

	private static final WordParsec add = new WordParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					return null;
				}
			}, "+");

	private static final WordParsec num2 = new WordParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {

					assertEquals("4444", word);
					return Integer.parseInt(word);
				}
			}, "4444");

	private static final AndParsec and = new AndParsec(num1, add, num2);
	private static String text = "4+4444";

	@BeforeClass
	public static void init() {
		sb.append(text);

		and.setSemanticActions(new ParsecObjectsSemanticAction() {

			@Override
			public Object doAction(List<Object> resultObjects) {

				assertEquals(3, resultObjects.size());
				System.out.println(resultObjects);

				assertEquals(new Integer(4), resultObjects.get(0));
				assertEquals(null, resultObjects.get(1));
				assertEquals(new Integer(4444), resultObjects.get(2));

				return (Integer) resultObjects.get(0)
						+ (Integer) resultObjects.get(2);
			}
		});
	}

	@Test
	public void test01() {
		TestTool.assertEqual(new Integer(4448), and.parse(source));

	}
}

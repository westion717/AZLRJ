package Zeson.AZLRJ.parser;

import java.util.List;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.parsec.AndParsec;
import Zeson.AZLRJ.parsec.ClosureOrPlusParsec;
import Zeson.AZLRJ.parsec.InCharsParsec;
import Zeson.AZLRJ.parsec.WordParsec;
import Zeson.AZLRJ.parsec.action.ParsecLiteralSemanticAction;
import Zeson.AZLRJ.parsec.action.ParsecObjectsSemanticAction;

public class WordBuilder {

	private char CR;

	private InCharsParsec whiteCharParsec = new InCharsParsec(
			new ParsecLiteralSemanticAction() {

				@Override
				public Object doAction(String word, Source source) {
					if (word.equals(CR + "")) {
						source.line += 1;
						source.column = 1;
					} else {
						source.column += 1;
					}
					return null;
				}
			});

	private ClosureOrPlusParsec whiteSpace = new ClosureOrPlusParsec(
			whiteCharParsec, false);

	private WordBuilder() {

	}

	private AbstractParsec createParsecInternal(AbstractParsec parsec) {
		AndParsec p = new AndParsec(new ParsecObjectsSemanticAction() {

			@Override
			public Object doAction(List<Object> resultObjects) {

				// Assume.assumeNotNull(resultObjects.get(1));

				return resultObjects.get(1);
			}
		}, whiteSpace, parsec, whiteSpace);

		return p;
	}

	public AbstractParsec createParsec(String word,
			ParsecLiteralSemanticAction action) {
		WordParsec p = new WordParsec(action, word);
		return createParsecInternal(p);
	}

	public AbstractParsec createParsec(String word) {
		WordParsec p = new WordParsec(word);
		return createParsecInternal(p);
	}

	public AbstractParsec createParsec(AbstractParsec parsec) {

		return createParsecInternal(parsec);
	}

	public AbstractParsec getWhiteParsec() {

		return whiteCharParsec;
	}

	public static WordBuilder get(char CR, Character... whiteStrs) {

		final WordBuilder wb = new WordBuilder();

		wb.CR = CR;
		wb.whiteCharParsec.addChars(CR);

		for (Character c : whiteStrs) {
			wb.whiteCharParsec.addChars(c);

		}

		return wb;

	}

}

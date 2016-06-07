package Zeson.AZLRJ.common;

import java.util.HashMap;
import java.util.Map;

public class DefaultMemoizationTable implements MemoizationTable {

	private Map<AbstractParsec, Map<Integer, ParsedResult>> table = new HashMap<AbstractParsec, Map<Integer, ParsedResult>>();

	@Override
	public ParsedResult get(AbstractParsec parsec, Integer key) {
		Map<Integer, ParsedResult> oneParsecTable = table.get(parsec);
		if (oneParsecTable == null) {
			return null;
		} else {
			return oneParsecTable.get(key);
		}

	}

	@Override
	public ParsedResult put(AbstractParsec parsec, Integer key,
			ParsedResult value) {

		Map<Integer, ParsedResult> oneParsecTable = table.get(parsec);

		if (oneParsecTable == null) {
			Map<Integer, ParsedResult> t = new HashMap<Integer, ParsedResult>();
			table.put(parsec, t);
			return t.put(key, value);
		} else {
			return oneParsecTable.put(key, value);
		}

	}
}

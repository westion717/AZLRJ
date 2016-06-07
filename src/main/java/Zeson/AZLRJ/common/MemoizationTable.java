package Zeson.AZLRJ.common;

public interface MemoizationTable {

	ParsedResult get(AbstractParsec parsec, Integer key);

	ParsedResult put(AbstractParsec parsec, Integer key, ParsedResult value);
}

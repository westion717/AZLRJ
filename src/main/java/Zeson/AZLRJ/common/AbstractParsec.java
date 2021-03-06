package Zeson.AZLRJ.common;

import Zeson.AZLRJ.common.Source;

public abstract class AbstractParsec implements IParsec {

	public static FailHandler handler;

	public static MemoizationTable memoizationTable = new DefaultMemoizationTable();

	public static boolean isMemoization = true;

	@Override
	public ParsedResult parse(Source inputString) {

		int pos = inputString.pos;

		if (isMemoization) {
			ParsedResult storedResult = memoizationTable.get(this, pos);
			if (storedResult != null) {
				return storedResult;
			}
		}

		Source dup = inputString.fork();

		ParsedResult result = this.internalParse(dup);
		if (result.getClass() == FailedResult.class) {

			if (handler != null) {
				handler.failHandle((FailedResult) result);
			}
		} else {
			/*try {
				Thread.sleep(0,1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		if (isMemoization) {
			memoizationTable.put(this, pos, result);

		}
		return result;
	}

	public abstract ParsedResult internalParse(Source inputString);

	public static String getFailMessage() {
		if (handler != null) {
			return handler.getErrorMsg();
		}
		return null;
	}

	public static void reset() {
		memoizationTable = new DefaultMemoizationTable();
	}

}

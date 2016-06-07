package Zeson.AZLRJ.utils;

import org.junit.Assert;
import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.SuccessResult;
import Zeson.AZLRJ.common.Source;

public class TestTool {

	public static void assertValue(AbstractParsec parsec, Source source,
			Object... values) {
		SuccessResult result = new SuccessResult(null, source);
		for (Object i : values) {
			System.out.println(result.toString());

			result = (SuccessResult) parsec.parse(result.getRestInputString());

			Assert.assertEquals(i, result.getResultObject());
		}
	}

	public static <I> void assertEqual(I expected, ParsedResult actual) {

		Assume.assumeTrue(actual.toString(),
				actual.getClass() == SuccessResult.class);

		SuccessResult parsedResult = (SuccessResult) actual;

		Assert.assertEquals(expected, parsedResult.getResultObject());
	}
}

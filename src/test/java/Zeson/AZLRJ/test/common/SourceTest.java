package Zeson.AZLRJ.test.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Zeson.AZLRJ.common.EOFStream;
import Zeson.AZLRJ.common.Source;

/**
 * base test configuration
 * 
 * */

public class SourceTest extends BaseTest {

	private static String text = "This this \tis a txt\n\r";

	@BeforeClass
	public static void init() {
		sb.append(text);
	}

	@Before
	public void unset() {
		source = new Source(sb);
	}

	@Test
	public void getCurrentChar() throws EOFStream {
		Assert.assertEquals('T', source.getCurrentChar());
	}

	@Test
	public void peek() throws EOFStream {
		source.peek(3);
		Assert.assertEquals('s', source.getCurrentChar());
	}

	@Test
	public void moveTo() throws EOFStream {
		source.moveTo(7);
		Assert.assertEquals('i', source.getCurrentChar());
	}

	@Test
	public void getCurrentLengthStr() throws EOFStream {
		source.moveTo(7);
		Assert.assertEquals("is \t", source.getCurrentLengthStr(4));
	}

	@Test
	public void index() {
		Assert.assertEquals('\r', sb.charAt(text.length() - 1));
	}

}

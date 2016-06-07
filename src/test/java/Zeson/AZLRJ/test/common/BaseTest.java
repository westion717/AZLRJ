package Zeson.AZLRJ.test.common;

import Zeson.AZLRJ.common.Source;

/**
 * base test configuration
 * 
 * */
public class BaseTest {

	/*
	 * protected File file = null; protected InputStream in = null; protected
	 * Source source = null; protected StringBuffer sb = null;
	 * 
	 * protected void initBase(String testNo, String charset) { Path path =
	 * Paths.get(getClass().getResource("/test/" + testNo) .getPath()); byte[]
	 * data = null; try { data = Files.readAllBytes(path); } catch (IOException
	 * e) { e.printStackTrace(); } sb = new StringBuffer(); sb.append(data); }
	 */

	protected static StringBuffer sb = new StringBuffer();

	protected static Source source = new Source(sb);
}

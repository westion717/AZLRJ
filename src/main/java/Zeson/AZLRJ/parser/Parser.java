package Zeson.AZLRJ.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Assume;

import Zeson.AZLRJ.common.AbstractParsec;
import Zeson.AZLRJ.common.FailedResult;
import Zeson.AZLRJ.common.ParsedResult;
import Zeson.AZLRJ.common.Source;
import Zeson.AZLRJ.common.SuccessResult;

public final class Parser {

	private Source source;
	private AbstractParsec start;

	public Parser(Source source, AbstractParsec start) {
		super();
		this.source = source;
		this.start = start;
	}

	public Parser(File file, AbstractParsec start) {
		super();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(new String(Files.readAllBytes(file.toPath()), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("can not read from the file"
					+ file.getName(), e);
		}
		this.source = new Source(sb);
		this.start = start;
	}

	public boolean parse() {
		ParsedResult parsedResult = start.parse(source);

		if (parsedResult.getClass() == FailedResult.class) {

			return false;
		}
		Assume.assumeTrue(parsedResult.getClass() == SuccessResult.class);
		SuccessResult successResult = (SuccessResult) parsedResult;
		if (!successResult.getRestInputString().isEOF()) {
			return false;
		}
		return true;

	}

}

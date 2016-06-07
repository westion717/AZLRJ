package Zeson.AZLRJ.common;

import java.util.Locale;

import javax.tools.Diagnostic;

public final class FailedResult extends ParsedResult implements
		Diagnostic<IParsec> {

	public static class ErrorCode {
		public final static String EOF = "end of file";
	}

	private Kind kind;
	private IParsec errorSource;
	private long position;
	private long startPosition;
	private long endPosition;
	private long lineNumber;
	private long columnNumber;
	private String code;
	private String msg;

	public FailedResult(javax.tools.Diagnostic.Kind kind, IParsec errorSource,
			long position, long startPosition, long endPosition,
			long lineNumber, long columnNumber, String code, String msg) {
		super();
		this.kind = kind;
		this.errorSource = errorSource;
		this.position = position;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.code = code;
		this.msg = msg;

	}

	public FailedResult() {
		super();
		this.position = -1;

	}

	@Override
	public javax.tools.Diagnostic.Kind getKind() {
		return kind;
	}

	@Override
	public IParsec getSource() {
		return errorSource;
	}

	@Override
	public long getPosition() {

		return position;
	}

	@Override
	public long getStartPosition() {

		return startPosition;
	}

	@Override
	public long getEndPosition() {

		return endPosition;
	}

	@Override
	public long getLineNumber() {

		return lineNumber;
	}

	@Override
	public long getColumnNumber() {

		return columnNumber;
	}

	@Override
	public String getCode() {

		return code;
	}

	@Override
	public String getMessage(Locale locale) {
		return msg;
	}

	@Override
	public String toString() {
		return "FailedResult [kind=" + kind + ", errorSource=" + errorSource
				+ ", position=" + position + ", startPosition=" + startPosition
				+ ", endPosition=" + endPosition + ", lineNumber=" + lineNumber
				+ ", columnNumber=" + columnNumber + ", code=" + code
				+ ", msg=" + msg + "]";
	}

}

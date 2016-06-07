package Zeson.AZLRJ.common;

public class Source {

	public int pos;
	public int line;
	public int column;
	private StringBuffer sb;

	private String lastString;
	private char lastChar;

	public Source(StringBuffer sb) {
		super();
		this.sb = sb;
		this.pos = 0;
		this.line = 1;
		this.column = 1;
	}

	private Source(int line, int column, int pos, StringBuffer sb) {
		this.sb = sb;
		this.line = line;
		this.column = column;
		this.pos = pos;
	}

	public void moveTo(int pos) {
		this.pos = pos;
	}

	public void peek(int num) {
		this.pos += num;
	}

	private char index(int pos) throws EOFStream {
		if (pos >= 0 && pos < sb.length()) {

			lastString = sb.charAt(pos) + "";
			return sb.charAt(pos);
		}
		throw new EOFStream();
	}

	public char getCurrentChar() throws EOFStream {
		return this.index(this.pos);
	}

	public char getPreChar() throws EOFStream {
		return this.index(this.pos - 1);
	}

	public char getLastChar() {
		return lastChar;
	}

	public String getCurrentLengthStr(int length) throws EOFStream {

		if (pos >= 0 && pos + length <= sb.length()) {
			lastString = this.sb.substring(this.pos, this.pos + length);
			return new String(lastString);
		}
		throw new EOFStream();
	}

	public String getLastString() {
		return lastString;
	}

	public boolean isEOF() {
		return pos >= sb.length();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + pos;
		result = prime * result + ((sb == null) ? 0 : sb.hashCode());
		return result;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Source other = (Source) obj;
		if (pos != other.pos)
			return false;
		if (sb == null) {
			if (other.sb != null)
				return false;
		} else if (!sb.equals(other.sb))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Source [pos=" + pos + ", line=" + line + ", column=" + column
				+ "]";
	}

	public Source fork() {
		return new Source(this.line, this.column, this.pos, this.sb);
	}

}
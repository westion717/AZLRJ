package Zeson.AZLRJ.common;

public class DefaultFailHandler implements FailHandler {
	protected FailedResult failMsg;

	// private Set<IParsec> ignoreFailSources = new HashSet<>();

	protected IParsec ignoreFailParsec;

	public DefaultFailHandler(IParsec ignoreFailParsec) {
		super();
		this.ignoreFailParsec = ignoreFailParsec;
	}

	@Override
	public void failHandle(FailedResult failedResult) {
		if (ignoreFailParsec == failedResult.getSource()) {
			return;
		}

		if (failMsg == null) {
			failMsg = failedResult;
		} else if (failedResult.getPosition() >= failMsg.getPosition()) {
			failMsg = failedResult;
		}

	}

	@Override
	public String getErrorMsg() {
		return failMsg.toString();
	}

	public void reset() {
		failMsg = null;
	}

}

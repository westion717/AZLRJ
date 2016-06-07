package Zeson.AZLRJ.common;

public interface FailHandler {

	void failHandle(FailedResult failedResult);

	String getErrorMsg();

}

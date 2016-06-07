package Zeson.AZLRJ.common;

public final class SuccessResult extends ParsedResult {

	Object resultObject;
	Source restInputString;

	public SuccessResult(Object resultObject, Source restInputString) {
		super();
		this.resultObject = resultObject;
		this.restInputString = restInputString;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public Source getRestInputString() {
		return restInputString;
	}

	public void setRestInputString(Source restInputString) {
		this.restInputString = restInputString;
	}

	@Override
	public String toString() {
		return "ParsedResult [resultObject=" + resultObject
				+ ", restInputString=" + restInputString + "]";
	}

}

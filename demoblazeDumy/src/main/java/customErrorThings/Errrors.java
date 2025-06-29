package customErrorThings;

public class Errrors {
	/**
	 * the vbariable that stores the cusetom message
	 */
	private static String capturedMsg = null;

	private Errrors() {
		// this is to ensure that no object can be crated
	}

	/** add the error message and store it.This causes the loss of Error message stored previously(if not null) */
	public static void setErrorMessages(String msg) {
		capturedMsg = msg;
	}

	/**
	 * @return String if not null,returns the last message stored.else, returns no
	 *         error message.
	 * @implNote- clears the message stored after this.
	 */
	public static String getErrorMsg() {
		try {
			return (capturedMsg == null) ? "there is no error message" : capturedMsg;
		} finally {
			capturedMsg = null;// restoring its original state
		}
	}

	/** clears the stored messages */
	public static void clearStoredMessage() {
		capturedMsg = null;
	}

	/**
	 * @return if there is there is a message stored or not.if true message is
	 *         present.if false then no.
	 */
	public boolean messageIsPresent() {
		return (capturedMsg != null) ? true : false;
	}

}

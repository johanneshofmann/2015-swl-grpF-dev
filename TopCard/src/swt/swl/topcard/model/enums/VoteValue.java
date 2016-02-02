package swt.swl.topcard.model.enums;

/**
 * 
 * TODO: javadoc
 * 
 * @author -steve-
 *
 */
public enum VoteValue {

	ALL(3), YES(1), NO(0), UNKNOWN(2);
	private final int value;

	VoteValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

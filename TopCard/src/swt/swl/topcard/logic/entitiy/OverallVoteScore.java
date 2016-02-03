package swt.swl.topcard.logic.entitiy;

/**
 * 
 * A simple Data-Object that holds the calculated vote result of a requirement
 * card.
 * 
 * @author -steve-
 *
 */
public interface OverallVoteScore {

	/**
	 * 
	 * Simple getter.
	 * 
	 * @return
	 */
	double getTotalAmount();

	/**
	 * 
	 * Simple getter.
	 * 
	 * @return
	 */
	int getYes();

	/**
	 * 
	 * Simple getter.
	 * 
	 * @return
	 */
	int getNo();

	/**
	 * 
	 * Simple getter.
	 * 
	 * @return
	 */
	int getDontKnow();

}

package swt.swl.topcard.logic.entitiy;

/**
 * 
 * @author swt-041649
 *
 */
public interface SubmittedVoteSimple {

	/**
	 * 
	 * The interface for the classes' Builder.
	 * 
	 * @author -steve-
	 *
	 */
	public interface VoteBuilder {

		public VoteBuilder setID(int ID);

		public VoteBuilder setrqID(int rqID);

		public VoteBuilder setuserID(int userID);

		public VoteBuilder setOverallVoteScore(OverallVoteScore[] voteScores);

		public VoteBuilder setDescriptionPrecise(double value);

		public VoteBuilder setDescriptionUnderstandable(double value);

		public VoteBuilder setDescriptionCorrect(double value);

		public VoteBuilder setDescriptionComplete(double value);

		public VoteBuilder setDescriptionAtomic(double value);

		public VoteBuilder setRationalePrecise(double value);

		public VoteBuilder setRationaleUnderstandable(double value);

		public VoteBuilder setRationaleTraceable(double value);

		public VoteBuilder setRationaleComplete(double value);

		public VoteBuilder setRationaleConsistent(double value);

		public VoteBuilder setFitCriterionComplete(double d);

		/**
		 * 
		 * This method is called, when the entity is configured correctly.
		 * 
		 * @return
		 */
		public SubmittedVoteSimple buildVote();
	}

	/**
	 * 
	 * Simple getters..
	 * 
	 * @return
	 */
	public int getID();

	double getDescriptionPrecise();

	double getDescriptionUnderstandable();

	double getRationalePrecise();

	double getDescriptionCorrect();

	double getDescriptionComplete();

	double getDescriptionAtomic();

	double getRationaleUnderstandable();

	double getRationaleTraceable();

	double getRationaleComplete();

	double getRationaleConsistent();

	double getFitCriterionComplete();

	void setOverallVoteScore(String[] overallVoteScore);

	public String[] getOverallVoteScore();
}

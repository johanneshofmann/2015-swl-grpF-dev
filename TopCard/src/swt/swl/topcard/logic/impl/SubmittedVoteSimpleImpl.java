package swt.swl.topcard.logic.impl;

import swt.swl.topcard.logic.OverallVoteScore;
import swt.swl.topcard.logic.SubmittedVoteSimple;
import swt.swl.topcard.model.search.VoteValue;

/**
 * 0-> Don't know; 1-> Yes; 2-> ;
 * 
 * @author swt-041649
 *
 */
public class SubmittedVoteSimpleImpl implements SubmittedVoteSimple {

	private int ID;
	private int rqID;
	private int userID;

	private String[] overallVoteScore;

	// 1-10
	private double descriptionPrecise, descriptionUnderstandable, rationalePrecise, rationaleUnderstandable;

	// Yes/No/Don't know
	private VoteValue descriptionCorrect, descriptionComplete, descriptionAtomic, rationaleTraceable, rationaleComplete,
			rationaleConsistent, fitCriterionComplete;

	private SubmittedVoteSimpleImpl(int rqID) {
		this.rqID = rqID;
	}

	private SubmittedVoteSimpleImpl() {

	}

	public static class VoteBuilderImpl implements VoteBuilder {

		private SubmittedVoteSimpleImpl vote;

		public VoteBuilderImpl() {
			vote = new SubmittedVoteSimpleImpl();
		}

		public VoteBuilderImpl(int ID) {
			vote = new SubmittedVoteSimpleImpl();
			setrqID(ID);
		}

		@Override
		public VoteBuilder setID(int ID) {
			vote.ID = ID;
			return this;
		}

		@Override
		public VoteBuilder setrqID(int rqID) {
			vote.rqID = rqID;
			return this;
		}

		@Override
		public VoteBuilder setuserID(int userID) {
			vote.userID = userID;
			return this;
		}

		@Override
		public VoteBuilder setOverallVoteScore(OverallVoteScore[] voteScores) {

			double[] voteScoresAsDouble = new double[11];

			vote.overallVoteScore = new String[11];

			for (int i = 0; i < 4; i++) {

				vote.overallVoteScore[i] = "" + voteScores[i].getTotalAmount();
				voteScoresAsDouble[i] = voteScores[i].getTotalAmount();
			}

			for (int i = 4; i < voteScores.length; i++) {

				vote.overallVoteScore[i] = overallVoteScoreToString(voteScores[i]);
				voteScoresAsDouble[i] = voteScores[i].getYes();
			}

			return this.setDescriptionPrecise(voteScoresAsDouble[0]).setDescriptionUnderstandable(voteScoresAsDouble[1])
					.setRationalePrecise(voteScoresAsDouble[2]).setRationaleUnderstandable(voteScoresAsDouble[3])
					.setDescriptionCorrect(voteScoresAsDouble[4]).setDescriptionComplete(voteScoresAsDouble[5])
					.setDescriptionAtomic(voteScoresAsDouble[6]).setRationaleTraceable(voteScoresAsDouble[7])
					.setRationaleComplete(voteScoresAsDouble[8]).setRationaleConsistent(voteScoresAsDouble[9])
					.setFitCriterionComplete(voteScoresAsDouble[10]);
		}

		@Override
		public VoteBuilder setDescriptionPrecise(double value) {
			vote.descriptionPrecise = value;
			return this;
		}

		@Override
		public VoteBuilder setDescriptionUnderstandable(double value) {
			vote.descriptionUnderstandable = value;
			return this;
		}

		@Override
		public VoteBuilder setDescriptionCorrect(double value) {
			vote.descriptionCorrect = getVoteValueType(value);
			return this;
		}

		@Override
		public VoteBuilder setDescriptionComplete(double value) {
			vote.descriptionComplete = getVoteValueType(value);
			return this;
		}

		@Override
		public VoteBuilder setDescriptionAtomic(double value) {
			vote.descriptionAtomic = getVoteValueType(value);
			return this;
		}

		@Override
		public VoteBuilder setRationalePrecise(double value) {
			vote.rationalePrecise = value;
			return this;
		}

		@Override
		public VoteBuilder setRationaleUnderstandable(double value) {
			vote.rationaleUnderstandable = value;
			return this;
		}

		@Override
		public VoteBuilder setRationaleTraceable(double value) {
			vote.rationaleTraceable = getVoteValueType(value);
			return this;
		}

		@Override
		public VoteBuilder setRationaleComplete(double value) {
			vote.rationaleComplete = getVoteValueType(value);
			return this;
		}

		@Override
		public VoteBuilder setRationaleConsistent(double value) {
			vote.rationaleConsistent = getVoteValueType(value);
			return this;
		}

		@Override
		public VoteBuilder setFitCriterionComplete(double value) {

			vote.fitCriterionComplete = getVoteValueType(value);
			return this;
		}

		@Override
		public SubmittedVoteSimple buildVote() {
			return vote;
		}

	}

	private static String overallVoteScoreToString(OverallVoteScore overallVoteScore) {

		double amount = overallVoteScore.getTotalAmount();
		return (overallVoteScore.getYes() + "/" + (int) amount + " Y, " + overallVoteScore.getNo() + "/" + (int) amount
				+ " N, " + overallVoteScore.getDontKnow() + "/" + (int) amount + " ?");
	}

	public static VoteValue getVoteValueType(double doubleValue) {

		int value = (int) doubleValue;
		if (value == 0) {
			return VoteValue.NO;

		} else if (value == 1) {
			return VoteValue.YES;
		} else {
			return (value > 2 ? VoteValue.ALL : VoteValue.UNKNOWN);
		}
	}

	// Getters :

	public int getID() {
		return ID;
	}

	public int getUserID() {
		return userID;
	}

	public double getDescriptionPrecise() {
		return descriptionPrecise;
	}

	public double getDescriptionUnderstandable() {
		return descriptionUnderstandable;
	}

	public double getDescriptionCorrect() {
		return descriptionCorrect.getValue();
	}

	public double getDescriptionComplete() {
		return descriptionComplete.getValue();
	}

	public double getDescriptionAtomic() {
		return descriptionAtomic.getValue();
	}

	public double getRationalePrecise() {
		return rationalePrecise;
	}

	public double getRationaleUnderstandable() {
		return rationaleUnderstandable;
	}

	public double getRationaleTraceable() {
		return rationaleTraceable.getValue();
	}

	public double getRationaleComplete() {
		return rationaleComplete.getValue();
	}

	public double getRationaleConsistent() {
		return rationaleConsistent.getValue();
	}

	public double getFitCriterionComplete() {
		return fitCriterionComplete.getValue();
	}

	public int getRqID() {
		return rqID;
	}

	public String[] getOverallVoteScore() {
		return overallVoteScore;
	}

	public String toString() {
		return "ID= " + ID + ", RQID= " + rqID + ", UserID=" + userID + ", descriptionPrecise=" + descriptionPrecise
				+ ", descriptionUnderstandable=" + descriptionUnderstandable + ", rationalePrecise=" + rationalePrecise
				+ ", rationaleUnderstandable=" + rationaleUnderstandable + ", descriptionCorrect=" + descriptionCorrect
				+ ", descriptionComplete" + descriptionComplete + ", descriptionAtomic=" + descriptionAtomic
				+ ", rationaleTraceable=" + rationaleTraceable + ", rationaleComplete" + rationaleComplete
				+ ", rationaleConsistent=" + rationaleConsistent + ", fitCriterionCorrect=" + fitCriterionComplete;
	}

	@Override
	public void setOverallVoteScore(String[] overallVoteScore) {
		// TODO Auto-generated method stub

	}

}

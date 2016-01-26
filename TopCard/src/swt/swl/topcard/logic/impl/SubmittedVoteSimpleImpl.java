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
	private final int rqID;
	private int userID;

	private String[] overallVoteScore;

	// 1-10
	private double descriptionPrecise, descriptionUnderstandable, rationalePrecise, rationaleUnderstandable;

	// Yes/No/Don't know
	private VoteValue descriptionCorrect, descriptionComplete, descriptionAtomic, rationaleTraceable, rationaleComplete,
			rationaleConsistent, fitCriterionComplete;

	public SubmittedVoteSimpleImpl(int rqID) {
		this.rqID = rqID;
	}

	public SubmittedVoteSimpleImpl(double descriptionPrecise, double descriptionUnderstandable,
			double descriptionCorrect, double descriptionComplete, double descriptionAtomic, double rationalePrecise,
			double rationaleUnderstandable, double rationaleTraceable, double rationaleComplete,
			double rationaleConsistent, double fitCriterionCorrect) {
		this.rqID = 0;
		this.descriptionPrecise = descriptionPrecise;
		this.descriptionUnderstandable = descriptionUnderstandable;
		this.descriptionCorrect = getVoteValueType(descriptionCorrect);
		this.descriptionComplete = getVoteValueType(descriptionComplete);
		this.descriptionAtomic = getVoteValueType(descriptionAtomic);
		this.rationalePrecise = rationalePrecise;
		this.rationaleUnderstandable = rationaleUnderstandable;
		this.rationaleTraceable = getVoteValueType(rationaleTraceable);
		this.rationaleComplete = getVoteValueType(rationaleComplete);
		this.rationaleConsistent = getVoteValueType(rationaleConsistent);
		this.fitCriterionComplete = getVoteValueType(fitCriterionCorrect);

	}

	public SubmittedVoteSimpleImpl(int ID, int rqID, int userID, int descriptionPrecise, int descriptionUnderstandable,
			int descriptionCorrect, int descriptionComplete, int descriptionAtomic, int rationalePrecise,
			int rationaleUnderstandable, int rationaleTraceable, int rationaleComplete, int rationaleConsistent,
			int fitCriterionCorrect) {
		this.ID = ID;
		this.rqID = rqID;
		this.userID = userID;
		this.descriptionPrecise = descriptionPrecise;
		this.descriptionUnderstandable = descriptionUnderstandable;
		this.descriptionCorrect = getVoteValueType(descriptionCorrect);
		this.descriptionComplete = getVoteValueType(descriptionComplete);
		this.descriptionAtomic = getVoteValueType(descriptionAtomic);
		this.rationalePrecise = rationalePrecise;
		this.rationaleUnderstandable = rationaleUnderstandable;
		this.rationaleTraceable = getVoteValueType(rationaleTraceable);
		this.rationaleComplete = getVoteValueType(rationaleComplete);
		this.rationaleConsistent = getVoteValueType(rationaleConsistent);
		this.fitCriterionComplete = getVoteValueType(fitCriterionCorrect);

	}

	public SubmittedVoteSimpleImpl(OverallVoteScore[] voteScores) {

		double[] voteScoresAsDouble = new double[11];

		overallVoteScore = new String[11];

		for (int i = 0; i < 4; i++) {

			overallVoteScore[i] = "" + voteScores[i].getTotalAmount();
			voteScoresAsDouble[i] = voteScores[i].getTotalAmount();
		}

		for (int i = 4; i < overallVoteScore.length; i++) {

			overallVoteScore[i] = overallVoteScoreToString(voteScores[i]);
			voteScoresAsDouble[i] = voteScores[i].getYes();
		}

		this.rqID = 0;

		this.descriptionPrecise = voteScoresAsDouble[0];
		this.descriptionUnderstandable = voteScoresAsDouble[1];
		this.rationalePrecise = voteScoresAsDouble[2];
		this.rationaleUnderstandable = voteScoresAsDouble[3];

		this.descriptionCorrect = getVoteValueType(voteScoresAsDouble[4]);
		this.descriptionComplete = getVoteValueType(voteScoresAsDouble[5]);
		this.descriptionAtomic = getVoteValueType(voteScoresAsDouble[6]);
		this.rationaleTraceable = getVoteValueType(voteScoresAsDouble[7]);
		this.rationaleComplete = getVoteValueType(voteScoresAsDouble[8]);
		this.rationaleConsistent = getVoteValueType(voteScoresAsDouble[9]);
		this.fitCriterionComplete = getVoteValueType(voteScoresAsDouble[10]);
	}

	private String overallVoteScoreToString(OverallVoteScore overallVoteScore) {

		double amount = overallVoteScore.getTotalAmount();
		return (overallVoteScore.getYes() + "/" + (int) amount + " Y, " + overallVoteScore.getNo() + "/" + (int) amount
				+ " N, " + overallVoteScore.getDontKnow() + "/" + (int) amount + " ?");
	}

	public VoteValue getVoteValueType(double doubleValue) {

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

	public void setOverallVoteScore(String[] overallVoteScore) {
		this.overallVoteScore = overallVoteScore;
	}

	public String toString() {
		return "ID= " + ID + ", RQID= " + rqID + ", UserID=" + userID + ", descriptionPrecise=" + descriptionPrecise
				+ ", descriptionUnderstandable=" + descriptionUnderstandable + ", rationalePrecise=" + rationalePrecise
				+ ", rationaleUnderstandable=" + rationaleUnderstandable + ", descriptionCorrect=" + descriptionCorrect
				+ ", descriptionComplete" + descriptionComplete + ", descriptionAtomic=" + descriptionAtomic
				+ ", rationaleTraceable=" + rationaleTraceable + ", rationaleComplete" + rationaleComplete
				+ ", rationaleConsistent=" + rationaleConsistent + ", fitCriterionCorrect=" + fitCriterionComplete;
	}

}

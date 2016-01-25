package swt.swl.topcard.logic.impl;

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

	public SubmittedVoteSimpleImpl(String[] voteScore) {
		this.rqID = 0;
		this.overallVoteScore = voteScore;
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

	public String[] getOverallVoteScores() {
		return overallVoteScore;
	}

	public void setOverallVoteScores(String[] overallVoteScores) {
		this.overallVoteScore = overallVoteScores;
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

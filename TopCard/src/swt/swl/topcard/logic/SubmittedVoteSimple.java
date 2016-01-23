package swt.swl.topcard.logic;

/**
 * 0-> Don't know; 1-> Yes; 2-> ;
 * 
 * @author swt-041649
 *
 */
public class SubmittedVoteSimple {

	private int ID;
	private final int rqID;
	private int userID;

	// 1-10
	private double descriptionPrecise;
	private double descriptionUnderstandable;
	private double rationalePrecise;
	private double rationaleUnderstandable;

	// Yes/No/Don't know
	private double descriptionCorrect;
	private double descriptionComplete;
	private double descriptionAtomic;
	private double rationaleTraceable;
	private double rationaleComplete;
	private double rationaleConsistent;
	private double fitCriterionCorrect;

	public SubmittedVoteSimple(int rqID) {
		this.rqID = rqID;
	}

	public SubmittedVoteSimple(double descriptionPrecise, double descriptionUnderstandable, double descriptionCorrect,
			double descriptionComplete, double descriptionAtomic, double rationalePrecise,
			double rationaleUnderstandable, double rationaleTraceable, double rationaleComplete,
			double rationaleConsistent, double fitCriterionCorrect) {
		this.rqID = 0;
		this.descriptionPrecise = descriptionPrecise;
		this.descriptionUnderstandable = descriptionUnderstandable;
		this.descriptionCorrect = descriptionCorrect;
		this.descriptionComplete = descriptionComplete;
		this.descriptionAtomic = descriptionAtomic;
		this.rationalePrecise = rationalePrecise;
		this.rationaleUnderstandable = rationaleUnderstandable;
		this.rationaleTraceable = rationaleTraceable;
		this.rationaleComplete = rationaleComplete;
		this.rationaleConsistent = rationaleConsistent;
		this.fitCriterionCorrect = fitCriterionCorrect;
	}

	public SubmittedVoteSimple(int ID, int rqID, int userID, double descriptionPrecise,
			double descriptionUnderstandable, double descriptionCorrect, double descriptionComplete,
			int descriptionAtomic, double rationalePrecise, double rationaleUnderstandable, double rationaleTraceable,
			double rationaleComplete, double rationaleConsistent, double fitCriterionCorrect) {
		this.ID = ID;
		this.rqID = rqID;
		this.userID = userID;
		this.descriptionPrecise = descriptionPrecise;
		this.descriptionUnderstandable = descriptionUnderstandable;
		this.descriptionCorrect = descriptionCorrect;
		this.descriptionComplete = descriptionComplete;
		this.descriptionAtomic = descriptionAtomic;
		this.rationalePrecise = rationalePrecise;
		this.rationaleUnderstandable = rationaleUnderstandable;
		this.rationaleTraceable = rationaleTraceable;
		this.rationaleComplete = rationaleComplete;
		this.rationaleConsistent = rationaleConsistent;
		this.fitCriterionCorrect = fitCriterionCorrect;
	}

	// Getters & Setters:

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public double getDescriptionPrecise() {
		return descriptionPrecise;
	}

	public void setDescriptionPrecise(int descriptionPrecise) {
		this.descriptionPrecise = descriptionPrecise;
	}

	public double getDescriptionUnderstandable() {
		return descriptionUnderstandable;
	}

	public void setDescriptionUnderstandable(int descriptionUnderstandable) {
		this.descriptionUnderstandable = descriptionUnderstandable;
	}

	public double getDescriptionCorrect() {
		return descriptionCorrect;
	}

	public void setDescriptionCorrect(int descriptionCorrect) {
		this.descriptionCorrect = descriptionCorrect;
	}

	public double getDescriptionComplete() {
		return descriptionComplete;
	}

	public void setDescriptionComplete(int descriptionComplete) {
		this.descriptionComplete = descriptionComplete;
	}

	public double getDescriptionAtomic() {
		return descriptionAtomic;
	}

	public void setDescriptionAtomic(int descriptionAtomic) {
		this.descriptionAtomic = descriptionAtomic;
	}

	public double getRationalePrecise() {
		return rationalePrecise;
	}

	public void setRationalePrecise(int rationalePrecise) {
		this.rationalePrecise = rationalePrecise;
	}

	public double getRationaleUnderstandable() {
		return rationaleUnderstandable;
	}

	public void setRationaleUnderstandable(int rationaleUnderstandable) {
		this.rationaleUnderstandable = rationaleUnderstandable;
	}

	public double getRationaleTraceable() {
		return rationaleTraceable;
	}

	public void setRationaleTraceable(int rationaleTraceable) {
		this.rationaleTraceable = rationaleTraceable;
	}

	public double getRationaleComplete() {
		return rationaleComplete;
	}

	public void setRationaleComplete(int rationaleComplete) {
		this.rationaleComplete = rationaleComplete;
	}

	public double getRationaleConsistent() {
		return rationaleConsistent;
	}

	public void setRationaleConsistent(int rationaleConsistent) {
		this.rationaleConsistent = rationaleConsistent;
	}

	public double getFitCriterionCorrect() {
		return fitCriterionCorrect;
	}

	public void setFitCriterionCorrect(int fitCriterionCorrect) {
		this.fitCriterionCorrect = fitCriterionCorrect;
	}

	public int getRqID() {
		return rqID;
	}

	public String toString() {
		return "ID= " + ID + ", RQID= " + rqID + ", UserID=" + userID + ", descriptionPrecise=" + descriptionPrecise
				+ ", descriptionUnderstandable=" + descriptionUnderstandable + ", rationalePrecise=" + rationalePrecise
				+ ", rationaleUnderstandable=" + rationaleUnderstandable + ", descriptionCorrect=" + descriptionCorrect
				+ ", descriptionComplete" + descriptionComplete + ", descriptionAtomic=" + descriptionAtomic
				+ ", rationaleTraceable=" + rationaleTraceable + ", rationaleComplete" + rationaleComplete
				+ ", rationaleConsistent=" + rationaleConsistent + ", fitCriterionCorrect=" + fitCriterionCorrect;
	}

}

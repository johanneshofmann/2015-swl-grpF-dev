package swt.swl.topcard.logic;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequirementCardSimple {

	private final SimpleIntegerProperty ID, minorVersion, majorVersion, ownerID, rqID, isFrozen;
	private final SimpleStringProperty title, ownerName, modules, description, rationale, source, userStories,
			supportingMaterials, fitCriterion, lastModifiedAt;
	private Timestamp createdAt;

	private SubmittedVoteSimple submittedVote;

	public RequirementCardSimple(int ID, String title, int minorVersion, int majorVersion, int ownerID,
			String ownerName, int rqID, String modules, String description, String rationale, String source,
			String userStories, String supportingMaterials, String fitCriterion, int isFrozen, Timestamp createdAt,
			String lastModifiedAt) {

		super();

		this.ID = new SimpleIntegerProperty(ID);
		this.title = new SimpleStringProperty(title);
		this.majorVersion = new SimpleIntegerProperty(majorVersion);
		this.minorVersion = new SimpleIntegerProperty(minorVersion);
		this.ownerID = new SimpleIntegerProperty(ownerID);
		this.ownerName = new SimpleStringProperty(ownerName);
		this.rqID = new SimpleIntegerProperty(rqID);
		this.modules = new SimpleStringProperty(modules);
		this.description = new SimpleStringProperty(description);
		this.rationale = new SimpleStringProperty(rationale);
		this.source = new SimpleStringProperty(source);
		this.userStories = new SimpleStringProperty(userStories);
		this.supportingMaterials = new SimpleStringProperty(supportingMaterials);
		this.fitCriterion = new SimpleStringProperty(fitCriterion);
		this.isFrozen = new SimpleIntegerProperty(isFrozen);
		this.createdAt = createdAt;
		this.lastModifiedAt = new SimpleStringProperty(lastModifiedAt);
	}

	// Getters & Setters:

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public int getMinorVersion() {
		return minorVersion.get();
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion.set(minorVersion);
	}

	public int getMajorVersion() {
		return majorVersion.get();
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion.set(majorVersion);
	}

	public int getOwnerID() {
		return ownerID.get();
	}

	public void setOwnerID(int ownerID) {
		this.ownerID.set(ownerID);
	}

	public String getOwnerName() {
		return ownerName.get();
	}

	public void setOwnerName(String ownerName) {
		this.ownerName.set(ownerName);
	}

	public int getRqID() {
		return rqID.get();
	}

	public void setRqID(int rqID) {
		this.rqID.set(rqID);
	}

	public String getModules() {
		return modules.get();
	}

	public void setModules(String modules) {
		this.modules.set(modules);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public String getRationale() {
		return rationale.get();
	}

	public void setRationale(String rationale) {
		this.rationale.set(rationale);
	}

	public String getSource() {
		return source.get();
	}

	public void setSource(String source) {
		this.source.set(source);
	}

	public String getSupportingMaterials() {
		return supportingMaterials.get();
	}

	public void setSupportingMaterials(String supportingMaterials) {
		this.supportingMaterials.set(supportingMaterials);
	}

	public String getFitCriterion() {
		return fitCriterion.get();
	}

	public void setFitCriterion(String fitCriterion) {
		this.fitCriterion.set(fitCriterion);
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedAt() {
		return lastModifiedAt.get();
	}

	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt.set(lastModifiedAt);
	}

	public int getID() {
		return ID.get();
	}

	/**
	 * @return the submittedVote
	 */
	public SubmittedVoteSimple getSubmittedVote() {
		return submittedVote;
	}

	/**
	 * @param submittedVote
	 *            the submittedVote to set
	 */
	public void setSubmittedVote(SubmittedVoteSimple submittedVote) {
		this.submittedVote = submittedVote;
	}

	public String getUserStories() {
		return userStories.get();
	}

	public void setUserStories(String userStories) {
		this.userStories.set(userStories);
	}

	public int getIsFrozen() {
		return isFrozen.get();
	}

}

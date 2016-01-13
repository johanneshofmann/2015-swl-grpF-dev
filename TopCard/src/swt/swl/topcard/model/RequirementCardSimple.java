package swt.swl.topcard.model;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequirementCardSimple {

	private SimpleIntegerProperty ID;
	private SimpleStringProperty title;
	private SimpleIntegerProperty minorVersion;
	private SimpleIntegerProperty majorVersion;
	private SimpleIntegerProperty ownerID;
	private SimpleStringProperty ownerName;
	private SimpleIntegerProperty rqID;
	private SimpleStringProperty description;
	private SimpleStringProperty rationale;
	private SimpleStringProperty source;
	private SimpleStringProperty supportingMaterials;
	private SimpleStringProperty fitCriterion;
	private SimpleIntegerProperty isFrozen;
	private Timestamp createdAt;
	private SimpleStringProperty lastModifiedAt;

	public RequirementCardSimple(int ID, String title, int minorVersion, int majorVersion, int ownerID,
			String ownerName, int rqID, String description, String rationale, String source, String supportingMaterials,
			String fitCriterion, int isFrozen, Timestamp createdAt, String lastModifiedAt) {
		super();
		this.ID = new SimpleIntegerProperty(ID);

		this.title = new SimpleStringProperty(title);
		this.majorVersion = new SimpleIntegerProperty(majorVersion);
		this.minorVersion = new SimpleIntegerProperty(minorVersion);
		this.ownerID = new SimpleIntegerProperty(ownerID);
		this.ownerName = new SimpleStringProperty(ownerName);
		this.rqID = new SimpleIntegerProperty(rqID);
		this.description = new SimpleStringProperty(description);
		this.rationale = new SimpleStringProperty(rationale);
		this.source = new SimpleStringProperty(source);
		this.supportingMaterials = new SimpleStringProperty(supportingMaterials);
		this.fitCriterion = new SimpleStringProperty(fitCriterion);
		this.isFrozen = new SimpleIntegerProperty(isFrozen);
		this.createdAt = createdAt;
		this.lastModifiedAt = new SimpleStringProperty(lastModifiedAt);

	}

	public RequirementCardSimple(String title) {
		this.title = new SimpleStringProperty(title);
	}

	// Getters & Setters:

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title = new SimpleStringProperty(title);
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

	public int getIsFrozen() {
		return isFrozen.get();
	}

	public void setIsFrozen(int isFrozen) {
		this.isFrozen.set(isFrozen);
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

}

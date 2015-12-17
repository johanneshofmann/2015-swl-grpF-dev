package swt.swl.topcard.model;

import java.sql.Timestamp;

public class RequirementCardSimple {

	private int ID;
	private String title;
	private int minorVersion;
	private int majorVersion;
	private int ownerID;
	private String ownerName;
	private int rqID;
	private String description;
	private String rationale;
	private String source;
	private String supportingMaterials;
	private String fitCriterion;
	private int isFrozen;
	private Timestamp createdAt;
	private String lastModifiedAt;

	public RequirementCardSimple(int ID, String title, int minorVersion, int majorVersion, int ownerID, int rqID,
			String description, String rationale, String source, String supportingMaterials, String fitCriterion,
			int isFrozen, Timestamp createdAt, String lastModifiedAt) {
		super();
		this.ID = ID;
		this.title = title;
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.ownerID = ownerID;
		this.rqID = rqID;
		this.description = description;
		this.rationale = rationale;
		this.source = source;
		this.supportingMaterials = supportingMaterials;
		this.fitCriterion = fitCriterion;
		this.isFrozen = isFrozen;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;

	}

	// Getters & Setters:

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getRqID() {
		return rqID;
	}

	public void setRqID(int rqID) {
		this.rqID = rqID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRationale() {
		return rationale;
	}

	public void setRationale(String rationale) {
		this.rationale = rationale;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSupportingMaterials() {
		return supportingMaterials;
	}

	public void setSupportingMaterials(String supportingMaterials) {
		this.supportingMaterials = supportingMaterials;
	}

	public String getFitCriterion() {
		return fitCriterion;
	}

	public void setFitCriterion(String fitCriterion) {
		this.fitCriterion = fitCriterion;
	}

	public int getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(int isFrozen) {
		this.isFrozen = isFrozen;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public int getID() {
		return ID;
	}

}

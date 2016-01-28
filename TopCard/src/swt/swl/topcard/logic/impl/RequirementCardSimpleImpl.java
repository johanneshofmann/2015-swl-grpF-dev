package swt.swl.topcard.logic.impl;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;

public class RequirementCardSimpleImpl implements RequirementCardSimple {

	private SimpleIntegerProperty ID, minorVersion, majorVersion, ownerID, rqID, isFrozen;
	private SimpleStringProperty title, ownerName, modules, teams, description, rationale, source, userStories,
			supportingMaterials, fitCriterion, lastModifiedAt;
	private Timestamp createdAt;

	private SubmittedVoteSimple submittedVote;

	RQBuilder RQBuilder;

	private RequirementCardSimpleImpl() {

		this.ID = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.majorVersion = new SimpleIntegerProperty();
		this.minorVersion = new SimpleIntegerProperty();
		this.ownerID = new SimpleIntegerProperty();
		this.ownerName = new SimpleStringProperty();
		this.rqID = new SimpleIntegerProperty();
		this.modules = new SimpleStringProperty();
		this.teams = new SimpleStringProperty();

		this.description = new SimpleStringProperty();
		this.rationale = new SimpleStringProperty();
		this.source = new SimpleStringProperty();
		this.userStories = new SimpleStringProperty();
		this.supportingMaterials = new SimpleStringProperty();
		this.fitCriterion = new SimpleStringProperty();
		this.isFrozen = new SimpleIntegerProperty();
		this.lastModifiedAt = new SimpleStringProperty();
	}

	public static class RQBuilderImpl implements RQBuilder {

		RequirementCardSimpleImpl requirementCard = new RequirementCardSimpleImpl();

		public RQBuilder setID(int ID) {

			requirementCard.ID.set(ID);
			return this;
		}

		public RQBuilder setTitle(String title) {

			requirementCard.title.set(title);
			return this;
		}

		public RQBuilder setMinorVersion(int minorVersion) {

			requirementCard.minorVersion.set(minorVersion);
			return this;
		}

		public RQBuilder setMajorVersion(int majorVersion) {

			requirementCard.majorVersion.set(majorVersion);
			return this;
		}

		public RQBuilder setOwnerID(int ownerID) {
			requirementCard.ownerID.set(ownerID);
			return this;
		}

		public RQBuilder setOwnerName(String ownerName) {
			requirementCard.ownerName.set(ownerName);
			return this;
		}

		public RQBuilder setRqID(int rqID) {
			requirementCard.rqID.set(rqID);
			return this;
		}

		public RQBuilder setModules(String modules) {
			requirementCard.modules.set(modules);
			return this;
		}

		public RQBuilder setTeams(String teams) {
			requirementCard.teams.set(teams);
			return this;
		}

		public RQBuilder setDescription(String description) {
			requirementCard.description.set(description);
			return this;
		}

		public RQBuilder setRationale(String rationale) {
			requirementCard.rationale.set(rationale);
			return this;
		}

		public RQBuilder setSource(String source) {
			requirementCard.source.set(source);
			return this;
		}

		public RQBuilder setSupportingMaterials(String supportingMaterials) {
			requirementCard.supportingMaterials.set(supportingMaterials);
			return this;
		}

		public RQBuilder setFitCriterion(String fitCriterion) {
			requirementCard.fitCriterion.set(fitCriterion);
			return this;
		}

		public RQBuilder setFrozen(int frozen) {

			requirementCard.isFrozen.set(frozen);
			return this;
		}

		public RQBuilder setCreatedAt(Timestamp createdAt) {
			requirementCard.createdAt = createdAt;
			return this;
		}

		public RQBuilder setLastModifiedAt(String lastModifiedAt) {
			requirementCard.lastModifiedAt.set(lastModifiedAt);
			return null;
		}

		/**
		 * @param submittedVote
		 *            the submittedVote to set
		 */
		public RQBuilder setSubmittedVote(SubmittedVoteSimpleImpl submittedVote) {
			requirementCard.submittedVote = submittedVote;
			return null;
		}

		public RQBuilder setUserStories(String userStories) {
			requirementCard.userStories.set(userStories);
			return null;
		}

		public RequirementCardSimple buildRQ() {
			return requirementCard;
		}
	}

	public int getID() {
		return ID.get();
	}

	public int getMinorVersion() {
		return minorVersion.get();
	}

	public int getMajorVersion() {
		return majorVersion.get();
	}

	public int getOwnerID() {
		return ownerID.get();
	}

	public int getRqID() {
		return rqID.get();
	}

	public int getIsFrozen() {
		return isFrozen.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getOwnerName() {
		return ownerName.get();
	}

	public String getModules() {
		return modules.get();
	}

	public String getDescription() {
		return description.get();
	}

	public String getRationale() {
		return rationale.get();
	}

	public String getSource() {
		return source.get();
	}

	public String getUserStories() {
		return userStories.get();
	}

	public String getSupportingMaterials() {
		return supportingMaterials.get();
	}

	public String getFitCriterion() {
		return fitCriterion.get();
	}

	public String getLastModifiedAt() {
		return lastModifiedAt.get();
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public SubmittedVoteSimple getSubmittedVote() {
		return submittedVote;
	}

	// Getters

}

package swt.swl.topcard.logic.entity.impl;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;

public class RequirementCardSimpleImpl implements RequirementCardSimple {

	private SimpleIntegerProperty ID, minorVersion, majorVersion, ownerID, rqID, isFrozen;
	private SimpleStringProperty title, ownerName, modules, teams, description, rationale, source, userStories,
			supportingMaterials, fitCriterion, lastModifiedAt;
	private Timestamp createdAt;

	private SubmittedVoteSimple submittedVote;

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

	public static class RequirementCardBuilderImpl implements RequirementCardBuilder {

		private RequirementCardSimpleImpl requirementCard = new RequirementCardSimpleImpl();

		public RequirementCardBuilder setID(int ID) {

			requirementCard.ID.set(ID);
			return this;
		}

		public RequirementCardBuilder setTitle(String title) {

			requirementCard.title.set(title);
			return this;
		}

		public RequirementCardBuilder setMinorVersion(int minorVersion) {

			requirementCard.minorVersion.set(minorVersion);
			return this;
		}

		public RequirementCardBuilder setMajorVersion(int majorVersion) {

			requirementCard.majorVersion.set(majorVersion);
			return this;
		}

		public RequirementCardBuilder setOwnerID(int ownerID) {
			requirementCard.ownerID.set(ownerID);
			return this;
		}

		public RequirementCardBuilder setOwnerName(String ownerName) {
			requirementCard.ownerName.set(ownerName);
			return this;
		}

		public RequirementCardBuilder setRqID(int rqID) {
			requirementCard.rqID.set(rqID);
			return this;
		}

		public RequirementCardBuilder setModules(String modules) {
			requirementCard.modules.set(modules);
			return this;
		}

		public RequirementCardBuilder setTeams(String teams) {
			requirementCard.teams.set(teams);
			return this;
		}

		public RequirementCardBuilder setDescription(String description) {
			requirementCard.description.set(description);
			return this;
		}

		public RequirementCardBuilder setRationale(String rationale) {
			requirementCard.rationale.set(rationale);
			return this;
		}

		public RequirementCardBuilder setSource(String source) {
			requirementCard.source.set(source);
			return this;
		}

		public RequirementCardBuilder setSupportingMaterials(String supportingMaterials) {
			requirementCard.supportingMaterials.set(supportingMaterials);
			return this;
		}

		public RequirementCardBuilder setFitCriterion(String fitCriterion) {
			requirementCard.fitCriterion.set(fitCriterion);
			return this;
		}

		public RequirementCardBuilder setFrozen(int frozen) {

			requirementCard.isFrozen.set(frozen);
			return this;
		}

		public RequirementCardBuilder setCreatedAt(Timestamp createdAt) {
			requirementCard.createdAt = createdAt;
			return this;
		}

		public RequirementCardBuilder setLastModifiedAt(String lastModifiedAt) {
			requirementCard.lastModifiedAt.set(lastModifiedAt);
			return this;
		}

		/**
		 * @param submittedVote
		 *            the submittedVote to set
		 */
		public RequirementCardBuilder setSubmittedVote(SubmittedVoteSimpleImpl submittedVote) {
			requirementCard.submittedVote = submittedVote;
			return this;
		}

		public RequirementCardBuilder setUserStories(String userStories) {
			requirementCard.userStories.set(userStories);
			return this;
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

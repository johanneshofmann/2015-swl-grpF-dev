package swt.swl.topcard.logic.entitiy;

import java.sql.Timestamp;

import swt.swl.topcard.logic.entity.impl.SubmittedVoteSimpleImpl;

/**
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardSimple {

	public static interface RequirementCardBuilder {

		public RequirementCardBuilder setID(int ID);

		public RequirementCardBuilder setTitle(String title);

		public RequirementCardBuilder setMinorVersion(int minorVersion);

		public RequirementCardBuilder setMajorVersion(int majorVersion);

		public RequirementCardBuilder setOwnerID(int ownerID);

		public RequirementCardBuilder setOwnerName(String ownerName);

		public RequirementCardBuilder setRqID(int rqID);

		public RequirementCardBuilder setModules(String modules);

		public RequirementCardBuilder setTeams(String teams);

		public RequirementCardBuilder setDescription(String description);

		public RequirementCardBuilder setRationale(String rationale);

		public RequirementCardBuilder setSource(String source);

		public RequirementCardBuilder setSupportingMaterials(String supportingMaterials);

		public RequirementCardBuilder setFitCriterion(String fitCriterion);

		public RequirementCardBuilder setFrozen(int frozen);

		public RequirementCardBuilder setCreatedAt(Timestamp createdAt);

		public RequirementCardBuilder setLastModifiedAt(String lastModifiedAt);

		/**
		 * @param submittedVote
		 *            the submittedVote to set
		 */
		public RequirementCardBuilder setSubmittedVote(SubmittedVoteSimpleImpl submittedVote);

		public RequirementCardBuilder setUserStories(String userStories);

		public RequirementCardSimple buildRQ();

	}

	String getOwnerName();

	String getModules();

	int getRqID();

	String getSupportingMaterials();

	String getFitCriterion();

	Object getCreatedAt();

	String getTitle();

	String getLastModifiedAt();

	int getMinorVersion();

	int getMajorVersion();

	String getDescription();

	String getRationale();

	int getID();

	String getSource();

	String getUserStories();

	SubmittedVoteSimple getSubmittedVote();

}
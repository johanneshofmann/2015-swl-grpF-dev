package swt.swl.topcard.logic.entitiy;

import java.sql.Timestamp;

import swt.swl.topcard.logic.entity.impl.SubmittedVoteSimpleImpl;

/**
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardSimple {

	public static interface RQBuilder {

		public RQBuilder setID(int ID);

		public RQBuilder setTitle(String title);

		public RQBuilder setMinorVersion(int minorVersion);

		public RQBuilder setMajorVersion(int majorVersion);

		public RQBuilder setOwnerID(int ownerID);

		public RQBuilder setOwnerName(String ownerName);

		public RQBuilder setRqID(int rqID);

		public RQBuilder setModules(String modules);

		public RQBuilder setTeams(String teams);

		public RQBuilder setDescription(String description);

		public RQBuilder setRationale(String rationale);

		public RQBuilder setSource(String source);

		public RQBuilder setSupportingMaterials(String supportingMaterials);

		public RQBuilder setFitCriterion(String fitCriterion);

		public RQBuilder setFrozen(int frozen);

		public RQBuilder setCreatedAt(Timestamp createdAt);

		public RQBuilder setLastModifiedAt(String lastModifiedAt);

		/**
		 * @param submittedVote
		 *            the submittedVote to set
		 */
		public RQBuilder setSubmittedVote(SubmittedVoteSimpleImpl submittedVote);

		public RQBuilder setUserStories(String userStories);

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

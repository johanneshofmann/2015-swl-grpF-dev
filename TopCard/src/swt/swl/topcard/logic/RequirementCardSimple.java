package swt.swl.topcard.logic;

/**
 * 
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardSimple {

	String getOwnerName();

	String getModules();

	int getRqID();

	String getSupportingMaterials();

	String getFitCriterion();

	Object getCreatedAt();

	String getTitle();
	
	void setTitle(String title);

	String getLastModifiedAt();

	int getMinorVersion();

	int getMajorVersion();

	String getDescription();

	String getRationale();

	int getID();

	String getSource();

	String getUserStories();

}

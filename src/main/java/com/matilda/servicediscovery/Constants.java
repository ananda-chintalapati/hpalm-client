package com.matilda.servicediscovery;

/**
 *
 * These constants are used throughout the code to set the server to work with.
 * To execute this code, change these settings to fit those of your server.
 */
public class Constants {
	private Constants() {
	}

	public static final String HOST = "qc1260.inttst.thrivent.com";
	public static final String PORT = "8443";

	public static final String USERNAME = "MatildaQC";
	public static final String PASSWORD = "SJ6oYSfG";

	public static final String DOMAIN = "ACTIVE";
	public static final String PROJECT = "ERM_and_E";

	/**
	 * Supports running tests correctly on both versioned and non-versioned
	 * projects.
	 * 
	 * @return true if entities of entityType support versioning
	 */
	public static boolean isVersioned(String entityType, final String domain, final String project) throws Exception {

		RestConnector con = RestConnector.getInstance();
		String descriptorUrl = con
				.buildUrl("rest/domains/" + domain + "/projects/" + project + "/customization/entities/" + entityType);

		String descriptorXml = con.httpGet(descriptorUrl, null, null).toString();
		EntityDescriptor descriptor = EntityMarshallingUtils.marshal(EntityDescriptor.class, descriptorXml);

		boolean isVersioned = descriptor.getSupportsVC().getValue();

		return isVersioned;
	}

	public static String generateFieldXml(String field, String value) {
		return "<Field Name=\"" + field + "\"><Value>" + value + "</Value></Field>";
	}

	/**
	 * This string used to create new "requirement" type entities.
	 */
	public static final String entityToPostName = "req" + Double.toHexString(Math.random());
	public static final String entityToPostFieldName = "type-id";
	public static final String entityToPostFieldValue = "2";
	public static final String entityToPostFormat = "<Entity Type=\"requirement\">" + "<Fields>"
			+ Constants.generateFieldXml("%s", "%s") + Constants.generateFieldXml("%s", "%s") + "</Fields>"
			+ "</Entity>";
	
	
	public static final String entitiesroot = "<Entities TotalResults=\"1\">";
	public static final String entitiesend = "</Entities>";
	
	public static final String entityToCreateTestFolderFormat = "<Entity Type=\"test-set-folder\">" + "<Fields>"
			+ Constants.generateFieldXml("name", "%s") 
			+ Constants.generateFieldXml("parent-id", "%s") + "</Fields>"
			+ "</Entity>";
	
	public static final String entityToCreateTestFolderPostXml = String.format(entityToCreateTestFolderFormat, "name", "Matilda_02042019");
	
	public static final String entityToCreateTestFormat = "<Entity Type=\"test-set\">" + "<Fields>"
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") + "</Fields>"
			+ "</Entity>";
	
	public static final String entityToCreateTestPostXml = String.format(entityToCreateTestFormat, 
					"name", "matilda testset1", 
					"status", "Open",
					"open-date", "2019-02-04",
					"description", "test matilda",
					"subtype-id","hp.qc.test-set.default",
					"parent-id", "1044");

	
	public static final String entityToCreateTestInstanceFormat = "<Entity Type=\"test-instance\">" + "<Fields>"
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") 
			//+ Constants.generateFieldXml("%s", "%s") 
			//+ Constants.generateFieldXml("%s", "%s") 
			//+ Constants.generateFieldXml("%s", "%s") 
			+ Constants.generateFieldXml("%s", "%s") + "</Fields>"
			+ "</Entity>";
	
	public static final String entityToCreateTestInstancePostXml = String.format(entityToCreateTestInstanceFormat, 
					"name", "BRAT123", 
					//"status", "No Run",
					"actual-tester", "matildaqc",
				//	"test-config-id", "1315",
					//"owner", "matildaqc",
					"cycle-id","6957",
					//"test-id","314",
					"test-id","103",
					"subtype-id", "hp.qc.test-instance.MANUAL");
					//"subtype-id", "hp.qc.test-instance.MANUAL");
	
	
	public static final String entityToPostXml = String.format(entityToPostFormat, "name", entityToPostName,
			entityToPostFieldName, entityToPostFieldValue);

	public static final CharSequence entityToPostFieldXml = generateFieldXml(Constants.entityToPostFieldName,
			Constants.entityToPostFieldValue);

}
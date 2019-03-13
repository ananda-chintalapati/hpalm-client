package com.matilda.servicediscovery;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * This example shows how to read collections and entities as XML or into objects.
 */
@RestController
@RequestMapping("/matilda/alm")
public class ALMController {

	
	@Value("${hpalm.username}")
	private String username;
	
	@Value("${hpalm.password}")
	private String password;
	
	@Value("${hpalm.domain}")
	private String domain;
	
	@Value("${hpalm.project}")
	private String project;
	
	@Value("${hpalm.url}")
	private String serverUrl;
	
	@RequestMapping(method = RequestMethod.POST,  value = "/createreq", produces="application/xml")
		public @ResponseBody String createRequirement() throws Exception {
			
			
			RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

			// use the login example to learn how to authenticate properly.
			// we use that functionality here to login so that we can do more
			// complex actions.
			AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

			boolean loginResult = login.login(username, password);
            System.out.println("login status : " + loginResult);
			
			// After login set the session
			boolean sessionCreated = con.getQCSession();

			System.out.println("Session Created : " + sessionCreated);
			
			// Build the location of the requirements collection, and the
			// XML for an entity of type requirement
			String requirementsUrl = con.buildEntityCollectionUrl("requirement");
			System.out.println("requirementsUrl is : " + requirementsUrl);
			
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "application/xml");
			requestHeaders.put("Accept", "application/xml");
			Response postedEntityResponse = con.httpPost(requirementsUrl, Constants.entityToPostXml.getBytes(),
					requestHeaders);

			System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode() + HttpURLConnection.HTTP_CREATED);

			String postedEntityResponseXml = postedEntityResponse.toString(); 
			
			System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
			
			login.logout();
			System.out.println("Logout Successfully");
			return postedEntityResponseXml;
			
		}
	
	
	
	@RequestMapping(method = RequestMethod.POST,  value = "/createtestset", produces="application/xml")
	public @ResponseBody String Createtestset(@RequestParam String testSetName, @RequestParam String status, @RequestParam String openDate, @RequestParam String description, @RequestParam String subTypeId, @RequestParam int parentId) throws Exception {
		
		
		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		String testsetfoldersurl = con.buildEntityCollectionUrl("test-set");
		System.out.println("testsetfoldersurl is : " + testsetfoldersurl);
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		String reqXml = createTestSetReqXml(testSetName, status, openDate, description, subTypeId, parentId);
		Response postedEntityResponse = con.httpPost(testsetfoldersurl, reqXml.getBytes(),
				requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode() + HttpURLConnection.HTTP_CREATED);

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST,  value = "/createtestinstance", produces="application/xml")
	public @ResponseBody String createtestInstance() throws Exception {
		
		 RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		String testsetfoldersurl = con.buildEntityCollectionUrl("test-instance");
		System.out.println("testsetfoldersurl is : " + testsetfoldersurl);
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
       // String reqxml = createTestSetInstanceReqXml(testInstName, actualTester, testConfigId, cycleId, testId, subTypeid);		
        String reqxml = Constants.entityToCreateTestInstancePostXml;
		System.out.println("Request xml is" + reqxml);
		
		Response postedEntityResponse = con.httpPost(testsetfoldersurl, reqxml.getBytes(),
				requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
		
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET,  value = "/fetchTestInstances/{id}", produces="application/json")
	public @ResponseBody String fetchTestinstances(@PathVariable String id) throws Exception {
		
		String queryString = id;
        System.out.println("id is " + id);

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		// Fetch Test Plans with test plan id
		//String url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/tests";
		//System.out.println(url);
		//String query = "query={name['003*']}";
		//Response postedEntityResponse = con.httpGet(url,"query={name['DB2 DPropR - DEVTEST']}\"", requestHeaders);
		//Response postedEntityResponse = con.httpGet(url,query, requestHeaders);
		
		//Fetch test folder info for a spefic folder
		//url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/test-folders";
		//System.out.println(url);
		//postedEntityResponse = con.httpGet(url,"query={name['"+queryString+"']}", requestHeaders);

		//Fetch test cases for a spefic folder
		 url = con.buildEntityCollectionUrl("test-instance")+"/"+queryString;
		 System.out.println(url);
	     postedEntityResponse = con.httpGet(url,"", requestHeaders);
		
		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET,  value = "/fetchAllTestinstances/{id}", produces="application/json")
	public @ResponseBody String fetchAllTestinstances(@PathVariable String id) throws Exception {
		
		String queryString = id;
        System.out.println("id is " + id);

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");

		//Fetch test cases for a spefic folder
		 url = con.buildEntityCollectionUrl("test-instance");
		 System.out.println(url);
	     postedEntityResponse = con.httpGet(url,"query={cycle-id['"+queryString+"']}", requestHeaders);
	     
	     //test-set.assign-rcyc

		
		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
		
	}

	@RequestMapping(method = RequestMethod.GET,  value = "/fetchTestCases/{id}", produces="application/json")
	public @ResponseBody String fetchTestCases(@PathVariable String id) throws Exception {
		
        String queryString = id;
        System.out.println("folder heirarchial id is " + id);

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();
		System.out.println("before login");
		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		// Fetch Test Plans with test plan id
		//String url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/tests";
		//System.out.println(url);
		//String query = "query={name['003*']}";
		//Response postedEntityResponse = con.httpGet(url,"query={name['DB2 DPropR - DEVTEST']}\"", requestHeaders);
		//Response postedEntityResponse = con.httpGet(url,query, requestHeaders);
		
		//Fetch test folder info for a spefic folder
		//url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/test-folders";
		//System.out.println(url);
		//postedEntityResponse = con.httpGet(url,"query={name['"+queryString+"']}", requestHeaders);

		//Fetch test cases for a spefic folder
		 url = con.buildEntityCollectionUrl("test");
		 System.out.println(url);
	     postedEntityResponse = con.httpGet(url,"query={test-folder.hierarchical-path['"+queryString+"*']}", requestHeaders);

		
		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
	}
	
	
	@RequestMapping(method = RequestMethod.GET,  value = "/fetchTestFolderInfo/{id}", produces="application/json")
	public @ResponseBody String fetchTestFolderInfo(@PathVariable String id) throws Exception {
		
        String queryString = id;
        System.out.println("folder heirarchial id is " + id);

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		// Fetch Test Plans with test plan id
		//String url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/tests";
		//System.out.println(url);
		//String query = "query={name['003*']}";
		//Response postedEntityResponse = con.httpGet(url,"query={name['DB2 DPropR - DEVTEST']}\"", requestHeaders);
		//Response postedEntityResponse = con.httpGet(url,query, requestHeaders);
		
		//Fetch test folder info for a spefic folder
		
        url = con.buildEntityCollectionUrl("test-folder");
		System.out.println(url);
		queryString = queryString.replace(" ", "%20");
		postedEntityResponse = con.httpGet(url,"query={name[=\""+queryString+"\"]}", requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
	}
	
	
	@RequestMapping(method = RequestMethod.GET,  value = "/fetchTestSetFolderInfo/{id}", produces="application/json")
	public @ResponseBody String fetchTestSetFolderInfo(@PathVariable String id) throws Exception {
		
        String queryString = id;
        System.out.println("folder heirarchial id is " + id);

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		// Fetch Test Plans with test plan id
		//String url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/tests";
		//System.out.println(url);
		//String query = "query={name['003*']}";
		//Response postedEntityResponse = con.httpGet(url,"query={name['DB2 DPropR - DEVTEST']}\"", requestHeaders);
		//Response postedEntityResponse = con.httpGet(url,query, requestHeaders);
		
		//Fetch test folder info for a spefic folder
		
        url = con.buildEntityCollectionUrl("test-set-folder");
		System.out.println(url);
		queryString = queryString.replace(" ", "%20");
		postedEntityResponse = con.httpGet(url,"query={name[=\""+queryString+"\"]}", requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST,  value = "/CreateUpdateTestSetFolders", produces="application/json")
	public @ResponseBody String CreateUpdateTestSetFolders(@RequestBody String reqJson) throws Exception {
		
		JsonObject responsejson = new JsonObject();

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated + ":" + reqJson);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		if(reqJson != null) {
			Gson gson = new Gson();
			JsonObject body = gson.fromJson(reqJson, JsonObject.class);
			String parentFolderName = body.get("name").getAsString();
			String parentFolderId = body.get("id").getAsString();
			String parentFolderCreatedId = checkFolderExist(con, parentFolderName,  parentFolderId);
			if(parentFolderCreatedId != null) {
				
				responsejson.addProperty("folder-name", parentFolderName);
				responsejson.addProperty("folder-id", parentFolderCreatedId.split(":")[1]);
				responsejson.addProperty("hpath", parentFolderCreatedId.split(":")[0]);
				responsejson.addProperty("parent-id", parentFolderId);
				parentFolderCreatedId = parentFolderCreatedId.split(":")[1];
				
			}else {
				parentFolderCreatedId = createTestSetFolderMethod(con, parentFolderName,  parentFolderCreatedId);
				
				responsejson.addProperty("folder-name", parentFolderName);
				responsejson.addProperty("folder-id", parentFolderCreatedId.split(":")[1]);
				responsejson.addProperty("hpath", parentFolderCreatedId.split(":")[0]);
				responsejson.addProperty("parent-id", parentFolderId);
				parentFolderCreatedId = parentFolderCreatedId.split(":")[1];
			}
			createChildTestSetFolderMethod(con, responsejson,  body, parentFolderCreatedId+"");
		}
	//	System.out.println("folder exist" + checkFolderExist(con, folderName,  parentId));
		login.logout();
		System.out.println("Logout Successfully" + responsejson.toString());
		return responsejson.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET,  value = "/fetchTestSetInfo/{id}", produces="application/json")
	public @ResponseBody String fetchTestSetInfo(@PathVariable String id) throws Exception {
		
        String queryString = id;
        System.out.println("folder heirarchial id is " + id);

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		// Fetch Test Plans with test plan id
		//String url = "https://qc1260.inttst.thrivent.com:8443/qcbin/rest/domains/ACTIVE/projects/ERM_and_E/tests";
		//System.out.println(url);
		//String query = "query={name['003*']}";
		//Response postedEntityResponse = con.httpGet(url,"query={name['DB2 DPropR - DEVTEST']}\"", requestHeaders);
		//Response postedEntityResponse = con.httpGet(url,query, requestHeaders);
		
		//Fetch test folder info for a spefic folder
		
        url = con.buildEntityCollectionUrl("test-set");
		System.out.println(url);
		queryString = queryString.replace(" ", "%20");
		postedEntityResponse = con.httpGet(url,"query={test-set-folder.hierarchical-path['AAAAAHAABAAB*']}", requestHeaders);
		
		//postedEntityResponse = con.httpGet(url,"query={name[=\""+queryString1+"\"];test-set-folder.id["+queryString+"]}", requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
	}


	@RequestMapping(method = RequestMethod.POST,  value = "/createtestsetfolder", produces="application/xml")
	public @ResponseBody String createTestSetFolder(@RequestParam String folderName, @RequestParam String parentId) throws Exception {
		
		
		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		String testsetfoldersurl = con.buildEntityCollectionUrl("test-set-folder");
		System.out.println("testsetfoldersurl is : " + testsetfoldersurl);
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		
		String requestXml = this.createTestSetFolderReqXml(folderName, parentId);
		System.out.println("TestSetFolder request xml is: "  + requestXml);
		
		Response postedEntityResponse = con.httpPost(testsetfoldersurl, requestXml.getBytes(),
				requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		login.logout();
		System.out.println("Logout Successfully");
		return postedEntityResponseXml;
		
	}
	
	
	/**
	 * Create test set folder req xml
	 * 
	 * @param folderName
	 * @param parentFolderId
	 * @return
	 */
	private String createTestSetFolderReqXml(String folderName, String parentFolderId) {
		
		return String.format(Constants.entityToCreateTestFolderFormat, folderName, parentFolderId);
		
	}
	
	/**
	 * Create test set folder req xml
	 * 
	 * @param folderName
	 * @param parentFolderId
	 * @return
	 */
	private String createTestSetInstanceReqXml(String testInstName, String actualTester, int testConfigId, int cycleId, int testId, String subTypeid) {
		System.out.println("testInstName: "+ testInstName);
		System.out.println("actualTester: "+ actualTester);
		System.out.println("testConfigId: "+ testConfigId);
		System.out.println("cycleId: "+ cycleId);
		System.out.println("testId: "+ testId);
		System.out.println("subTypeid: "+ subTypeid);
		
		return String.format(Constants.entityToCreateTestInstanceFormat, 
				"name", testInstName, 
				//"status", "No Run",
				"actual-tester", actualTester,
				//"test-config-id", testConfigId,
				//"owner", "matildaqc",
				"cycle-id",cycleId,
				"test-id",testId,
				"subtype-id", subTypeid);
		//"hp.qc.test-instance.MANUAL"
		
	}

	
	/**
	 * Create test set folder req xml
	 * 
	 * @param folderName
	 * @param parentFolderId
	 * @return
	 */
	private String createTestSetReqXml(String testSetName, String status, String openDate, String description, String subTypeId, int parentId) {
		
		return String.format(Constants.entityToCreateTestFormat, 
				"name", testSetName, 
				"status", status,
				"open-date", openDate,
				"description", description,
				"subtype-id",subTypeId,
				"parent-id", parentId);
		//"hp.qc.test-instance.MANUAL"
		
	}
	
	
   private String checkFolderExist(RestConnector con, String folderName, String parentId) {
	   boolean folderExist = false;
	   String folderId = null;
	   try {
	   String queryString = folderName;
       
	   String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		url = con.buildEntityCollectionUrl("test-set-folder");
		queryString = queryString.replace(" ", "%20");
		postedEntityResponse = con.httpGet(url,"query={name[=\""+queryString+"\"]}", requestHeaders);


		String postedEntityResponseXml = postedEntityResponse.toString(); 
	   
	   Gson gson = new Gson();
	  
		JsonObject body = gson.fromJson(postedEntityResponseXml, JsonObject.class);
		JsonArray results = body.get("entities").getAsJsonArray();
		JsonObject fieldSet = null;
		for(int i=0; i<results.size(); i++) {
			folderId = null;
			fieldSet = results.get(i).getAsJsonObject();
			JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
			for(int j=0; j<fieldsArray.size(); j++) {
				
				JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
				String name = fieldSetobj.get("Name").getAsString();
				
				
				if(name.equalsIgnoreCase("hierarchical-path")) {

					JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
					for(int v = 0; v< valuesArray.size(); v++) {
						JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
						folderId = valueObj.get("value").getAsString();
					}
				}
				
				if(name.equalsIgnoreCase("id")) {

					JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
					for(int v = 0; v< valuesArray.size(); v++) {
						
						JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
						folderId = folderId +":" + valueObj.get("value").getAsInt() + "";
					}
				}
				
				if(name.equalsIgnoreCase("parent-id")) {
					JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
					for(int k = 0; k< valuesArray.size(); k++) {
						JsonObject valueObj = valuesArray.get(k).getAsJsonObject();
						String parentIdstr = valueObj.get("value").getAsString();
						if(parentIdstr.equalsIgnoreCase(parentId)) {
							folderExist = true;
							break;
						}
					}
				}
				if(folderExist) {
					break;
				}
				
			}
			if(folderExist) {
				break;
			}
		}
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   
		 if(!folderExist) 
		 { 
			 folderId = null; 
			 }
		
   	return folderId;
   }
	
   private String createTestSetFolderMethod(RestConnector con, String folderName, String parentId) {
	   String createdFolderId = null;
	   String hpath = null;
	   int pId = 0;
	 try {  
		
	   String testsetfoldersurl = con.buildEntityCollectionUrl("test-set-folder");
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		
		String requestXml = this.createTestSetFolderReqXml(folderName, parentId);
		//System.out.println("TestSetFolder request xml is: "  + requestXml);
		
		Response postedEntityResponse = con.httpPost(testsetfoldersurl, requestXml.getBytes(),	requestHeaders);
		
		String postedEntityResponseXml = postedEntityResponse.toString(); 
		System.out.println("postedEntityResponseXml : not printing " + postedEntityResponseXml);
		System.out.println("postedEntityResponse.getStatusCode() " + postedEntityResponse.getStatusCode());
		
		 int PRETTY_PRINT_INDENT_FACTOR = 4;
		 JSONObject xmlJSONObj = XML.toJSONObject(postedEntityResponseXml);
		 String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
       	 Gson gson = new Gson();
		 JsonObject body = gson.fromJson(jsonPrettyPrintString, JsonObject.class);
		 JsonObject entityObj = body.get("Entity").getAsJsonObject();
		 JsonObject fieldsArray = entityObj.get("Fields").getAsJsonObject();
		 JsonArray arr = fieldsArray.get("Field").getAsJsonArray();
			
			for(int j=0; j<arr.size(); j++) {
				JsonObject fObject =(JsonObject) arr.get(j);
				
				if(fObject.get("Name").getAsString().equalsIgnoreCase("hierarchical-path")) {
					hpath = fObject.get("Value").getAsString();
					System.out.println("created hpath is " + createdFolderId);
				}
				
				if(fObject.get("Name").getAsString().equalsIgnoreCase("id")) {
					pId = fObject.get("Value").getAsInt();
				}
				
			} 
		
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	   return hpath+":"+pId;
   }
   
   
   private void createChildTestSetFolderMethod(RestConnector con, JsonObject responseObject, JsonObject childbody, String parentId) {
		 try {  
			JsonArray childObjArray =  childbody.get("children").getAsJsonArray();
			JsonArray jsonArray = new JsonArray();
			JsonObject resChildObj = null;
			for(int i=0; i< childObjArray.size(); i++) {
				JsonObject childObj = childObjArray.get(i).getAsJsonObject();
				resChildObj = new JsonObject();
				if(childObj.has("name")) {
					String childrenNodeName = childObj.get("name").getAsString();
					resChildObj.addProperty("folder-name", childrenNodeName);
					String folderId = checkFolderExist(con, childrenNodeName,  parentId);
					if(folderId != null) {
						//responseObject.addProperty(childrenNodeName, folderId);
					}else {
						folderId = createTestSetFolderMethod(con, childrenNodeName, parentId);
						//responseObject.addProperty(childrenNodeName, folderId);
					}
					
					resChildObj.addProperty("folder-id", folderId.split(":")[1]);
					resChildObj.addProperty("hpath", folderId.split(":")[0]);
					resChildObj.addProperty("parent-id", parentId);
					jsonArray.add(resChildObj);
					if(childObj.has("children")) {
						folderId = folderId.split(":")[1];
						createChildTestSetFolderMethod(con, resChildObj, childObj, folderId+"");
					}
				}	
			}
			
			responseObject.add("children", jsonArray);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	   }
   
   
   @RequestMapping(method = RequestMethod.POST,  value = "/CreateUpdateTestSets", produces="application/json")
	public @ResponseBody String CreateUpdateTestSets(@RequestBody String reqJson) throws Exception {
		
		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
       System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		try {
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		if(reqJson != null) {
			Gson gson = new Gson();
			JsonObject body = gson.fromJson(reqJson, JsonObject.class);
			String testSetName = body.get("testSetName").getAsString();
			String status = body.get("status").getAsString();
			String openDate = body.get("openDate").getAsString();
			String description = body.get("description").getAsString();
			String subTypeId = body.get("subTypeId").getAsString();
			int parentId = body.get("parentId").getAsInt();
			int testSetId = checkTestSetExist(con, testSetName, parentId);
			if(testSetId > 0) {
				System.out.println("Test set exist with id as " + testSetId );
			}else {
				testSetId = CreatetestsetwithExistingcon(con, testSetName, status, openDate, description,  subTypeId, parentId);
				System.out.println("Test set created with id as " + testSetId );
			}
			
			// Fetch Hierarchial Path
			String hPath = fetchHierarchialPathForTestPlan(con, testSetName);
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "application/json");
			requestHeaders.put("Accept", "application/json");
			
			
			// fetching plans
			 String url = con.buildEntityCollectionUrl("test");
			 Response postedEntityResponse = con.httpGet(url,"query={test-folder.hierarchical-path['"+hPath+"*']}", requestHeaders);
			 String postedEntityResponseXml = postedEntityResponse.toString(); 
			
			 // Fetch TestInstances for a cycleid
			 
			 Map<Integer, String> testPlanIdsMap = fetchAllTestinstancesByCycleId(con, testSetId);
			 
			//creating test instances
			    
				body = gson.fromJson(postedEntityResponseXml, JsonObject.class);
				JsonArray results = body.get("entities").getAsJsonArray();
				JsonObject fieldSet = null;
				for(int i=0; i<results.size(); i++) {
					fieldSet = results.get(i).getAsJsonObject();
					System.out.println(" ");
					String tisubTypeId = null;
					int testId = 0;
					int cycleId = testSetId;
					String testPlanName = null;
					//System.out.println("Field value is " + fieldSet);
					JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
					for(int j=0; j<fieldsArray.size(); j++) {
						
						JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
						String name = fieldSetobj.get("Name").getAsString();
						
						if(name.equalsIgnoreCase("subtype-id")) {
							JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
							for(int v = 0; v< valuesArray.size(); v++) {
								JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
								tisubTypeId = valueObj.get("value").getAsString();
							}
						}
						if(name.equalsIgnoreCase("id")) {
							JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
							for(int v = 0; v< valuesArray.size(); v++) {
								JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
								testId = valueObj.get("value").getAsInt();
							}
						}
						
						if(name.equalsIgnoreCase("name")) {
							JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
							for(int v = 0; v< valuesArray.size(); v++) {
								JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
								testPlanName = valueObj.get("value").getAsString();
							}
						}
						
					}
					
					if(!testPlanIdsMap.containsKey(testId))	{
						String testsetfoldersurl = con.buildEntityCollectionUrl("test-instance");
						String reqxml = createTestSetInstanceReqXml(testPlanName, "matildaqc", 0, cycleId, testId, "hp.qc.test-instance.MANUAL");		
						System.out.println("Request xml is" + reqxml);
						
						System.out.println("Test Instance Created with name as : " + testPlanName);
						System.out.println("tisubTypeId: " + tisubTypeId);
						System.out.println("testId: " + testId);
						System.out.println("cycleId: " + cycleId);
						System.out.println("testPlanName: " + testPlanName);
						System.out.println("");
				
						requestHeaders = new HashMap<String, String>();
						requestHeaders.put("Content-Type", "application/xml");
						requestHeaders.put("Accept", "application/xml");
						postedEntityResponse = con.httpPost(testsetfoldersurl, reqxml.getBytes(), requestHeaders);
	
						System.out.println("postedEntityResponse : " + postedEntityResponse.toString());
						
					}else {
						System.out.println("Test instance is already existing for test plan: " + testId +" Hence test instance not created");
					}
				}
				
				
				
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	//	System.out.println("folder exist" + checkFolderExist(con, folderName,  parentId));
		login.logout();
		System.out.println("Logout Successfully");
		return "Success";
	}
   
   private int checkTestSetExist(RestConnector con, String testSetName,  int parentId) {
	   boolean folderExist = false;
	   int folderId = 0;
	   try {
	   String queryString = testSetName;
       System.out.println("Test Set Name " + testSetName);
       System.out.println("parentId " + parentId);
	   
	   String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		url = con.buildEntityCollectionUrl("test-set");
		System.out.println(url);
		queryString = queryString.replace(" ", "%20");
		postedEntityResponse = con.httpGet(url,"query={name[=\""+queryString+"\"]}", requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
	   
	   Gson gson = new Gson();
	  
		JsonObject body = gson.fromJson(postedEntityResponseXml, JsonObject.class);
		JsonArray results = body.get("entities").getAsJsonArray();
		JsonObject fieldSet = null;
		for(int i=0; i<results.size(); i++) {
			fieldSet = results.get(i).getAsJsonObject();
			System.out.println(" ");
			System.out.println("Field value is " + fieldSet);
			JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
			for(int j=0; j<fieldsArray.size(); j++) {
				
				System.out.println("");
				System.out.println(fieldsArray.get(j).getAsJsonObject());
				JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
				String name = fieldSetobj.get("Name").getAsString();
				if(name.equalsIgnoreCase("id")) {

					JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
					for(int v = 0; v< valuesArray.size(); v++) {
						System.out.println("");
						JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
						folderId = valueObj.get("value").getAsInt();
					}
				}
				
				if(name.equalsIgnoreCase("parent-id")) {
					JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
					for(int k = 0; k< valuesArray.size(); k++) {
						System.out.println("");
						JsonObject valueObj = valuesArray.get(k).getAsJsonObject();
						String parentIdstr = valueObj.get("value").getAsString();
						if(parentIdstr.equalsIgnoreCase(parentId+"")) {
							folderExist = true;
							break;
						}
					}
				}
				if(folderExist) {
					break;
				}
				
			}
			if(folderExist) {
				break;
			}
		}
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   
	   if(!folderExist) {
		   folderId = 0;
	   }
	   System.out.println("TEST SET CYCLE ID IS " + folderId);
   	return folderId;
   }
   
	public @ResponseBody int CreatetestsetwithExistingcon(RestConnector con, String testSetName, String status, String openDate, String description,  String subTypeId, int parentId) throws Exception {
		
		int createdTestSetid = 0;
		String testsetfoldersurl = con.buildEntityCollectionUrl("test-set");
		System.out.println("testsetfoldersurl is : " + testsetfoldersurl);
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		String reqXml = createTestSetReqXml(testSetName, status, openDate, description, subTypeId, parentId);
		Response postedEntityResponse = con.httpPost(testsetfoldersurl, reqXml.getBytes(),
				requestHeaders);

		
		String postedEntityResponseXml = postedEntityResponse.toString(); 
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		 JSONObject xmlJSONObj = XML.toJSONObject(postedEntityResponseXml);
		 String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
      	 Gson gson = new Gson();
		 JsonObject body = gson.fromJson(jsonPrettyPrintString, JsonObject.class);
		 JsonObject entityObj = body.get("Entity").getAsJsonObject();
		 JsonObject fieldsArray = entityObj.get("Fields").getAsJsonObject();
		 JsonArray arr = fieldsArray.get("Field").getAsJsonArray();
			
			for(int j=0; j<arr.size(); j++) {
				JsonObject fObject =(JsonObject) arr.get(j);
				if(fObject.get("Name").getAsString().equalsIgnoreCase("id")) {
					createdTestSetid = fObject.get("Value").getAsInt();
				}
			}

		System.out.println("Create Test id" + createdTestSetid);	
		return createdTestSetid;
		
	}
	
	
	public @ResponseBody String createtestInstancesFromTestSet(RestConnector con, @RequestParam String testInstName,@RequestParam String actualTester,@RequestParam int testConfigId,@RequestParam int cycleId,
			@RequestParam int testId, @RequestParam String subTypeid) throws Exception {
		

		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		String testsetfoldersurl = con.buildEntityCollectionUrl("test-instance");
		System.out.println("testsetfoldersurl is : " + testsetfoldersurl);
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
        String reqxml = createTestSetInstanceReqXml(testInstName, actualTester, testConfigId, cycleId, testId, subTypeid);		
		System.out.println("Request xml is" + reqxml);
		
		Response postedEntityResponse = con.httpPost(testsetfoldersurl, reqxml.getBytes(),
				requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
		return postedEntityResponseXml;
		
	}
	
	
	public String fetchHierarchialPathForTestPlan(RestConnector con, String testInstName) {

		String hPath = null;
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
		try {	
		    url = con.buildEntityCollectionUrl("test-folder");
			System.out.println(url);
			testInstName = testInstName.replace(" ", "%20");
			postedEntityResponse = con.httpGet(url,"query={name[=\""+testInstName+"\"]}", requestHeaders);
			
			String postedEntityResponseXml = postedEntityResponse.toString(); 
			
			
			Gson gson = new Gson();
			  
			JsonObject body = gson.fromJson(postedEntityResponseXml, JsonObject.class);
			JsonArray results = body.get("entities").getAsJsonArray();
			JsonObject fieldSet = null;
			for(int i=0; i<results.size(); i++) {
				fieldSet = results.get(i).getAsJsonObject();
				System.out.println(" ");
				System.out.println("Field value is " + fieldSet);
				JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
				for(int j=0; j<fieldsArray.size(); j++) {
					
					System.out.println("");
					System.out.println(fieldsArray.get(j).getAsJsonObject());
					JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
					String name = fieldSetobj.get("Name").getAsString();
					if(name.equalsIgnoreCase("hierarchical-path")) {

						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							System.out.println("");
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							hPath = valueObj.get("value").getAsString();
						}
						
						System.out.println("hPath is" + hPath);
					}
					
					
					
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hPath;
	}
	
	
	public Map<Integer, String> fetchAllTestinstancesByCycleId(RestConnector con, int cycleid) throws Exception {
		
		Map<Integer, String> testPlanIdsMap = new HashMap<Integer, String>();
		// Build the location of the requirements collection, and the
		// XML for an entity of type requirement
		//String requirementsUrl = con.buildEntityCollectionUrl("requirement");
		//System.out.println("requirementsUrl is : " + requirementsUrl);
		String url = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");

		//Fetch test cases for a spefic folder
		 url = con.buildEntityCollectionUrl("test-instance");
		 System.out.println(url);
	     postedEntityResponse = con.httpGet(url,"query={cycle-id['"+cycleid+"']}", requestHeaders);
	     	     
	     String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		Gson gson = new Gson();
			  
			JsonObject body = gson.fromJson(postedEntityResponseXml, JsonObject.class);
			JsonArray results = body.get("entities").getAsJsonArray();
			JsonObject fieldSet = null;
			for(int i=0; i<results.size(); i++) {
				fieldSet = results.get(i).getAsJsonObject();
				System.out.println(" ");
				System.out.println("Field value is " + fieldSet);
				JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
				for(int j=0; j<fieldsArray.size(); j++) {
					
					System.out.println("");
					System.out.println(fieldsArray.get(j).getAsJsonObject());
					JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
					String name = fieldSetobj.get("Name").getAsString();
					if(name.equalsIgnoreCase("test-id")) {

						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							System.out.println("");
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							testPlanIdsMap.put(valueObj.get("value").getAsInt(), "");
						}
						
					}
					
					
					
				}
			}
		
		return testPlanIdsMap;
		
	}
	
	/**
	 * @param reqJson
	 */
	 @RequestMapping(method = RequestMethod.GET,  value = "/fetchAllTestInstancesReports/{hPath}", produces="application/json")
	public String fetchAllTestInstancesReports(@PathVariable String hPath) {
	
		 JsonArray resArray = new JsonArray();
	Response postedEntityResponse = null;
	try {	
		

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		// use the login example to learn how to authenticate properly.
		// we use that functionality here to login so that we can do more
		// complex actions.
		AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
        System.out.println("login status : " + loginResult);
		
		// After login set the session
		boolean sessionCreated = con.getQCSession();

		System.out.println("Session Created : " + sessionCreated);
		
			String url = null;
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "application/json");
			requestHeaders.put("Accept", "application/json");
			url = con.buildEntityCollectionUrl("test-set");
			System.out.println(url);
			postedEntityResponse = con.httpGet(url,"query={test-set-folder.hierarchical-path['"+hPath+"*']}", requestHeaders);
			System.out.println("hpath" + hPath);
		
			
			Gson gson = new Gson();
			url = con.buildEntityCollectionUrl("test-instance");
			JsonObject body = gson.fromJson(postedEntityResponse.toString(), JsonObject.class);
			JsonArray results = body.get("entities").getAsJsonArray();
			JsonObject fieldSet = null;
			for(int i=0; i<results.size(); i++) {
				 JsonObject responsejson = new JsonObject();
				fieldSet = results.get(i).getAsJsonObject();
				System.out.println(" ");
				System.out.println("Field value is " + fieldSet);
				JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
				for(int j=0; j<fieldsArray.size(); j++) {
					
					System.out.println(fieldsArray.get(j).getAsJsonObject());
					JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
					String name = fieldSetobj.get("Name").getAsString();
					
					if(name.equalsIgnoreCase("name")) {
						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							System.out.println("");
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							responsejson.addProperty("test-set", valueObj.get("value").getAsString());
						}
						
					}
					
					if(name.equalsIgnoreCase("id")) {

						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							System.out.println("");
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							
							Response postedEntityResponse1 = con.httpGet(url,"query={cycle-id['"+valueObj.get("value").getAsInt()+"']}", requestHeaders);
							
							System.out.println("cycle id:"  + valueObj.get("value").getAsInt());
							
							System.out.println("response test:"  + postedEntityResponse1.toString());
							
							 populateTestInstanceData(postedEntityResponse1.toString(), responsejson);
							
						}
						
					}
					
				}
			resArray.add(responsejson);
			}
			
			
			
			
			
		login.logout();
		System.out.println("Logout Successfully");
		
	}catch(Exception e) {
	  e.printStackTrace();	
	}
	return resArray.toString();
	}
	
	 
	public void populateTestInstanceData(String testInstanceJson, JsonObject responsejson) {
		JsonArray tiArray = new JsonArray();
		try {
			
			Gson gson = new Gson();
			JsonObject testInstanceDataObj = new JsonObject();
			JsonObject body = gson.fromJson(testInstanceJson, JsonObject.class);
			JsonArray results = body.get("entities").getAsJsonArray();
			JsonObject fieldSet = null;
			
			for(int i=0; i<results.size(); i++) {
				fieldSet = results.get(i).getAsJsonObject();
				System.out.println(" ");
				System.out.println("Field value is " + fieldSet);
				JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
				for(int j=0; j<fieldsArray.size(); j++) {
					
					System.out.println("");
					System.out.println(fieldsArray.get(j).getAsJsonObject());
					JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
					String name = fieldSetobj.get("Name").getAsString();
					if(name.equalsIgnoreCase("name")) {

						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							testInstanceDataObj.addProperty("name", valueObj.get("value").getAsString());
						}
						
					}
					/*
					 * if(name.equalsIgnoreCase("owner")) {
					 * 
					 * JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray(); for(int v
					 * = 0; v< valuesArray.size(); v++) { JsonObject valueObj =
					 * valuesArray.get(v).getAsJsonObject();
					 * testInstanceDataObj.addProperty("Owner",
					 * valueObj.get("value").getAsString()); }
					 * 
					 * } if(name.equalsIgnoreCase("actual-tester")) {
					 * 
					 * JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray(); for(int v
					 * = 0; v< valuesArray.size(); v++) { JsonObject valueObj =
					 * valuesArray.get(v).getAsJsonObject();
					 * testInstanceDataObj.addProperty("actual-tester",
					 * valueObj.get("value").getAsString()); }
					 * 
					 * }
					 */				
					if(name.equalsIgnoreCase("status")) {

						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							if(valueObj.get("value") != null) {
								testInstanceDataObj.addProperty("status", valueObj.get("value").getAsString());
							}else {
								testInstanceDataObj.addProperty("status", "No Run");
							}
							
						}
						
					}
					
					
					
				}
				tiArray.add(testInstanceDataObj);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		responsejson.add("test-instance", tiArray);
		
	}
	
	public String getTestSetFolderHPath(RestConnector con, String testSetFolderName, int parentId) throws Exception {
		
		String url = null;
		String hPath = null;
		Response postedEntityResponse = null;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/json");
		requestHeaders.put("Accept", "application/json");
		
        url = con.buildEntityCollectionUrl("test-set-folder");
		System.out.println(url);
		testSetFolderName = testSetFolderName.replace(" ", "%20");
		postedEntityResponse = con.httpGet(url,"query={name[=\""+testSetFolderName+"\"]}", requestHeaders);

		System.out.println("postedEntityResponse response is: " + postedEntityResponse.getStatusCode());

		String postedEntityResponseXml = postedEntityResponse.toString(); 
		
		System.out.println("postedEntityResponseXml : " + postedEntityResponseXml);
		
        boolean folderExist = false;
		 Gson gson = new Gson();
		  
			JsonObject body = gson.fromJson(postedEntityResponseXml, JsonObject.class);
			JsonArray results = body.get("entities").getAsJsonArray();
			JsonObject fieldSet = null;
			for(int i=0; i<results.size(); i++) {
				fieldSet = results.get(i).getAsJsonObject();
				JsonArray fieldsArray = fieldSet.get("Fields").getAsJsonArray();
				for(int j=0; j<fieldsArray.size(); j++) {
					
					JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject();
					String name = fieldSetobj.get("Name").getAsString();
					if(name.equalsIgnoreCase("hierarchical-path")) {

						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int v = 0; v< valuesArray.size(); v++) {
							System.out.println("");
							JsonObject valueObj = valuesArray.get(v).getAsJsonObject();
							hPath = valueObj.get("value").getAsString();
						}
					}
					
					if(name.equalsIgnoreCase("parent-id")) {
						JsonArray valuesArray = fieldSetobj.get("values").getAsJsonArray();
						for(int k = 0; k< valuesArray.size(); k++) {
							System.out.println("");
							JsonObject valueObj = valuesArray.get(k).getAsJsonObject();
							int parentIdstr = valueObj.get("value").getAsInt();
							if(parentIdstr == parentId) {
								folderExist = true;
								break;
							}
						}
					}
					if(folderExist) {
						break;
					}
					
				}
				if(folderExist) {
					break;
				}
			}
		return hPath;
	}
	
	
	public void fetchTestSetsInfo() {
		
	}
}

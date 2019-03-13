package com.matilda.servicediscovery;

import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Test {

	public static void main(String[] args) {
		String testJson = "{\"entities\":[],\"TotalResults\":0}";
		String sXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entity Type=\"test-set-folder\"><ChildrenCount><Value>0</Value></ChildrenCount><Fields><Field Name=\"last-modified\"><Value>2019-02-14 15:42:11</Value></Field><Field Name=\"ver-stamp\"><Value>2</Value></Field><Field Name=\"attachment\"><Value></Value></Field><Field Name=\"workflow\"><Value></Value></Field><Field Name=\"hierarchical-path\"><Value>AAAAAX</Value></Field><Field Name=\"name\"><Value>2019_test5</Value></Field><Field Name=\"description\"><Value></Value></Field><Field Name=\"no-of-sons\"/><Field Name=\"id\"><Value>1077</Value></Field><Field Name=\"parent-id\"><Value>0</Value></Field><Field Name=\"order-id\"><Value>21</Value></Field><Field Name=\"assign-rcyc\"><Value></Value></Field></Fields><RelatedEntities/></Entity>";
		 int PRETTY_PRINT_INDENT_FACTOR = 4;
		JSONObject xmlJSONObj = XML.toJSONObject(sXML);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        System.out.println(jsonPrettyPrintString);
        
    	Gson gson = new Gson();
		JsonObject body = gson.fromJson(jsonPrettyPrintString, JsonObject.class);
		JsonObject entityObj = body.get("Entity").getAsJsonObject();
		JsonObject fieldsArray = entityObj.get("Fields").getAsJsonObject();
		JsonArray arr = fieldsArray.get("Field").getAsJsonArray();
		
		for(int j=0; j<arr.size(); j++) {
			JsonObject jj =(JsonObject) arr.get(j);
			if(jj.get("Name").getAsString().equalsIgnoreCase("id")) {
				System.out.println(jj.get("Value").getAsString());
			}
			//System.out.println(fieldsArray.get(j).getAsJsonObject());
			/*
			 * JsonObject fieldSetobj = fieldsArray.get(j).getAsJsonObject(); String name =
			 * fieldSetobj.get("Name").getAsString(); if(name.equalsIgnoreCase("id")) {
			 * System.out.println(fieldSetobj.get("Value").getAsInt()); }
			 */
		}
	
		
	}

}

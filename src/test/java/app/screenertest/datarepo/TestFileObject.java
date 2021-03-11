package app.screenertest.datarepo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

@JsonSerializableSchema
public class TestFileObject {
  
    @JsonProperty
    String launchurl;
	
	@JsonProperty
    TestObject[] testarray;
    
    
	public String getLaunchUrl() {
		return this.launchurl;
	}
	public TestObject[] getTestarray() {
        return testarray;
    }
}


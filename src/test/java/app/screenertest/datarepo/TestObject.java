package app.screenertest.datarepo;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

@JsonSerializableSchema
public class TestObject {
    @JsonProperty
    private String testname;

    @JsonProperty
    private HashMap<String,String> datamap;

    public String getTestname() {
        return testname;
    }

    public HashMap<String, String> getDatamap() {
        return datamap;
    }
}

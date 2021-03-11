package app.screenertest.datamanager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
 
import com.fasterxml.jackson.databind.ObjectMapper;

import app.screenertest.datarepo.TestFileObject;
import app.screenertest.datarepo.TestObject;

public class JSONDataManager {
    String url;
	TestObject[] testarray;
    public JSONDataManager(String filepath) throws IOException {

        File file = new File(filepath);
        ObjectMapper objectMapper = new ObjectMapper();
        TestFileObject tfo = objectMapper.readValue(file,TestFileObject.class);
        this.testarray=tfo.getTestarray();
        this.url=tfo.getLaunchUrl();
    }
    
    public String getUrl() {
    	return this.url;
    }
    public HashMap<String, String> getData(String testname) {
        for(TestObject t: testarray){
            if(t.getTestname().equalsIgnoreCase(testname)){
                return t.getDatamap();
            }
        }
        return null;
    }
}

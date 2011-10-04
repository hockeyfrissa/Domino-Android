package mogul.demo.android.domain;

import java.io.Serializable;
import java.util.Map;

public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private Map<String, String> properties;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}

package mogul.demo.android.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Request {

	private Map<String, String> requestAttributes = new HashMap<String, String>();
	
	private final String STRING = "string|";
	private final String DATETIME = "datetime|";
	private final String NUMBER = "number|";
	private final String TEXTLIST = "textlist|";
	
	public void addString(String field, String value) {
		requestAttributes.put(field, STRING + value);
	}
	
	public void addDate(String field, String date) {
		requestAttributes.put(field,  DATETIME + date);
	}
	
	public void addTextList(String field, List<String> list) {
		requestAttributes.put(field, TEXTLIST + StringUtils.join(list, ";"));
	}
	
	public void addNumber(String field, String value) {
		requestAttributes.put(field,  NUMBER +  value);
	}
	
	public Map<String, String> getRequestAttributes() {
		return requestAttributes;
	}
}

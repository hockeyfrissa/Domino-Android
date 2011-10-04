package mogul.demo.android.consumer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mogul.demo.android.domain.Document;
import mogul.demo.android.project.Request;
import mogul.demo.android.util.AndroidLogger;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

public class RestConsumerImpl implements RestConsumer {

	private Log logger = new AndroidLogger(RestConsumerImpl.class);
	private HttpClient httpclient;
	private HttpPost httpPost;
	
	public RestConsumerImpl() {
		httpclient = new DefaultHttpClient();
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> getDocuments(String uri) {
		List<Document> retval = new ArrayList<Document>();
		try {
			HttpUriRequest request = new HttpGet(uri);
			HttpResponse response = httpclient.execute(request);
			InputStream in = response.getEntity().getContent();
			
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, String>> list = mapper.readValue(in, List.class);
			in.close();
			logger.debug("json values: " + list);
			for (Map<String, String> doc : list) {
				Document document = new Document();
				document.setProperties(doc);
				document.setKey(doc.get("DocUNID"));
				retval.add(document);
			}
		} catch (Exception e) {
			logger.error(null,  e);
		}
		return retval;
	}

	public Request createNewRequest(String uri) {
		this.httpPost = new HttpPost(uri);
		return new Request();
	}

	public void executeRequest(Request request) {
		for (Map.Entry<String, String> entry : request.getRequestAttributes().entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		try {
			HttpResponse response = httpclient.execute(httpPost);
			StatusLine line = response.getStatusLine();
			logger.debug("Status code returned: " + line.getStatusCode());
			logger.debug("Reason string: " + line.getReasonPhrase());
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

}

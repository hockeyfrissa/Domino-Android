package mogul.demo.android.consumer;

import java.util.List;

import mogul.demo.android.domain.Document;
import mogul.demo.android.project.Request;

public interface RestConsumer {

	public List<Document> getDocuments(String uri);
	
	public Request createNewRequest(String uri);
	
	public void executeRequest(Request request);
}

package mogul.demo.android;

import mogul.demo.android.consumer.RestConsumer;
import mogul.demo.android.consumer.RestConsumerImpl;
import mogul.demo.android.domain.Document;
import mogul.demo.android.project.Request;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDocument extends Activity {
	/** Called when the activity is first created. */
	private EditText mTitleTxt;
	private EditText mBodyTxt;
	private Long mRowId;
	private NotesDbAdapter mDbHelper;
	private Document doc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.document_edit);
		setTitle(R.string.editdocument);

		mTitleTxt = (EditText) findViewById(R.id.title);
		mBodyTxt = (EditText) findViewById(R.id.body);

		Button confirmButton = (Button) findViewById(R.id.confirm);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			doc = (Document) extras.getSerializable(ListDocuments.ROW_ID);
		}

		populateFields();

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (doc == null) {
					RestConsumer consumer = new RestConsumerImpl();
					Request request = consumer.createNewRequest("http://parc.mogul.se/thalen/restful.nsf/Person");
					String title = mTitleTxt.getText().toString();
					if (StringUtils.isNotEmpty(title)) {
						request.addString("Title", mTitleTxt.getText().toString());
					}
					String body = mBodyTxt.getText().toString();
					if (StringUtils.isNotEmpty(body)) {
						request.addString("FullName", body);
					}
					if (!request.getRequestAttributes().isEmpty()) {
						consumer.executeRequest(request);
					}
				}
				
				setResult(RESULT_OK);
				finish();
			}

		});

	}

	private void populateFields() {
		if (doc != null) {
			mTitleTxt.setText(doc.getProperties().get("Title"));
			mBodyTxt.setText(doc.getProperties().get("FullName"));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		String title = mTitleTxt.getText().toString();
		String body = mBodyTxt.getText().toString();

		if (mRowId == null) {
			long id = mDbHelper.createNote(title, body);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, title, body);
		}
	}
}
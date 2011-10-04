package mogul.demo.android;



import java.util.ArrayList;
import java.util.List;

import mogul.demo.android.consumer.RestConsumer;
import mogul.demo.android.consumer.RestConsumerImpl;
import mogul.demo.android.domain.Document;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListDocuments extends ListActivity {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDIT_ID = Menu.FIRST + 2;

    public final static String ROW_ID = "_rowId";
    
    private NotesDbAdapter mDbHelper;
    
    private final String uri = "http://parc.mogul.se/thalen/restful.nsf/testView";
    
    private List<Document> data = new ArrayList<Document>();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.document_list);
        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());

    }
    
    private void fillData() {
    	RestConsumer consumer = new RestConsumerImpl();
        List<Document> list = consumer.getDocuments(uri);
        this.data = list;
        Log.i("ListDocuments", list.toString());
        
        ArrayList<String> titles = new ArrayList<String>();
        for (Document doc : list) {
        	titles.add(doc.getProperties().get("Title"));
        }
        Log.i("ListDocuments", titles.toString());
        ArrayAdapter<String> notes = new ArrayAdapter<String>(this, R.layout.notes_row, titles);
        setListAdapter(notes);
        
       // registerForContextMenu(notes);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // super.onCreateOptionsMenu(menu);
        //menu.add(0, INSERT_ID, 0, R.string.menu_newdocument);
        //return true;
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;*/
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_newdoc:
                createDocument();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        CharSequence title = ((TextView) info.targetView.findViewById(R.id.text1)).getText();
        menu.setHeaderTitle(title);
        menu.add(0, DELETE_ID, 0, R.string.menu_deletedocument);
        menu.add(0, EDIT_ID, 0, R.string.menu_editdocument); 
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case DELETE_ID:
                mDbHelper.deleteNote(info.id);
                fillData();
                return true;
            case EDIT_ID:
                Intent i = new Intent(this, EditDocument.class);
                i.putExtra(NotesDbAdapter.KEY_ROWID, info.id);
                startActivityForResult(i, ACTIVITY_EDIT);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Intent i = new Intent(this, EditDocument.class);
        Document cur = data.get((int) id);
        i.putExtra(ListDocuments.ROW_ID, cur);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    
    private void createDocument() {
        Intent i = new Intent(this, EditDocument.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    private void deleteDocument(){}
    private void readDocument(){} //Do not use in this phase
 
}
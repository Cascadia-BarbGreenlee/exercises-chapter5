package com.example.mycontactlist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class ContactListActivity extends ListActivity {

	boolean isDeleting = false;
	ContactAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		initListButton();
        initMapButton();
        initSettingsButton();
        initDeleteButton();
             
        ContactDataSource ds = new ContactDataSource(this);
        ds.open();
        final ArrayList<Contact>contacts = ds.getContacts();
        ds.close();
        
        adapter = new ContactAdapter(this, contacts);
        setListAdapter(adapter);
	
        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent,View itemClicked, int position, long id){
        		Contact selectedContact = contacts.get(position);
        		Intent intent = new Intent(ContactListActivity.this,ContactActivity.class);
        		intent.putExtra("contactid", selectedContact.getContactID());
        		startActivity(intent);
        	}
		});
	}
	
	private void initDeleteButton(){
		final Button deleteButton = (Button)findViewById(R.id.ButtonDelete);
		deleteButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(isDeleting){
					deleteButton.setText("Delete");
					isDeleting = false;
				}
				else{
					deleteButton.setText("Done Deleting");
					isDeleting = true;
				}
			}
		});
	}
	
//	@Override
//	public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id){
//		Contact selectedContact = contacts.get(position);
//		if(isDeleting){
//			adapter.showDelete(position, itemClicked, ContactListActivity.this, selectedContact);
//		}
//		else{
//			Intent intent = new Intent(ContactListActivity.this, ContactActivity.class);
//			intent.putExtra("contactid",selectedContact.getContactID());
//			startActivity(intent);
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    private void initListButton(){
    	ImageButton list = (ImageButton)findViewById(R.id.imageButtonList);
    	list.setEnabled(false);
    }
    
    private void initMapButton(){
    	ImageButton map = (ImageButton)findViewById(R.id.imageButtonMap);
    	map.setOnClickListener(new View.OnClickListener() {
    		public void onClick (View v){
    			Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
	
    private void initSettingsButton(){
    	ImageButton settings = (ImageButton)findViewById(R.id.imageButtonSettings);
    	settings.setOnClickListener(new View.OnClickListener() {
    		public void onClick (View v){
    			Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
}

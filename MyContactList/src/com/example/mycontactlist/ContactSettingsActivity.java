package com.example.mycontactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContactSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_settings);
		
		initSettings();
		initSortByClick();
		initSortOrderClick();
		initBackgroundResource();
		initListButton();
        initMapButton();
        initSettingsButton();
        
        String color = getBackgroundResource();
        ScrollView s = (ScrollView)findViewById(R.id.scrollView1);
        if (color == "melon"){
            s.setBackgroundResource(R.color.settings_background_1);	
        } 
        else if(color == "tan"){
        	s.setBackgroundResource(R.color.settings_background_2);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_settings, menu);
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
	
	private void initSortByClick() {
		RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroup1);
		rgSortBy.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
				RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
				if (rbName.isChecked()) {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "contactname").commit();
				}
				else if (rbCity.isChecked()) {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "city").commit();
				}
				else {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").commit();
				}			
			}		
		});
	}

	private void initSortOrderClick() {
		RadioGroup rgSortOrder = (RadioGroup) findViewById(R.id.radioGroup2);
		rgSortOrder.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
				if (rbAscending.isChecked()) {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").commit();
				}
				else {
					getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").commit();
				}			
			}		
		});
	}
	
	private void initBackgroundResource() {
		RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroup3);
		rgSortBy.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton rbMelon = (RadioButton) findViewById(R.id.radioMelon);
				RadioButton rbTan = (RadioButton) findViewById(R.id.radioTan);
				if (rbMelon.isChecked()) {
					getSharedPreferences("MyColorPreferences", Context.MODE_PRIVATE).edit().putString("backgroundcolor", "melon").commit();
				}
				else if (rbTan.isChecked()) {
					getSharedPreferences("MyColorPreferences", Context.MODE_PRIVATE).edit().putString("backgroundcolor", "tan").commit();
				}
				else {
					getSharedPreferences("MyColorPreferences", Context.MODE_PRIVATE).edit().putString("backgroundcolor", "none").commit();
				}			
			}		
		});
	}
	
    private void initListButton(){
    	ImageButton list = (ImageButton)findViewById(R.id.imageButtonList);
    	list.setOnClickListener(new View.OnClickListener() {
    		public void onClick (View v){
    			Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
    
    private void initMapButton(){
    	ImageButton map = (ImageButton)findViewById(R.id.imageButtonMap);
    	map.setOnClickListener(new View.OnClickListener() {
    		public void onClick (View v){
    			Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
    		}
    	});
    }
	
    private void initSettingsButton(){
    	ImageButton settings = (ImageButton)findViewById(R.id.imageButtonSettings);
    	settings.setEnabled(false);
    }

    private void initSettings(){
    	String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield","contactname");
    	String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder","ASC");
    	String colorChoice = getSharedPreferences("MyColorPreferences", Context.MODE_PRIVATE).getString("backgroundcolor", "none");
    	
    	RadioButton rbName = (RadioButton)findViewById(R.id.radioName);
    	RadioButton rbCity = (RadioButton)findViewById(R.id.radioCity);
    	RadioButton rbBirthday = (RadioButton)findViewById(R.id.radioBirthday);
    	if(sortBy.equalsIgnoreCase("contactname")){
    		rbName.setChecked(true);
    	}
    	else if(sortBy.equalsIgnoreCase("city")){
    		rbCity.setChecked(true);
    	}
    	else {
    		rbBirthday.setChecked(true);
    	}
    	
    	RadioButton rbAscending = (RadioButton)findViewById(R.id.radioAscending);
    	RadioButton rbDescending = (RadioButton)findViewById(R.id.radioDescending);
    	if(sortOrder.equalsIgnoreCase("ASC")){
    		rbAscending.setChecked(true);
    	}
    	else{
    		rbDescending.setChecked(true);
    	}
    	
    	RadioButton rbMelon = (RadioButton)findViewById(R.id.radioMelon);
    	RadioButton rbTan = (RadioButton)findViewById(R.id.radioTan);
    	RadioButton rbNone = (RadioButton)findViewById(R.id.radioNone);
    	if(colorChoice.equalsIgnoreCase("melon")){
    		rbMelon.setChecked(true);
    	}
    	else if(colorChoice.equalsIgnoreCase("tan")){
    		rbTan.setChecked(true);
    	}
    	else {
    		rbNone.setChecked(true);
    	}
    }
    
	private String getBackgroundResource() {
		String colorChoice = getSharedPreferences("MyColorPreferences", Context.MODE_PRIVATE).getString("backgroundcolor", "none");
		if(colorChoice.equalsIgnoreCase("melon")){
			return "melon";
		} 
		else if (colorChoice.equalsIgnoreCase("tan")) {
				return "tan";
		}
		else {
			return "none";
		}			
	}
}

package com.example.mycontactlist;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class ContactDataSource {
	private SQLiteDatabase database;
	private ContactDBHelper dbHelper;
	
	public ContactDataSource(Context context){
		dbHelper = new ContactDBHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}

	public boolean insertContact(Contact c){
		boolean didSucceed = false;
		try{
			ContentValues initialValues = new ContentValues();
			
			initialValues.put("contactname", c.getContactName());
			initialValues.put("streetaddress", c.getStreetAddress());
			initialValues.put("city", c.getCity());
			initialValues.put("state", c.getState());
			initialValues.put("zipcode", c.getZipCode());
			initialValues.put("phonenumber", c.getPhoneNumber());
			initialValues.put("cellnumber", c.getCellNumber());
			initialValues.put("email", c.getEMail());
			initialValues.put("birthday", String.valueOf(c.getBirthday().toMillis(false)));
			
			didSucceed = database.insert("contact", null, initialValues) > 0;
		}
		catch (Exception e){
			// Do nothing---will return false if there is an exception
		}
		return didSucceed;
	}

	public boolean updateContact(Contact c){
		boolean didSucceed = false;
		try{
			Long rowId = Long.valueOf(c.getContactID());
			ContentValues updateValues = new ContentValues();
			
			updateValues.put("contactname", c.getContactName());
			updateValues.put("streetaddress", c.getStreetAddress());
			updateValues.put("city", c.getCity());
			updateValues.put("state", c.getState());
			updateValues.put("zipcode", c.getZipCode());
			updateValues.put("phonenumber", c.getPhoneNumber());
			updateValues.put("cellnumber", c.getCellNumber());
			updateValues.put("email", c.getEMail());
			updateValues.put("birthday", String.valueOf(c.getBirthday().toMillis(false)));
			
			didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
		}
		catch (Exception e){
			// Do nothing---will return false if exception
		}
		return didSucceed;
	}
	
	public boolean updateAddress(Contact c, ContactAddress cAdd){
		boolean didSucceed = false;
		try{
			Long rowId = Long.valueOf(c.getContactID());
			ContentValues updateValues = new ContentValues();
			
			updateValues.put("streetaddress", cAdd.getAddress());
	
			didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
		}
		catch (Exception e){
			// Do nothing---will return false if exception
		}
		return didSucceed;
	}
	
	public int getLastContactId(){
		int lastId = -1;
		try{
			String query = "Select MAX(_id) from contact";
			Cursor cursor = database.rawQuery(query,  null);
			
			cursor.moveToFirst();
			lastId = cursor.getInt(0);
			cursor.close();
		}
		catch (Exception e){
			lastId = -1;
		}
		return lastId;
	}
	public String getlastContactName(){
		String contactName = "";
		String streetAddress = "";
		String query = "SELECT contactname, streetaddress FROM contact";		
		try{
			Cursor cursor = database.rawQuery(query, null);
			cursor.moveToFirst();
			
			while(!cursor.isAfterLast()){
				contactName = cursor.getString(0);
				streetAddress = cursor.getString(1);
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch(Exception e){
			
		}
		return contactName + ", " + streetAddress;
	}
	public ArrayList<String> getContactName(){
		ArrayList<String> contactNames = new ArrayList<String>();
		try{
			String query = "Select contactname from contact";
			Cursor cursor = database.rawQuery(query, null);
			
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				contactNames.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch(Exception e){
			contactNames = new ArrayList<String>();
		}
		return contactNames;
	}
	
	public ArrayList<Contact>getContacts(){
		ArrayList<Contact>contacts = new ArrayList<Contact>();
		try{
			String query = "SELECT * FROM contact";
			Cursor cursor = database.rawQuery(query, null);
			
			Contact newContact;
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				newContact = new Contact();
				newContact.setContactID(cursor.getInt(0));
				newContact.setContactName(cursor.getString(1));
				newContact.setStreetAddress(cursor.getString(2));
				newContact.setCity(cursor.getString(3));
				newContact.setState(cursor.getString(4));
				newContact.setZipCode(cursor.getString(5));
				newContact.setPhoneNumber(cursor.getString(6));
				newContact.setCellNumber(cursor.getString(7));
				newContact.setEMail(cursor.getString(8));
				Time t = new Time();
				t.set(Long.valueOf(cursor.getString(9)));
				newContact.setBirthday(t);
				
				contacts.add(newContact);
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch(Exception e){
			contacts = new ArrayList<Contact>();
		}
		return contacts;
	}
	public boolean deleteContact(int contactId){
		boolean didDelete = false;
		try{
			didDelete = database.delete("contact", "_id" + contactId, null) > 0;
		}
		catch(Exception e){
			//do nothing = return value already false
		}
		return didDelete;
	}
	
}
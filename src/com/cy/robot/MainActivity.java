package com.cy.robot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;
import android.provider.ContactsContract.Contacts.Data;

public class MainActivity extends Activity implements HttpGetDataListener,android.view.View.OnClickListener{
	
	private HttpData httpData;
	private List<ListData> lists;
	private ListView lv;
	private EditText sendtext;
	private Button send_btn;
	private String content_str;
	private TextAdapter adapter;
	private String[] welcome_array;
	private double currentTime,oldTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initView();
		
		}
	private void initView(){
		lv=(ListView) findViewById(R.id.lv);
		sendtext=(EditText) findViewById(R.id.sendText);
		send_btn=(Button) findViewById(R.id.send_btn);
		lists=new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter=new TextAdapter(lists, this);
		lv.setAdapter(adapter);
		ListData listData;
		listData=new ListData(getRandomWelcomeTips(), ListData.RECEIVER,getTime());
		lists.add(listData);
	}
	
	private String getRandomWelcomeTips(){
		String welcome_tip=null;
		welcome_array=this.getResources().getStringArray(R.array.welcome_tips);
		int index=(int) (Math.random()*(welcome_array.length-1));
		welcome_tip=welcome_array[index];
		return welcome_tip;
		
	}

	@Override
	public void getDataUrl(String data) {
		//System.out.println(data);
		parseText(data);
		
	}
	private void parseText(String str) {
		try {
			JSONObject jb=new JSONObject(str);
			ListData listData;
			listData=new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	@Override
	public void onClick(View v) {
		getTime();
		content_str=sendtext.getText().toString();
		sendtext.setText("");
		String dropk=content_str.replace(" ", "");
		String droph=dropk.replace("\n", "");
		ListData listData;
		listData=new ListData(content_str, ListData.SEND,getTime());
		lists.add(listData);
		if(lists.size()>30){
			for (int i = 0; i < lists.size(); i++) {
				lists.remove(i);
			}
		}
		adapter.notifyDataSetChanged();
		// TODO Auto-generated method stub
		httpData=(HttpData) new HttpData(
				"http://www.tuling123.com/openapi/api?key=40abb943778c472ea40e9c31e1a3c339&info="
		+droph,this).execute();
	}
	
	private String getTime(){
		currentTime=System.currentTimeMillis();
		SimpleDateFormat format=new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ£ºmm:ss");
		Date curData=new Date();
		String str=format.format(curData);
		if(currentTime-oldTime>=5*60*1000){
			oldTime=currentTime;
			return str;
		}else {
			return "";
		}
		
	}


}

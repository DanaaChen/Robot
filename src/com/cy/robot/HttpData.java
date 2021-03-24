package com.cy.robot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
//�����첽ͨ��
public class HttpData extends AsyncTask<String,Void,String>{
	private HttpClient mHttpClient;//��������
	private HttpGet mHttpGet;//����ʽ
	private HttpResponse mHttpResponse;//����ķ���
	private HttpEntity mHttpEntity;
	private InputStream in;
	private HttpGetDataListener listener;
	private String url;
	
	public HttpData(String url,HttpGetDataListener listener) {//
		this.url=url;
		this.listener=listener;
	}

	@Override
	protected String doInBackground(String... arg0) {//��д����
		try{
			mHttpClient=new DefaultHttpClient();//ʵ�����ͻ���
			mHttpGet=new HttpGet(url);//��get��ʽ��������get��Ҫ���ݵ���һ�����������url,urlͨ�����õ�ǰ�������д��ݣ����Ը����һ�����Ը��õ���
			mHttpResponse=mHttpClient.execute(mHttpGet);
			mHttpEntity=mHttpResponse.getEntity();
			in=mHttpEntity.getContent();
			
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			String line=null;
			StringBuffer sb=new StringBuffer();
			while((line=br.readLine())!=null){//�����Ϊ�գ�һֱ��ȡ
				sb.append(line);//��ȡ�������ݵ�String��buffer��
			}
			return sb.toString();//����ת��ΪString���ͣ���ʱ���Է��ʣ�����Ҫ�������Ȩ�ޡ�
		}catch(Exception e){
			
		}
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result);
		super.onPostExecute(result);
	}

}

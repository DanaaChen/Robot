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
//进行异步通信
public class HttpData extends AsyncTask<String,Void,String>{
	private HttpClient mHttpClient;//用来请求
	private HttpGet mHttpGet;//请求方式
	private HttpResponse mHttpResponse;//请求的返回
	private HttpEntity mHttpEntity;
	private InputStream in;
	private HttpGetDataListener listener;
	private String url;
	
	public HttpData(String url,HttpGetDataListener listener) {//
		this.url=url;
		this.listener=listener;
	}

	@Override
	protected String doInBackground(String... arg0) {//复写方法
		try{
			mHttpClient=new DefaultHttpClient();//实例化客户端
			mHttpGet=new HttpGet(url);//用get方式进行请求，get需要传递的是一个具体请求的url,url通过调用当前这个类进行传递，所以该类的一个可以复用的类
			mHttpResponse=mHttpClient.execute(mHttpGet);
			mHttpEntity=mHttpResponse.getEntity();
			in=mHttpEntity.getContent();
			
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			String line=null;
			StringBuffer sb=new StringBuffer();
			while((line=br.readLine())!=null){//如果不为空，一直读取
				sb.append(line);//读取到的数据到String的buffer中
			}
			return sb.toString();//返回转化为String类型，此时可以访问，但是要添加网络权限。
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

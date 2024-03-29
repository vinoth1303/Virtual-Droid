package com.example.dialserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;

/**
 * Creates the jetty server and manages it
 * 
 * @author Hathy
 *
 */
public class MyHttpServer{
    
	
    ServletContextHandler handler;
    Server server;
    Context context;
    
    /**
     * Due to a bug in Android 2.x, it is recommended that you
     * do this. I have seen the bug only on the emulators.
     */
    static{
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            System.setProperty(
                   "java.net.preferIPv6Addresses"
                 , "false");
        }
    }
        
    /**
     * Create the jetty server and let it listen at port 8080
     */
    MyHttpServer(Context context){
        
        this.context=context;
        server=new Server(8000); // The port is being set here
        handler=new ServletContextHandler();
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder(new SmsServlet(context)),"/sipclient");
        handler.addServlet(new ServletHolder(new SimpleServlet(context)),"/sms");
        handler.addServlet(new ServletHolder(new CallServlet(context)),"/call");
        server.setHandler(handler);
        
        
    }
    
    /*
     * We better run the server in a separate thread.
     */
    public void start(){
        new Thread(){
            public void run(){
                try{
                    server.start();
                }catch(Exception e){
                    Log.e("SERVER_START",e.toString());
                }
            }
        }.start();
    }
    
    public String getIPAddress(){
        ConnectivityManager con;
        con = (ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(con.getNetworkInfo(
                 ConnectivityManager.TYPE_WIFI)
                .isConnected()){
            WifiManager wifi;
            wifi=(WifiManager)context
                    .getSystemService(Context.WIFI_SERVICE);
            int ip=wifi.getConnectionInfo().getIpAddress();
            return Formatter.formatIpAddress(ip);
        }else{
            return "You are not connected to WIFI";
        }
    }
        
    
    public void stop(){
        try{
            server.stop();
        }catch(Exception e){
            Log.e("SERVER_STOP",e.toString());
        }
    }
}
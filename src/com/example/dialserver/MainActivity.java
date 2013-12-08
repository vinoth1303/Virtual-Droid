package com.example.dialserver;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    MyHttpServer server;
    TextView ipAddress;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ipAddress=new TextView(this);
        setContentView(ipAddress);
        server=new MyHttpServer(MainActivity.this);
        server.start();

        ipAddress.setText("Your IP Address :" + server.getIPAddress());
    }

    @Override
    protected void onDestroy() {    
        super.onDestroy();
        server.stop();
    }
        
}

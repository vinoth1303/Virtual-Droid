package com.example.dialserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

public class SimpleServlet extends HttpServlet{
	private Context context;
    
    private static final long serialVersionUID = -621262173529602L;
    
    public SimpleServlet(Context context) {
		this.context=context;
	}
    /**
     * This method is called when the user enters the URL in the
     * browser. 
     */
    @Override
    protected void doGet(HttpServletRequest req
                         , HttpServletResponse resp)
                         throws ServletException, IOException {    
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out=resp.getWriter();
        out.println("<html>" +
                "<title>Send SMS</title>" +
          	  "<style type=\"text/css\"> a { text-decoration:none; font:14pt/16pt fantasy,cursive,Serif} a:link {color:#009933;} a:visited {color:#009933;} a:hover {color:#52cc29;} a:active {color:yellow;}</style>" +
          	  "<div style='font-size:18px' align='center'><a href='./call'>Radio Call   </a>|<a href='./sms'>   SMS   </a>|<a href='./sipclient'>   SIP Call</a></div><hr>" +
                "<body bgcolor='#ffe0b2'>" +
                "<div align='center'> <form method='post'>" +
                "<h2>Number</h2><input type='text' maxlength='10' style='font-size:24px' name='phone_number' id='phone_number'/><br/>" +
                "<h2>Message</h2><textarea type='text' style='font-size:24px;' name='message'></textarea><br/>" +
                "<br/><input type='submit' style='font-size:24px' value='Send'/>" +
                "</form></div>" +
                "<div align='center'><h2>Contacts present in the device:</h2></div>" +
                "<style type='text/css'>" +
  			  ".tftable {font-size:12px;color:#fbfbfb;border-width: 1px;border-color: #686767;border-collapse: collapse;}"+
  			  ".tftable th {font-size:12px;background-color:#171515;border-width: 1px;padding: 8px;border-style: solid;border-color: #686767;text-align:left;}" +
  			  ".tftable tr {background-color:#2f2f2f;}" +
  			  ".tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #686767;}" +
  			  ".tftable tr:hover {background-color:#171515;}" +
  			  "</style>" +
  			  "<table class='tftable' align='center' border='1'>" +
  			  "<tr><th>Name</th><th>Telephone Number</th></tr>");
          Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
          while (phones.moveToNext())
          {
          String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
          out.println("<tr><td>" + name + "</td>");
          String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
          phoneNumber = phoneNumber.replaceAll("\\D+","");
          out.println("<td onclick=\"javascript:document.getElementById('phone_number').value=this.innerText;\">" + phoneNumber + "</td></tr>");
          }
          phones.close();
          out.println("</table>" +
                "</body>" +
                "</html>");
    }
    
    /**
     * This method is called when the 'Go' button is pressed
     */    
    @Override
    protected void doPost(HttpServletRequest req
                          , HttpServletResponse resp)
                          throws ServletException, IOException {    
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out=resp.getWriter();
        String phoneNumber=req.getParameter("phone_number");
        String message=req.getParameter("message");
        
        if(phoneNumber==null || message==null) {
            out.println("Invalid input");
            return;
        }

        sendSMS(phoneNumber, message);
       
        out.println("<html>" +
                "<title>SMS Sent !</title>" +
            	  "<style type=\"text/css\"> a { text-decoration:none; font:14pt/16pt fantasy,cursive,Serif} a:link {color:#009933;} a:visited {color:#009933;} a:hover {color:#52cc29;} a:active {color:yellow;}</style>" +
              	  "<div style='font-size:18px' align='center'><a href='./call'>Radio Call   </a>|<a href='./sms'>   SMS   </a>|<a href='./sipclient'>   SIP Call</a></div><hr>" +
                    "<body bgcolor='#ffe0b2'>" +
                "<h1 align='center'>SMS sent to " + phoneNumber + ".</h1>" +
                "</body>" +
                "</html>");
    }
	private void sendSMS(String phoneNumber, String message){
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    
}
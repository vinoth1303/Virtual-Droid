package com.example.dialserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

/**
 * This servlet is used to send the SMS.
 * @author Hathy
 */
public class SmsServlet extends HttpServlet{
    private Context context;
    private static final long serialVersionUID = -61789602L;    
    
    public SmsServlet(Context context) {
		this.context=context;
	}

	/**
     * This method is called when the user enters the URL in the
     * browser. 
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {    
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out=resp.getWriter();
        out.println("<html>" +
              "<title>Dial and Call</title>" +
              "<body bgcolor='#d0bfbf'><h1 style='color:#2f2f2f'>Enter the name of the contact which you wish to call</h1>" +
              "<form method='post'>" +
              "<div align='center'><input style='font-size:24px' type='text' name='phone_number' /><br/>" +
              "<input type='image' src='data:image/png;base64,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' alt='Call'/></div>" +
              "</form>" +
              "<h2>SIP Contacts present in the device:</h2>" +
              "<style type='text/css'>" +
			  ".tftable {font-size:12px;color:#fbfbfb;width:100%;border-width: 1px;border-color: #686767;border-collapse: collapse;}"+
			  ".tftable th {font-size:12px;background-color:#171515;border-width: 1px;padding: 8px;border-style: solid;border-color: #686767;text-align:left;}" +
			  ".tftable tr {background-color:#2f2f2f;}" +
			  ".tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #686767;}" +
			  ".tftable tr:hover {background-color:#171515;}" +
			  "</style>" +
			  "<table class='tftable' border='1'>" +
			  "<tr><th>Name</th><th>SIP Address</th></tr>" +
			  "<tr><td>Vinoth</td><td>vinoth1303@sip.linphone.org</td></tr>" +
			  "<tr><td>Ramsu</td><td>ramsu1@sip.linphone.org</td></tr>" +
			  "<tr><td>Parthiban</td><td>parthiban88@sip.linphone.org</td></tr>" +
			  "<tr><td>Yogesh</td><td>yogeshajagtap@sip.linphone.org</td></tr>" +
			  "</table>" +
			
				              "</body>" +
				              "</html>");

    }
    
    /**
     * This method is called when the 'Send' button is pressed
     */    
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {    
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out=resp.getWriter();
        String phoneNumber=req.getParameter("phone_number");
        String message=req.getParameter("message");
        
        if(phoneNumber==null ) {
            out.println("Invalid input");
            return;
        }
        
        if(phoneNumber.equals("Vinoth")){
        	phoneNumber="vinoth1303@sip.linphone.org";
        }
        else if(phoneNumber.equals("Ramsu")){
        	phoneNumber="ramsu1@sip.linphone.org";
        }
        else if(phoneNumber.equals("Parthiban")){
        	phoneNumber="parthiban88@sip.linphone.org";
        }
        else if(phoneNumber.equals("Yogesh")){
        	phoneNumber="yogeshajagtap@sip.linphone.org";
        }

        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("sip:" + phoneNumber)));

        //sendSMS(phoneNumber, message);
        
        out.println("<html>" +
                "<title>Dialling the number</title>" +
                "<body>" +
                "Dialling " + phoneNumber + " ... " +
                "</body>" +
                "</html>");
    }

    
	private void sendSMS(String phoneNumber, String message){
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}

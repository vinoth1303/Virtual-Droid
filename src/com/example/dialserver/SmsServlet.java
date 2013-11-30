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
              "<body bgcolor='#d0bfbf'><h1 style='color:#2f2f2f'>Enter SIP Address</h1>" +
              "<form method='post'>" +
              "<input type='text' name='phone_number' value='2142888748'/><br/>" +
              "<input type='submit' value='Call'/>" +
              "</form>" +
              "<style type='text/css'>" +
			  ".tftable {font-size:12px;color:#fbfbfb;width:100%;border-width: 1px;border-color: #686767;border-collapse: collapse;}"+
			  ".tftable th {font-size:12px;background-color:#171515;border-width: 1px;padding: 8px;border-style: solid;border-color: #686767;text-align:left;}" +
			  ".tftable tr {background-color:#2f2f2f;}" +
			  ".tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #686767;}" +
			  ".tftable tr:hover {background-color:#171515;}" +
			  "</style>" +
			  "<table class='tftable' border='1'>" +
			  "<tr><th>Name</th><th>SIP Address</th></tr>" +
			  "<tr><td>Row:1 Cell:1</td><td>Row:1 Cell:2</td></tr>" +
			  "<tr><td>Row:2 Cell:1</td><td>Row:2 Cell:2</td></tr>" +
			  "<tr><td>Row:3 Cell:1</td><td>Row:3 Cell:2</td></tr>" +
			  "<tr><td>Row:4 Cell:1</td><td>Row:4 Cell:2</td></tr>" +
			  "<tr><td>Row:5 Cell:1</td><td>Row:5 Cell:2</td></tr>" +
			  "<tr><td>Row:6 Cell:1</td><td>Row:6 Cell:2</td></tr>" +
			  "<tr><td>Row:7 Cell:1</td><td>Row:7 Cell:2</td></tr>" +
			  "<tr><td>Row:8 Cell:1</td><td>Row:8 Cell:2</td></tr>" +
			  "<tr><td>Row:9 Cell:1</td><td>Row:9 Cell:2</td></tr>" +
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

        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));

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

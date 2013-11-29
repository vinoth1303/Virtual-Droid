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
                "<title>SMS Form</title>" +
                "<body>Hey Ramsu ! Send Text to My Phone !" +
                "<form method='post'>" +
                "Phone # : "+
                "<input type='text' name='phone_number' value='4692167255'/><br/>" +
                "Message : "+
                "<input type='text' name='message'/><br/>"+
                "<input type='submit' value='Send'/>" +
                "</form>" +
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
        
        if(phoneNumber==null || message==null) {
            out.println("Invalid input");
            return;
        }

       
        String phonenumber = "4692167255";
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber)));

        //sendSMS(phoneNumber, message);
        
        out.println("<html>" +
                "<title>SMS Form</title>" +
                "<body>" +
                "Message sent to " + phoneNumber +
                "</body>" +
                "</html>");
    }
//    public void init(ServletConfig config) throws ServletException 
//    { 
//    	try {
//			this.context = 
//        (android.content.Context)config.getServletContext().getAttribute("org.mortbay.ijetty.context"); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//      
//    }  
    
	private void sendSMS(String phoneNumber, String message){
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}

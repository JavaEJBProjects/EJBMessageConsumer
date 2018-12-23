package authConsumer.mdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import com.java.ejb.authConsumer.dao.DataSourceFactory;
import com.sun.appserv.jdbc.DataSource;

/**
 * Message-Driven Bean implementation class for: AuthConsumerMDB
 *
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"
		) }, 
		mappedName = "AuthJMSQueue")
public class AuthConsumerMDB implements MessageListener {

    /**
     * Default constructor. 
     */
    public AuthConsumerMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try{
    		DataSource ds=null;
    		Connection conn = null;
    	    Statement stmt = null;
    	    ResultSet rs = null;
            TextMessage msg=(TextMessage)message;  
            
            StringTokenizer tokenizer = new StringTokenizer(msg.getText(), "|");
            String name = null;
            String address = null;
            String mobile = null;
    		while (tokenizer.hasMoreTokens()) 
    		{
    			 
    			 name=tokenizer.nextToken();
                 address=tokenizer.nextToken();
                 mobile=tokenizer.nextToken();
    		} 

            System.out.println(name+":"+address+":"+mobile);
            
            
            
            try {
				ds=DataSourceFactory.getMySQLDataSource();	
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("following message is received:"+msg.getText());  
            conn=ds.getConnection();
            stmt=conn.createStatement();
            int n=stmt.executeUpdate("insert into user values('"+name+"','"+address+"','"+mobile+"');");
            if(n>0)
            	System.out.println("Sql Query-->insert into user values('"+name+"','"+address+"','"+mobile+"');");
            	System.out.println("Database Operation Done Successfully");
            }catch(Exception e){System.out.println(e);}  
        
    }

}

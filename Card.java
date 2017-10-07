
package business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * @author Katelyn
 */
public class Card {
    
private int AcctNo;
private double Climit, Baldue;
private String actmsg, errmsg;

public Card()//constructor for a new account
{
    this.AcctNo = 0;
    this.Climit = 0;
    this.Baldue = 0;
    this.actmsg = "";
    this.errmsg = "";
    
    while (this.AcctNo == 0)
    {
        try
        {
            this.AcctNo = (int) (Math.random() * 1000000);
            BufferedReader in = new BufferedReader(new FileReader("CC"+this.AcctNo+".txt"));
            //acctno already exists
            in.close();//close the buffered reader
            this.AcctNo = 0; //reset acctno to 0 so loop goes around again
        }
        catch(IOException e)
        {
            //desired result
            this.Climit = 1000;
            if(writestatus())
            {
                this.actmsg = "Account " + this.AcctNo + " was opened.";
                writelog(this.actmsg);
            }
            else // if we failed to write the status file
            {
                //this.errmsg = "Failed status file update: " + this.AcctNo;
                this.AcctNo = -1;
            }
            
        }
    }//end of while
} //empty constructor -- doesn't need any parameters
public Card (int acno)
{
    this.errmsg = "";
    this.actmsg = "";
    this.Climit = 0;
    this.Baldue = 0;
    this.AcctNo = acno;
    
    try
    {
        BufferedReader in = new BufferedReader(new FileReader("CC"+this.AcctNo + ".txt"));
        this.Climit = Double.parseDouble(in.readLine());
        this.Baldue = Double.parseDouble(in.readLine());
        in.close();
        this.actmsg = "Account " + this.AcctNo + " re-opened.";
    }
    catch(Exception e)
    {
        this.errmsg = "Unable to open account: " + this.AcctNo;
        this.AcctNo = 0;
    }
}
private boolean writestatus()
{
    try
    {
        PrintWriter out = new PrintWriter(new FileWriter("CC"+this.AcctNo+".txt"));
        out.println(this.Climit);
        out.println(this.Baldue);
        out.close(); //must close printwriter
    }
    catch(IOException e)
    {
        this.errmsg = "Status file update error: " + e.getMessage();
        return false;
    }
    return true;
}

private void writelog(String msg)
{
    Calendar cal = Calendar.getInstance();
    DateFormat df = DateFormat.getDateTimeInstance();
    String ts = df.format(cal.getTime());
    
    try
    {
        PrintWriter out = new PrintWriter(new FileWriter("CCL"+this.AcctNo+".txt",true));
        out.println(ts+ "\t" + msg);
        out.close();//flush buffer
    }
    catch(IOException e)
    {
        this.errmsg = "Log file update error: " + e.getMessage();
    }
}
    
public int getAcctNo()
{
    return this.AcctNo;
}

public double getCLimit()
{
    return this.Climit;
}

public double getBalDue()
{
    return this.Baldue;
}

public double getAvailCr()
{
    return(this.Climit - this.Baldue);
}

public String getErrMsg()
{
    return this.errmsg;
}

public String getActMsg()
{
    return this.actmsg;
}

public void setCharge(double chgamt, String chgdesc)
{
    this.errmsg = "";
    this.actmsg = "";
    if(this.AcctNo <= 0)
    {
        this.errmsg = "Charge attempt on non-active account.";
        return ;
    }
    if(chgamt <= 0)
    {
        this.actmsg = "Charge of " + chgamt + " declined: " +
                " value must be a positive number.";
        writelog(this.actmsg);
    }
    else if (this.Baldue + chgamt > this.Climit)
    {
        this.actmsg = "Charge of " + chgamt + " declined: " +
                " over limit.";
        writelog(this.actmsg);
    }
    else if (chgdesc.isEmpty())
    {
        this.actmsg = "Charge of " + chgamt + " declined: " +
                " missing charge desc.";
        writelog(this.actmsg);
    }
    else
    {
        this.Baldue += chgamt;
        if(writestatus())
        {
            this.actmsg = "Charge of "  + chgamt + " for " +
                    chgdesc + " posted.";
            writelog(this.actmsg);
        }
        else// negates the previous charge on the account if writestatus() fails
        {
            this.Baldue -= chgamt;
        }
    }
    
}

public void getPmt(double pmt)
{
    this.errmsg = "";
    this.actmsg = "";
    if(this.AcctNo <= 0)
    {
        this.errmsg = "Payment attempt on non-active account.";
        return ;
    }
    if(pmt <= 0)
    {
        this.actmsg = "Payment of " + pmt + " declined: " +
                " value must be a positive number.";
        writelog(this.actmsg);
    }
    else
    {
        this.Baldue -= pmt;
        if(writestatus())
        {
            this.actmsg = "Payment of "  + pmt + " posted.";
            writelog(this.actmsg);
        }
    }
}//end of getPmt

public void setCreditInc(double crinc)
{
    this.errmsg = "";
    this.actmsg = "";
    if(this.AcctNo <= 0)
    {
        this.errmsg = "Payment attempt on non-active account.";
        return ;
    }
    if(crinc <= 0)
    {
        this.actmsg = "Credit Increase of " + crinc + " declined: " +
                " value must be a positive number.";
        writelog(this.actmsg);
    }
    else
    {
        
        if(writestatus())
        {
            if (crinc % 100 == 0)
            {
                int Rdm = (int) (Math.random() * 100);
                if (Rdm >=51)
                {
                    this.actmsg = "Credit Increase of  " + crinc  + " declined";
                    writelog(this.actmsg);
                }
                else if (Rdm <= 50)
                {
                    this.Climit += crinc;
                    this.actmsg = "Credit Increase of "  + crinc + " granted.";
                    writelog(this.actmsg);
                }
            }
            else
            {
                this.actmsg = "Credit increases must be made in multiples of 100";
            }
        }
    }
}

public void setRate(double intR)
{
    this.errmsg = "";
    this.actmsg = "";
    if(this.AcctNo <= 0)
    {
        this.errmsg = "Payment attempt on non-active account.";
        return ;
    }
    else if(intR <= 0 || intR > 1)
    {
        this.errmsg = "Rate invalid. Must be 0<R>1";
    }
    else
    {
        this.Baldue += this.Baldue*(intR / 12.0);
        this.actmsg = "Rate applied to Balance Due.";
        writelog(this.actmsg);
    }
}
public ArrayList<String> getLog()
{
    this.errmsg = "";
    this.actmsg = "";
    String s;
    ArrayList<String> hist = new ArrayList<>();
    
    if (this.AcctNo <= 0)
    {
        this.errmsg = "Log request on non-acive account.";
        return hist;
    }
    try
    {
        BufferedReader in = new BufferedReader(new FileReader("CCL"+this.AcctNo+".txt"));
        s = in.readLine();
        while (s != null)
        {
            hist.add(s);
            s = in.readLine();
        }
        in.close();
        this.actmsg = "Log returned for account: " + this.AcctNo;
    }
    catch(Exception e)
    {
        this.errmsg = "Failure on read of log file for account: " + this.AcctNo;
    }
    return hist;
}
}//end of card.java class

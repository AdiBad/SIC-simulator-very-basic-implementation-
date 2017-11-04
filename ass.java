/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysic;

import static java.lang.System.err;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author dell
 */
public class ass {

Connection con=null;
ResultSet rs=null;
PreparedStatement pat=null;

    public String start;
    public int nol;
    public String filename;
    public String symtab[]=new String[10];
    public String optab[]=new String[10];
    public String variable[]=new String[10];
    public String mem[]=new String[20];
    public String hexf=new String();
    String t=new String();
    String sql = new String();
            
ass(){con=Connect.ConnectDB();}
    public String addHex(String m, int i)
    {
        
        //String num1 = m;//Hexcode
        int n1=Integer.parseInt(m,16); //actual number, can be added to
        
        int j=0,k=0, ncopy = n1;
        
        if(symtab[i].equals("0") && !optab[i].equals("START"))   //2 terms wala column, has only opcode
        {
             sql = "SELECT opcode FROM opcode WHERE Mnemonic='"+optab[i]+"';";
               try
               {
                //    System.out.println("ENTEREDD");
                   
                        pat=con.prepareStatement(sql);
                        rs=pat.executeQuery();
                    //System.out.println("Fine3");
                   t="0";
                    while(rs.next())
                    t= rs.getString(1);
               }
               catch(Exception e)
               {
                   System.out.println(err);
                   //System.err.println("HOWDY YALL, SQL RAN INTO SOME ERRORS LOL, TRY AGAIN LATER MAYBE? n1="+n1+sql);
               }
                // System.out.println("***********************n1="+n1);
               
               //n1+=Integer.parseInt(t,16);   
               System.out.println("n1 = "+n1+ " t from optab = "+t);
               t=t.concat(mem[i]);
               mem[i]=t;
               //n1=00;
                System.out.println("mem[i] = "+mem[i]);
        }
        else                        //3 terms, has a label segment or declaring a word
        { 
        if("WORD".equals(optab[i]))
            n1=n1+3;
        else if("RESW".equals(optab[i]))
        {
           // for(k=0; k<10; k++)
             //   if(optab[k]==variable[i])
            //System.err.println(" n1 = "+n1 +"   "+Integer.toHexString(n1));
                n1=n1+3*Integer.parseInt(variable[i]);
                //System.err.println("RESW variable[i] = "+variable[i]+" n1 = "+n1+"   "+Integer.toHexString(n1));
        }
        else if("RESB".equals(optab[i]))
        {
         n1=n1+Integer.parseInt(variable[k]);
        }
        else if("BYTE".equals(optab[i]))
        {
         n1=n1+Integer.parseInt(variable[k]);
        }
        else
        {
            if(!optab[i].equals("START"))
            {System.err.println("\nThere might be an error in OPTAB entry, kindly check again :)\n");
        System.out.println("This was for n1 = "+n1+optab[i]);}
            //n1+=0;
        }
        }
        if(ncopy==n1)
            n1+=3;
        String c = Integer.toHexString(n1);
        System.out.println("c = "+c);
        return c;
    }
    
    public void decide()
    {
        
        int i=0,k=0;
        mem[0]=start;
        System.out.println("File output..................\n"+hexf);
       
        for(i=1; i<nol; i++)
        {
            mem[i]=addHex(mem[i-1],i-1);//only adds memory by 3 or operand size
            System.out.println(" mem[i] = "+mem[i]);
              String zero = "0000";
                zero = zero.concat(mem[i]);
                mem[i] = zero.substring(zero.length()-4);
               //if(!symtab[i].equals("0"))//its a label
              //  for(k=0; k<nol;k++) //parse the list of symtab to find where that variable is defined
                //    if(variable[i].equals(symtab[k])) //take the current variable and determine its memory from its defined variable posn
                  //      mem[i]=mem[i].concat(Integer.toString(Integer.parseInt(variable[k]))); //add that integral value to mem cuz its not a memory space, need not convert to hex
           // hexf+=mem[i];
           //System.out.println("Try "+i+"   "+hexf);
        }
        
//following code means we've reached declaration, need not be in hexcode
       /* for(i=1; i<nol; i++)
        {
            if(!symtab[i].equals("0"))//its a label
                for(k=0; k<nol;k++) //parse the list of symtab to find where that variable is defined
                    if(variable[i].equals(symtab[k])) //take the current variable and determine its memory from its defined variable posn
                    {mem[i]=mem[i]+Integer.toString(Integer.parseInt(variable[k])); //add that integral value to mem cuz its not a memory space, need not convert to hex
                        System.out.println(" variable[k] = "+variable[k]+"    mem[i] = "+mem[i]);}
            // hexf+=mem[i];
           //System.out.println("Try "+i+"   "+hexf);
        }*/
        
         int n1=Integer.parseInt(mem[i-1],16);
         int n2=Integer.parseInt(start);
         n1=n1-n2;
         for(i=1;i<nol;i++)
         {
             if(mem[i].length()>4)
             hexf+=mem[i];
             else
             {
                 hexf+="'"+mem[i];      //printng memory addresses of the variable definition
             }
         }
         filename = filename.concat("         ");
         hexf = "H"+filename.toUpperCase().substring(0,7)+" "+start+Integer.toHexString(n1)+"\nT"+ hexf;
         //System.out.printf(Integer.toHexString(n1));
         hexf = hexf+"\nE"+mem[i-1]+"\n";
         outputframe ou=new outputframe();
         ou.filename=filename;
         ou.owt=hexf;
         ou.setVisible(true);
         
         System.out.printf("\n"+hexf);
         
    }
}

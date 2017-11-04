/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysic;
//import java.util.Arrays;
import java.*;
/**
 *
 * @author dell
 */

public class sicoptdata 
{
    /*
START 1010
LDA FOUR
ADD FIVE
FOUR RESW 4
FIVE RESW 5
    
    */
    public String txt= "START 1000\nLDA Bile\nADD Bile\nBile WORD 1";//default
    public String fn;
    
    @SuppressWarnings("empty-statement")
    public void func()
    {
       // System.out.print("Aha");
   String start = new String(); //get from frame startadress
   //String txt; // get from user text in frame
   
   //txt = "START 1000\nLDA Bile\nADD Bile\nBile WORD 1";//default
   //inputframe i1=new inputframe();
   //txt=i1.user;
   String lines[] = txt.split("\\r?\\n");
   String symtab[]=new String[10];
   String optab[]=new String[10], variable[]=new String[10];
   int i,j,nol = lines.length;
   //String memory[] = new String[20];
   String temp[] = null;
   
   //System.out.println("lines = "+lines[0]);
   int not=0;
   for(i=0;i<nol;i++){
       temp = lines[i].split("\\s");
       not=temp.length;
       
       symtab[i]="0"; 
       optab[i]="0"; 
       variable[i]="0";
       
       for(j=0;j<not;j++)
       {
           //System.out.println(i+"temp = "+not+temp[j]+j); 
            if(not==3)
            {   
                symtab[i]=temp[j];j++;optab[i]=temp[j];j++;variable[i]=temp[j];
            }
            if(not==2)
            {
                optab[i]=temp[j];j++;variable[i]=temp[j];
            }
       }
   }
   
   
   //System.out.println(".........................nol = "+nol+"..................");
   for(i=0;i<nol;i++)
   {
           System.out.println("symtab["+i+"] = "+symtab[i]+"\t\t optab["+i+"] = "+optab[i]+"\t\tvariable["+i+"] = "+variable[i]);
   }

/////////////////HEXCODE TO NUMBER TO HEXCODE STRING
/*
   String num1 = "12";//Hexcode
   int n1=Integer.parseInt(num1,16); //actual number, can be added to
   String c = Integer.toHexString(+n1);
   */

   //System.out.println(".......................................num = "+num1+"...........n = "+n1+"..........conv1 = "+c);
  if("START".equals(optab[0]))
  {start = variable[0]; }
  String zero = "0000";
  zero = zero.concat(start);
  start = zero.substring(zero.length()-4);
  
 /*
  ***************************************************************************************************************/
  ass ho = new ass();
  ho.start=start;
  ho.symtab=symtab;
  ho.optab=optab;
  ho.nol=nol;
  ho.filename=fn;
  ho.variable=variable;
  ho.decide();
  /***************************************************************************************************************
*/
 // ho.start=start;
  
  
  }
}
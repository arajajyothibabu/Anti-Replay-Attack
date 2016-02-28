import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;
/*<applet code=Calculator width=550 height=600></applet>*/
public class Calculator extends Applet implements ActionListener
{
       Button b[]=new Button[11];
       Button bt,add,sub,mul,div,result,mod;
       TextField t1,t2,t3,t4;
       int v1,v2;
       String x;
       Font f=new Font("Times New Roman",Font.BOLD,40);
        Font k=new Font("Times New Roman",Font.BOLD,20);
    public void init()
    {
        setLayout(new GridLayout(5,4,2,2));
        t1=new TextField(20);
        t1.setFont(f);
        t2=new TextField(20);
        t2.setFont(f);
        t3=new TextField(20);
        t3.setFont(f);
        bt=new Button("Clear");
        bt.addActionListener(this);
        result=new Button("=");
        result.setFont(f);
        result.setBackground(Color.red);
        result.addActionListener(this);
        t3.setBackground(Color.orange);
        add(t1);
        add(t2);
        add(result);
        add(t3);
        add(bt);
        bt.setBackground(Color.green);
        bt.setFont(f);
        for(int i=0;i<10;i++)
        {
            if(i==9)
                b[i]=new Button("0");
            else
                b[i]=new Button(String.valueOf(i+1));
            b[i].addActionListener(this);
            add(b[i]);
            b[i].setFont(f);
            b[i].setBackground(Color.magenta);
        }  
        add=new Button("+");
        add.setFont(f);
        add.addActionListener(this);
        sub=new Button("-");
        sub.setFont(f);
        sub.addActionListener(this);
        mul=new Button("*");
        mul.setFont(f);
        mul.addActionListener(this);
        div=new Button("/");
        div.setFont(f);
        div.addActionListener(this);
        mod=new Button("%");
        mod.setFont(f);
        mod.addActionListener(this);
        add(mod);
        add(add);
        add(sub);
        add(mul);
        add(div);
    }
    public void actionPerformed(ActionEvent ae)
    {
        String s=ae.getActionCommand();
        if(s=="+"||s=="-"||s=="/"||s=="*"||s=="%")
        {
            x=s;
            t2.setText(s);
            v1=Integer.parseInt(t1.getText());
            t1.setText("");
        }
        else if(s=="=")
        {
            float r=0;
            t1.setFont(k);
            v2=Integer.parseInt(t1.getText());
            if(x=="+")
            {
                r=v1+v2;
                t1.setText(String.valueOf(v1)+"+"+String.valueOf(v2));
            }    
            else if(x=="-")
            {
                r=v1-v2;
                t1.setText(String.valueOf(v1)+"-"+String.valueOf(v2));
            }
            else if(x=="*")
            {
                r=v1*v2;
                t1.setText(String.valueOf(v1)+"*"+String.valueOf(v2));
            }
            else if(x=="/")
            {
                r=v1/v2;
                t1.setText(String.valueOf(v1)+"/"+String.valueOf(v2));
            }
            else if(x=="%")
            {
                r=((float)v1/(float)v2)*100;
                t1.setText(String.valueOf(v1)+" in "+String.valueOf(v2));
            }
            t3.setText(String.valueOf(r));
        }
        else
        {
            t1.setFont(f);
            t1.setText(t1.getText()+s);
        }
         if(s.equals("Clear"))
        {
            t1.setText("");
            t2.setText("");
            t3.setText("");
        }
        repaint();
    }
}
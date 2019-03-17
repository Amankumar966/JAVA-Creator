import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jeditor extends MouseAdapter implements ActionListener  {
    JFrame f1;
    JFrame f3,f4;
    JButton find,close1,replace,replaceall,find1,close2;
    JTextField tf1,tf2,tr;
    JMenuBar mb;
    JMenu f,ed,b,cl;
    JMenuItem n,o,s,sa,e,fi,fr,c,co,p,com,r,clo,cloall;
    JSplitPane js1;
    final JTabbedPane jtab1;
    JTextArea jt1[]=new JTextArea[1000];
    JTextArea jt2=new JTextArea();
    JScrollPane jsp1[]=new JScrollPane[1000];JScrollPane jsp2;
    FileDialog fd1;
    Boolean flag1=true;
    String gf,gdir;
    Pattern pattern;
    Matcher matcher;
    int abc;
    //for findingvalue1
    int r1,r2,x,y;
    String tavalue;
    int pos;

    int count=0;
    public Jeditor(){
        //fOR fIND AND rEPLACE
        find=new JButton("Find Next");
        close1=new JButton("Close");
        // fOR fIND AND rEPLACE

        f1= new JFrame();
        f1.setSize(400,400);
        f1.setLayout(new BorderLayout());
        mb=new JMenuBar();
        f=new JMenu("File");
        ed=new JMenu("Edit");
        b=new JMenu("Build");
        cl=new JMenu("Close");
        n=new JMenuItem("New");
        o=new JMenuItem("Open");
        s=new JMenuItem("Save");
        sa=new JMenuItem("Saveas");
        e=new JMenuItem("Exit");
        fi=new JMenuItem("Find");
        fr=new JMenuItem("Find&replace");
        c=new JMenuItem("Cut");
        co=new JMenuItem("Copy");
        p=new JMenuItem("Paste");
        com=new JMenuItem("Compile");
        r=new JMenuItem("Run");
        clo=new JMenuItem("CLose");
        cloall=new JMenuItem("CloseAll");
        n.addActionListener(this);
        o.addActionListener(this);
        s.addActionListener(this);
        sa.addActionListener(this);
        e.addActionListener(this);
        fi.addActionListener(this);
        fr.addActionListener(this);
        c.addActionListener(this);
        co.addActionListener(this);
        p.addActionListener(this);
        com.addActionListener(this);
        r.addActionListener(this);
        clo.addActionListener(this);
        cloall.addActionListener(this);
        f.add(n);f.add(o);f.add(s);f.add(sa);f.add(e);
        ed.add(fi);ed.add(fr);ed.add(c);ed.add(co);ed.add(p);
        b.add(com);b.add(r);
        cl.add(clo);cl.add(cloall);
        mb.add(f);mb.add(ed);mb.add(b);mb.add(cl);
        f1.add(mb,BorderLayout.NORTH);
        jtab1=new JTabbedPane(JTabbedPane.BOTTOM);
        jt2=new JTextArea();
        jsp2=new JScrollPane(jt2);
        js1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,jtab1,jsp2);
        jtab1.setMaximumSize(new Dimension(50,50));
        jt2.setSize(100,100);
        jtab1.setPreferredSize(new Dimension(200,200));
        f1.add(js1);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);
        abc=jtab1.getTabCount();
    }
    public void actionPerformed(ActionEvent e1){
        // Finding And Replacing
        if (e1.getSource() == replaceall) {
            tavalue=jt1[jtab1.getSelectedIndex()].getText();
            pattern = Pattern.compile(tf2.getText());
            matcher = pattern.matcher(tavalue);
            while (matcher.find()) {
                jt1[jtab1.getSelectedIndex()].setText(matcher.replaceAll(tr.getText()));
            }
        }
        if(e1.getSource()==find) {
            abc=jtab1.getSelectedIndex();
            x=jt1[abc].getCaretPosition();
            findingValue1();

        }
        if(e1.getSource()==find1){
            abc=jtab1.getSelectedIndex();
                pos=jt1[abc].getCaretPosition();

                findingValue2();

        }
        if(e1.getSource()==replace){
            abc=jtab1.getSelectedIndex();
            System.out.println("hello");
            //jt1[abc].requestFocus();
            /*System.out.print(jt1[abc].getSelectedText().equals(""));
            if(jt1[abc].getSelectedText().equals(""))
                pos=jt1[abc].getCaretPosition();
            else
               pos=jt1[abc].getSelectionEnd();*/

            if(count==1){
            if (jt1[abc].getSelectedText().equals(tf2.getText())) {
                System.out.println("The selected text is "+jt1[abc].getSelectedText());
                jt1[abc].replaceRange(tr.getText(), jt1[abc].getSelectionStart(), jt1[abc].getSelectionEnd());
            }
            else
            {
                System.out.println("In the else part"+jt1[abc].getSelectedText()+tr.getText());
            }}

            findingValue2();
            count=1;
        }
        if (e1.getSource() == fi)
            finding();

        if (e1.getSource() == fr)
            replacing();


        //



        if(e1.getSource()==com){
            saving();
            String l,l2;
            jt2.setText("");
            try{
                String compiler="javac \""+gdir+"\\"+jtab1.getTitleAt(jtab1.getSelectedIndex())+"\"";

                Process pro= Runtime.getRuntime().exec(compiler);
                System.out.println(compiler);
                BufferedReader input=new BufferedReader(new InputStreamReader(pro.getInputStream()));
                while((l=input.readLine())!=null){
                    jt2.setText(jt2.getText()+"\n"+l);
                    System.out.println(l);}

                BufferedReader err=new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                while((l2=err.readLine())!=null){
                    jt2.setText(jt2.getText()+"\n"+l2);
                    System.out.println(l2);}
              //  err.close();
                if(jt2.getText().equals("")){
                    jt2.setText("Compiled Sucessfully");
                }
                input.close();
                err.close();
            }catch(Exception exec){
                System.out.print(exec.getMessage());
            }

        }
        if (e1.getSource()==r){
            String l,l2;
            saving();
            jt2.setText("");
            try{
                String compiler="javac \""+gdir+"\\"+jtab1.getTitleAt(jtab1.getSelectedIndex())+"\"";

                Process pro= Runtime.getRuntime().exec(compiler);
                System.out.println(compiler);
                BufferedReader input=new BufferedReader(new InputStreamReader(pro.getInputStream()));
                while((l=input.readLine())!=null){
                    jt2.setText(jt2.getText()+"\n"+l);
                    System.out.println(l);}

                BufferedReader err=new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                while((l2=err.readLine())!=null){
                    jt2.setText(jt2.getText()+"\n"+l2);
                    System.out.println(l2);}
                //  err.close();
                if(jt2.getText().equals("")){
                    jt2.setText("Compiled Sucessfully");
                }
                input.close();
                err.close();
            }catch(Exception exec){
                System.out.print(exec.getMessage());
            }
            if(jt2.getText().equals("Compiled Sucessfully")) {

                jt2.setText("");
                try {
                    String s1 = jtab1.getTitleAt(jtab1.getSelectedIndex());
                    String s2 = s1.substring(0, s1.indexOf(".java"));
                    String s3 = gdir.substring(0, gdir.length() - 1);
                    String compiler = "java -cp \"" + s3 + "\" " + s2; //cmd /c start
                    System.out.println(" c1= " + s1 + " s2= " + s2 + " compiler= " + compiler);
                    Process pro = Runtime.getRuntime().exec(compiler);
                    BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                    while ((l = input.readLine()) != null) {
                        jt2.setText(jt2.getText() + "\n" + l);

                        System.out.println(l);
                    }
                    input.close();
                    BufferedReader err = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                    while ((l2 = err.readLine()) != null) {
                        jt2.setText(jt2.getText() + "\n" + l2);
                        System.out.println(l2);
                    }
                    err.close();

                } catch (Exception exr) {
                    System.out.println(exr.getMessage());
                }
            }
        }
        if(e1.getSource()==s){
            saveBox();
        }
        if (e1.getSource()==sa){

            saveBox();
        }
        if(e1.getSource()==e){
            System.exit(0);
        }
        if(e1.getSource()==clo){
            jtab1.removeTabAt(jtab1.getSelectedIndex());
        }
        if(e1.getSource()==cloall){
            jtab1.removeAll();
        }
        if(e1.getSource()==c){
            try{
                jt1[jtab1.getSelectedIndex()].cut();
            }
            catch(Exception ex1){
                System.out.println(ex1.getMessage());
            }
        }
        if(e1.getSource()==co){
            try{
                jt1[jtab1.getSelectedIndex()].copy();
            }
            catch(Exception ex1){
                System.out.println(ex1.getMessage());
            }
        }
        if(e1.getSource()==p){
            try{
                jt1[jtab1.getSelectedIndex()].paste();
            }
            catch(Exception ex1){
                System.out.println(ex1.getMessage());
            }
        }
        if(e1.getSource()==n) {
            abc= jtab1.getTabCount();
            jtab1.add("Untittld "+(abc+1),jt1[abc]=new JTextArea());
        }
        if(e1.getSource()==o){
            abc= jtab1.getTabCount();
            fd1=new FileDialog(f1,"Open File",FileDialog.LOAD);
            fd1.setVisible(true);
            gf=fd1.getDirectory()+fd1.getFile();
            gdir=fd1.getDirectory();

            jt1[abc]=new JTextArea();

            System.out.println(abc);
            for(int i=0;i<=jtab1.getTabCount();i++){
                if(jtab1.getTitleAt(i).equals(fd1.getFile())){
                    break;

                }
                else
                    openning();

            }


        }
    }

    public void openning()
    {
        try{
            jtab1.add(""+fd1.getFile(),jsp1[abc]=new JScrollPane(jt1[abc]));
            File f=new File(gf);
            FileInputStream fis=new FileInputStream(f);
            int ch;
            abc= jtab1.getTabCount()-1;
            while((ch=fis.read())!=-1)
                jt1[abc].append(""+(char)ch);
            fis.close();

        }
        catch(Exception ex){ex.getMessage();}
    }
    public void saveBox(){
        FileDialog fd2=new FileDialog(f1,"Save File",FileDialog.SAVE);
        fd2.setVisible(true);
        gf=fd2.getDirectory()+fd2.getFile();
        saving();
    }
    public void saving()
    {
        try{
            File f=new File(gf);
            FileOutputStream fos=new FileOutputStream(f);
            char ch[]=jt1[jtab1.getSelectedIndex()].getText().toCharArray();
            for(int i=0;i<ch.length;i++)
                fos.write((byte)ch[i]);
            fos.close();

        }
        catch(Exception le){}
    }



    public void finding()
    {
        try{
            if(true)
            {
                f3=new JFrame("Find What:");
                f3.setSize(250,120);
                Panel p=new Panel();

                find.addActionListener(this);

                close1.addActionListener(this);

                p.setLayout(new FlowLayout());
                p.add(find);
                p.add(close1);
                tf1=new JTextField(20);
                f3.add(new Label("Find What:"),"North");
                f3.add(tf1);
                f3.add(p,"South");
                f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                f3.setVisible(true);
                f3.setLocation(500,150);
                tavalue=tf1.getText();

            }
        }
        catch(Exception le){}

    }
    public void replacing()
    {
        try{
            if(true)
            {

                f4=new JFrame("Find & Replace");
                f4.setSize(300,160);
                Panel p1=new Panel();
                p1.setLayout(new GridLayout(4,0));
                System.out.print("mid1");
                Panel p2=new Panel();
                p2.setLayout(new FlowLayout());
                p1.add(new Label("Find What:"));
                tf2=new JTextField(20);
                p1.add(tf2);
                p1.add(new Label("Replace with:"));
                tr=new JTextField(20);
                p1.add(tr);
                find1=new JButton("Find");
                find1.addActionListener(this);
                System.out.print("mid");
                close2=new JButton("Close");
                close2.addActionListener(this);
                replace=new JButton("Replace");
                replace.addActionListener(this);
                replaceall=new JButton("Replace All");
                replaceall.addActionListener(this);
                p2.add(find1);
                p2.add(replace);
                p2.add(replaceall);
                p2.add(close2);
                System.out.print("mid2");
                f4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f4.add(p1);
                f4.add(p2,BorderLayout.SOUTH);

                f4.setVisible(true);
                f4.setLocation(500,150);
            }

        }catch(Exception le){}}


    public void findingValue1()
    {
        try{
            abc=jtab1.getSelectedIndex();
            tavalue=jt1[jtab1.getSelectedIndex()].getText();
            pattern=Pattern.compile(tf1.getText());
            matcher=pattern.matcher(tavalue);
            //.............

         int z = jt1[abc].getCaretPosition();
        System.out.println("Z="+z);



            if(matcher.find(z))
            {
                y=0;
                int mn=jtab1.getSelectedIndex();
                char ch[]=(jt1[mn].getText()).toCharArray();
                for(int i=0;i<matcher.start();i++)
                    if(ch[i]=='\n')
                        y++;
                jt1[mn].select(matcher.start()-y,matcher.end()-y);
                r1=matcher.start()-y;
                r2=matcher.end()-y;
                z=matcher.end();

            }
           // else
               // cannotFound();
            f1.toFront();

        }catch(Exception le){}}
    public void findingValue2()
    {
        try{
            abc=jtab1.getSelectedIndex();
            String s1=jt1[abc].getText();
            s1=s1.replaceAll("\r","");
            jt1[abc].setText(s1);
            jt1[abc].setCaretPosition(pos);
            pattern=Pattern.compile(tf2.getText());
            matcher=pattern.matcher(s1);
            if(matcher.find(pos))
            {
                jt1[abc].requestFocus();
                jt1[abc].select(matcher.start(),matcher.end());
                String str1,str2;
                str1=tr.getText();
                str2=tf2.getText();
                if(str1.length()>str2.length())
                    pos=matcher.end()+1;
                else
                    pos=matcher.start()+1;
            }
                //cannotFound();

        }
        catch(Exception ex){}
    }



    public static void main(String[] args) {
        Jeditor jed=new Jeditor();
    }

}
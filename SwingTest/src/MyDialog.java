import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;

public class MyDialog extends JDialog {
    public MyDialog( JFrame frame ) {
        super( frame, "Hello", true );
        Vector col = new Vector();
        col.add( "Name" );
        col.add( "Roll No" );
        col.add( "Grade" );
        Vector first = new Vector();
        first.add( "Bhupendra" );
        first.add( "100" );
        first.add( "A+" );
        Vector row = new Vector();
        row.add( first );
        JTable table = new JTable( row, col );
        Container c = getContentPane();
        c.setLayout( new FlowLayout() );
        c.add( table );
        this.pack();
        this.show();
    }
    
    public static void main(String args[]) {
    	JFrame tmp = new JFrame();
    	tmp.setVisible(true);
    	new MyDialog(tmp);
    }
}
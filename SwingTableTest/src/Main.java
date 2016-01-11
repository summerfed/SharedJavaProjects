import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class Main {

	public static void main(String[] args) {
		/*SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		String[][] data = {{"0,0","0,1", "0,2"},{"1,0","1,1",""}};
		TableModel tableModel = new TableModel(data);
		JTable table = new JTable(tableModel) {
		    /**
			 * 
			 */
			private static final long serialVersionUID = -3127435750745933472L;

			public Component prepareRenderer(
		            TableCellRenderer renderer, int row, int column){
		            Component c = super.prepareRenderer(renderer, row, column);
		            if(column!=0) {
		            	c.setBackground(Color.LIGHT_GRAY);
		            } else {
		            	c.setBackground(Color.WHITE);
		            }
		            return c;
		        }
		    };
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(250, 200));
		
		JFrame frame = new JFrame("Swing JTable Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(tableScrollPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

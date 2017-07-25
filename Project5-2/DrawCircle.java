//package homework5;

import javax.swing.*;
import java.awt.*;

public class DrawCircle extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new DrawCircle();
	}

	private final int width = 80;// Width of the circle
	private final int height = 80;// Height of the circle

	public DrawCircle() {
		super();
		initialize();

	}

	private void initialize() {
		setVisible(true);
		this.setBounds(600, 300, 400, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Set a method to close
		setContentPane(new DrawPanel());
		this.setTitle("Five Rings");
	}

	// Create the panel
	class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			super.paint(g);

			// To obtain advanced drawing technique
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(4f)); // Set the thickness of circles

			setBackground(Color.WHITE);
			g.setColor(Color.BLUE);
			g.drawOval(60, 110, width, height);
			g.setColor(Color.BLACK);
			g.drawOval(150, 110, width, height);
			g.setColor(Color.RED);
			g.drawOval(240, 110, width, height);
			g.setColor(Color.YELLOW);
			g.drawOval(105, 155, width, height);
			g.setColor(Color.GREEN);
			g.drawOval(195, 155, width, height);

			Font consolas = new Font("Consolas", Font.BOLD, 86);
			g.setColor(Color.RED);
			g.setFont(consolas);
			g.drawString("U S A", 70, 95);
		}

	}
}

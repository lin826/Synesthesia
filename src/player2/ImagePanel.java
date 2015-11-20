package player2;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

class ImagePanel extends JComponent { //set Game background
    private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}

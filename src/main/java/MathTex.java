import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MathTex implements ObjJX {

    private ImageView imageView;
    private String latex;
    private Point offset;

    private Color fontColor;
    private float fontSize;

    private MathTex(){};

    public static MathTex fromMathTex(String latex){

        MathTex mathTex = new MathTex();
        mathTex.latex = latex;
        mathTex.setDefaults();
        mathTex.draw();
        return mathTex;
    }

    private void draw() {
        TeXFormula formula = new TeXFormula(latex);

        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(TeXConstants.STYLE_DISPLAY).setSize(fontSize)
                .setStyle(TeXConstants.STYLE_DISPLAY).setFGColor(fontColor)
                .build();

        icon.setInsets(new Insets(5, 5, 5, 5));

        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        JLabel jl = new JLabel();
        icon.paintIcon(jl, g2, 0, 0);

        SwingFXUtils.toFXImage(image, null);
        imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
    }

    private void setDefaults() {
        fontSize = 12;
        fontColor = Color.BLACK;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        draw();
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        draw();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getLatex() {
        return latex;
    }

    public Point getOffset() {
        return offset;
    }

    @Override
    public Node getNode() {
        return imageView;
    }

    @Override
    public Point offsetPoint(Point point) {
        if (point == null) {
            point = new Point(0, 0);
        }
        return point;
    }
}

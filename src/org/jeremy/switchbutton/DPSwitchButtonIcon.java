package org.jeremy.switchbutton;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

import static java.awt.RenderingHints.*;

/**
 * An Icon build from scratch to paint a switch button. Different kind / position can be handled from here.
 * <br /><br />
 * Icon is always composed of 3 layers :
 * <ol>
 *     <li>a rounded rectangle, referenced as 'base': The fixed part of the button,</li>
 *     <li>a smaller rounded rectangle, referenced as 'mobile': The moving part of the button,</li>
 *     <li>0, 1 or 2 text(s), referenced as 'textLeft' and 'textRight'.</li>
 * </ol>
 *
 * Notes:
 * <ul>
 *     <li>'height' and 'width' are switched in last parameters for rectangle creation,
 *     it is on purpose to give the 'Switch button look',</li>
 *     <li>The text left and right are forced to a fixed position :
 *     Each takes half of the button width and are placed in the center.
 *     Not so flexible but simple and clean enough for a 'standard' button</li>
 * </ul>
 */
public class DPSwitchButtonIcon implements Icon {
    // Layer 1 - base
    protected final Color baseColor;
    protected final int baseWidth;
    protected final int baseHeight;

    // Layer 2 - mobile
    protected final Color mobileColor;
    protected final int mobileWidth;
    protected final int mobileHeight;
    protected final Point mobilePosition;

    // Layer 3 - text(s)
    protected final Color textColor;
    protected final String textLeft;
    protected final Point textLeftPosition;

    protected final String textRight;
    protected final Point textRightPosition;

    /**
     * Creates an icon representing a switch button.
     *
     * @param settings The {@link DPSwitchButtonSettings} of the button.
     * @param baseColor The color of the base layer.
     * @param mobileColor The color of the mobile layer.
     * @param mobilePosition The position X and Y to set up the mobile placement.
     *                       X and Y are relative to the button itself: (0,0) would be the top left corner of the base.
     */
    public DPSwitchButtonIcon (DPSwitchButtonSettings settings, Color baseColor, Color mobileColor, Color textColor,
                               Point mobilePosition, DPSwitchButton.ShowTextOnIcon showText) {

        this.baseColor = baseColor;
        this.baseWidth = settings.getBaseDimension ().width;
        this.baseHeight = settings.getBaseDimension ().height;

        this.mobileColor = mobileColor;
        this.mobileWidth = settings.getMobileDimension ().width;
        this.mobileHeight = settings.getMobileDimension ().height;
        this.mobilePosition = mobilePosition;

        this.textColor = textColor;
        this.textLeft = showText.showLeft () ? settings.getTextHolder ().textLeft () : null;
        this.textLeftPosition = getTextPosition (textLeft, true);

        this.textRight = showText.showRight () ? settings.getTextHolder ().textRight () : null;
        this.textRightPosition = getTextPosition (textRight, false);
    }

    @Override public void paintIcon (Component c, Graphics g, int x, int y) {
        Graphics2D graphics2D = (Graphics2D) g.create ();

        improveRendering (graphics2D);

        drawBase (x, y, graphics2D);         // Layer 1
        drawMobile (x, y, graphics2D);       // Layer 2
        drawText (x, y, graphics2D);         // Layer 3

        graphics2D.dispose();
    }

    protected void drawBase (int x, int y, Graphics2D graphics2D) {
        graphics2D.setColor (baseColor);
        graphics2D.fillRoundRect (x, y, baseWidth, baseHeight, baseHeight, baseWidth);
    }

    protected void drawMobile (int x, int y, Graphics2D graphics2D) {
        graphics2D.setColor (mobileColor);
        graphics2D.fillRoundRect (x + mobilePosition.x, y + mobilePosition.y,
                mobileWidth, mobileHeight, mobileHeight, mobileWidth);
    }

    protected void drawText (int x, int y, Graphics2D graphics2D) {
        graphics2D.setFont (getIconFont ());
        graphics2D.setColor (textColor);

        if (textLeft != null && !textLeft.isEmpty ())
            graphics2D.drawString (textLeft, x + textLeftPosition.x, y + textLeftPosition.y);

        if (textRight != null && !textRight.isEmpty ())
            graphics2D.drawString (textRight, x + textRightPosition.x, y + textRightPosition.y);
    }

    @Override public int getIconWidth () {
        return baseWidth;
    }

    @Override public int getIconHeight () {
        return baseHeight;
    }

    private void improveRendering (Graphics2D graphics2D) {
        graphics2D.setRenderingHint (KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint (KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint (KEY_RENDERING, VALUE_RENDER_QUALITY);
    }

    /**
     * Get the position of the text centered in its half
     * @param left to get the textLeft position, false to get the textRight position
     */
    private Point getTextPosition (String text, boolean left) {
        if (text == null || text.isEmpty ())
            return new Point (0, 0); // Doesn't matter, text won't be displayed

        FontRenderContext context = new FontRenderContext (null, true, true);
        Font font = getIconFont ();

        int textWidth = (int) font.getStringBounds (text, context).getWidth ();
        int centeredTextInHalfButton = baseWidth / 4 - textWidth / 2;
        int textPositionX = centeredTextInHalfButton + (left ? 0 : baseWidth / 2);

        int textHeight = (int) font.getStringBounds (text, context).getHeight ();
        int textPositionY = baseHeight / 2 + textHeight / 3; // Why 3 ? Don't know, but it is the one that works ...

        return new Point (textPositionX, textPositionY);
    }

    /**
     * Force the font size depending on the button height.
     * Sounds reasonable for a standard short text, written on one line.
     */
    private Font getIconFont () {
        return new Font ("", Font.PLAIN, baseHeight / 2);
    }
}

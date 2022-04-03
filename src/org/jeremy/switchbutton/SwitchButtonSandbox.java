package org.jeremy.switchbutton;

import javax.swing.*;
import java.awt.*;

import org.jeremy.switchbutton.DPSwitchButtonSettings.*;

/**
 * A simple frame to test / demo SwitchButton implementation.
 *
 * @author JeremyLavigne
 */
public class SwitchButtonSandbox {
    public static void main (String[] args) {
        JFrame frame = new JFrame("Sandbox");
        frame.add(buildPanel ());
        showFrame (frame);
    }

    private static JPanel buildPanel () {
        JPanel panel = new JPanel(new GridBagLayout ());
        GridBagConstraints gbc = new GridBagConstraints();

        addSwitchButtons (panel, gbc);

        return panel;
    }

    private static void showFrame(JFrame frame) {
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        frame.setSize (new Dimension(1200, 800));
        frame.setLocationRelativeTo (null);
        frame.setVisible (true);
    }

    private static void addSwitchButtons (JPanel panel, GridBagConstraints gbc) {
        gbc.gridy = 0;
        DPSwitchButton myButton = new DPSwitchButton ("Default");
        panel.add (myButton, gbc);

        gbc.gridy = 1;
        DPSwitchButtonSettings myCustomSettings = new DPSwitchButtonSettings ();
        myCustomSettings.setTextHolder (new TextHolder ("Yes", "No", ShowTextOption.ONLY_ON_FREE_SPACE));
        myCustomSettings.setBaseDimension (new Dimension (200, 80));
        myCustomSettings.setMobileDimension (new Dimension (60, 60));
        DPSwitchButton myButton2 = new DPSwitchButton ("Bigger, with text", myCustomSettings);
        panel.add (myButton2, gbc);

        gbc.gridy = 2;
        DPSwitchButtonSettings myCustomSettings2 = new DPSwitchButtonSettings ();
        myCustomSettings2.setTextHolder (new TextHolder ("", "", ShowTextOption.NONE_NEVER));
        DPSwitchButton myButton3 = new DPSwitchButton ("Disabled, no text", myCustomSettings2);
        myButton3.setEnabled (false);
        panel.add (myButton3, gbc);

        gbc.gridy = 3;
        DPSwitchButtonSettings myCustomSettings3 = new DPSwitchButtonSettings ();
        myCustomSettings3.setBaseColors (
                new ColorHolder (Color.CYAN, Color.YELLOW,  Color.BLUE, Color.GRAY, Color.BLACK, Color.GRAY));
        myCustomSettings3.setMobileColors (
                new ColorHolder (Color.GREEN, Color.CYAN,  Color.BLUE, Color.GREEN, Color.RED, Color.PINK));
        DPSwitchButton myButton4 = new DPSwitchButton ("Colorful", myCustomSettings3);
        panel.add (myButton4, gbc);

        gbc.gridy = 4;
        DPSwitchButtonSettings myCustomSettings4 = new DPSwitchButtonSettings ();
        myCustomSettings4.setMobileDimension (new Dimension (10, 10));
        myCustomSettings4.setSlideEffectTime (800);
        DPSwitchButton myButton5 = new DPSwitchButton ("Slow, small knob", myCustomSettings4);
        panel.add (myButton5, gbc);

        gbc.gridy = 5;
        DPSwitchButtonSettings myCustomSettings5 = new DPSwitchButtonSettings ();
        myCustomSettings5.setSlideEffectFrequency (0);
        myCustomSettings5.setTextHolder (new TextHolder ("", "", ShowTextOption.NONE_NEVER));
        DPSwitchButton myButton6 = new DPSwitchButton ("No text, no effect", myCustomSettings5);
        panel.add (myButton6, gbc);

        gbc.gridy = 6;
        DPSwitchButtonSettings myCustomSettings6 = new DPSwitchButtonSettings ();
        myCustomSettings6.setBaseDimension (new Dimension (200, 40));
        myCustomSettings6.setMobileDimension (new Dimension (100, 38));
        myCustomSettings6.setTextHolder (
                new TextHolder ("Opt 1", "Opt 2", ShowTextOption.BOTH_ALWAYS));
        myCustomSettings6.setBaseColors (
                new ColorHolder (Color.GRAY, Color.GRAY,  Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY));
        myCustomSettings6.setMobileColors (
                new ColorHolder (Color.GREEN, Color.GREEN,  Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
        DPSwitchButton myButton7 = new DPSwitchButton ("Custom", myCustomSettings6);
        panel.add (myButton7, gbc);
    }
}

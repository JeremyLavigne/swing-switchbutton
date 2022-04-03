package org.jeremy.switchbutton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A JCheckbox that looks like a switch button : (o_)
 *
 * <br \><br \>
 * It behaves like a checkbox : two states are possibles. Only the displayed icons change: default icons are
 * replaced by custom {@link DPSwitchButtonIcon}s.
 * The global look of the button is managed by its {@link DPSwitchButtonSettings}.
 *
 * <br \><br \>
 * The animation (that mimic a slide effect when activate/deactivate the button) is also only matter
 * of which icon is displayed. it is manage inside this class.
 *
 * <br \><br \>
 * Notes :
 * <ul>
 *     <li>It is possible to write text directly on the button, but it is not meant at all to welcome long text.
 *     If it doesn't fit, get a smaller text, or a bigger button</li>
 *     <li>A checkbox state usually depends on its method {@link #isSelected()}, it is still the case.
 *     Some methods has been added to make more sense depending on the actual use of the button.
 *     Same global meaning, different names :
 *     <ul>
 *         <li>{@link #isActive}</li>
 *         <li>{@link #isInactive}</li>
 *         <li>{@link #isLeftActive}</li>
 *         <li>{@link #isRightActive}</li>
 *     </ul>
 * </ul>
 *
 * @author JeremyLavigne
 */
public class DPSwitchButton extends JCheckBox {
    protected final DPSwitchButtonSettings settings;

    protected DPSwitchButtonIcon activeIcon;
    protected DPSwitchButtonIcon inactiveIcon;
    protected DPSwitchButtonIcon activeDisabledIcon;
    protected DPSwitchButtonIcon inactiveDisabledIcon;
    protected DPSwitchButtonIcon activeRolloverIcon;
    protected DPSwitchButtonIcon inactiveRolloverIcon;

    // A list of icons used to simulate the button slide effect.
    protected ArrayList<DPSwitchButtonIcon> animatedIconsList;
    protected int currentAnimatedIconIndex;
    protected boolean isAnimationRunning;

    protected ScheduledExecutorService executor; // The 'Animation' executor

    /**
     * Creates a Switch Button with default settings.
     *
     * @param text The text to display on the left of the switch button
     */
    public DPSwitchButton (String text) {
        this (text, new DPSwitchButtonSettings ());
    }

    /**
     * Creates a Switch Button with Custom settings
     *
     * @param text The text to display on the left of the switch button
     * @param settings The {@link DPSwitchButtonSettings} needed to create the button.
     */
    public DPSwitchButton (String text, DPSwitchButtonSettings settings) {
        super (text);                           // Gives some  extra space between text and button
        setHorizontalTextPosition (SwingConstants.LEFT);   // Force the text on the left of the button
        setFocusPainted (false);                           // Remove the focus drawing around the text

        validateSettings (settings);                       // Throw if one value is 'not standard'
        this.settings = settings;

        initialiseIcons ();
        initialiseAnimatedIcons ();

        if (!animatedIconsList.isEmpty ())
            addActionListener (getSlideEffectListener ()); // Handle animation on toggle action
    }

    public boolean isActive () {
        return isSelected ();
    }

    public boolean isInactive () {
        return !isSelected ();
    }

    public boolean isLeftActive () {
        return !isSelected (); // Left active means mobile is to the left, means not selected.
    }

    public boolean isRightActive () {
        return isSelected ();
    }

    @Override public Icon getIcon () {
        return isAnimationRunning ? animatedIconsList.get (currentAnimatedIconIndex) : inactiveIcon;
    }

    @Override public Icon getSelectedIcon () {
        return isAnimationRunning ? animatedIconsList.get (currentAnimatedIconIndex) : activeIcon;
    }

    @Override public Icon getDisabledIcon () {
        return inactiveDisabledIcon;
    }

    @Override public Icon getDisabledSelectedIcon () {
        return activeDisabledIcon;
    }

    @Override public Icon getRolloverIcon () {
        return isAnimationRunning ? animatedIconsList.get (currentAnimatedIconIndex) : inactiveRolloverIcon;
    }

    @Override public Icon getPressedIcon () {
        if (isAnimationRunning)
            return animatedIconsList.get (currentAnimatedIconIndex);
        return isActive () ? activeRolloverIcon : inactiveRolloverIcon;
    }

    @Override public Icon getRolloverSelectedIcon () {
        return isAnimationRunning ? animatedIconsList.get (currentAnimatedIconIndex) : activeRolloverIcon;
    }

    /**
     * Validate the numeric values include into the settings.
     * @param settings The {@link DPSwitchButtonSettings} to validate.
     */
    private void validateSettings (DPSwitchButtonSettings settings) {
        // No real restriction for base width and height. Though, it is not really meant to be a 'big' button,
        validateRange ("base width", settings.getBaseDimension ().width, 0, 500);
        validateRange ("base height", settings.getBaseDimension ().height, 0, 200);

        // Avoid a mobile part bigger than the base.
        validateRange ("mobile width", settings.getMobileDimension ().width, 0, settings.getBaseDimension ().width);
        validateRange ("mobile height", settings.getMobileDimension ().height, 0, settings.getBaseDimension ().height);

        // Avoid too many repaint for simulate the movement.
        validateRange ("slide effect frequency", settings.getSlideEffectFrequency (), 0, 100);

        // No real restriction for time either, but better warn if we start consider a movement that takes more than 2s.
        validateRange ("slide effect time", settings.getSlideEffectTime (), 0, 2000);
    }

    /**
     * Throw exception if the value is not in the range.
     */
    private void validateRange (String property, double toValidate, double min, double max) {
        if (toValidate < min || toValidate > max)
            throw new IllegalArgumentException ("The " + property + " should be between " + min +
                    " and " + max + ". Got : " + toValidate);
    }

    /**
     * Create an icon for each state of the button : active, inactive, disabled, hovered.
     */
    protected void initialiseIcons () {
        Point mobileLeftPos = getMobileLeftPosition ();
        Point mobileRightPos = getMobileRightPosition ();

        activeIcon = new DPSwitchButtonIcon (settings, settings.getBaseColors ().active (),
                settings.getMobileColors ().active (), settings.getTextColors ().active (),
                mobileRightPos, setShowText (true, false));
        inactiveIcon = new DPSwitchButtonIcon (settings, settings.getBaseColors ().inactive (),
                settings.getMobileColors ().inactive (), settings.getTextColors ().inactive (),
                mobileLeftPos, setShowText (false, true));
        activeDisabledIcon = new DPSwitchButtonIcon (settings, settings.getBaseColors ().activeDisabled (),
                settings.getMobileColors ().activeDisabled (), settings.getTextColors ().activeDisabled (),
                mobileRightPos, setShowText (true, false));
        inactiveDisabledIcon = new DPSwitchButtonIcon (settings, settings.getBaseColors ().inactiveDisabled (),
                settings.getMobileColors ().inactiveDisabled (), settings.getTextColors ().inactiveDisabled (),
                mobileLeftPos, setShowText (false, true));
        activeRolloverIcon = new DPSwitchButtonIcon (settings, settings.getBaseColors ().activeRollover (),
                settings.getMobileColors ().activeRollover (), settings.getTextColors ().activeRollover (),
                mobileRightPos, setShowText (true, false));
        inactiveRolloverIcon = new DPSwitchButtonIcon (settings, settings.getBaseColors ().inactiveRollover (),
                settings.getMobileColors ().inactiveRollover (), settings.getTextColors ().inactiveRollover (),
                mobileLeftPos, setShowText (false, true));
    }

    private Point getMobileLeftPosition () {
        return new Point (getMobileMargin (), getMobileMargin ());
    }

    private Point getMobileRightPosition () {
        int mobileMargin = getMobileMargin ();
        int x = settings.getBaseDimension ().width - settings.getMobileDimension ().width - mobileMargin;
        return new Point (x, getMobileMargin ());
    }

    private int getMobileMargin () {
        return (int) ((settings.getBaseDimension ().getHeight () - settings.getMobileDimension ().getHeight ()) / 2);
    }

    private ShowTextOnIcon setShowText (boolean showLeft, boolean showRight) {
        return switch (settings.getTextHolder ().showTextOption ()) {
            case NONE_NEVER -> new ShowTextOnIcon (false, false);
            case ONLY_ON_FREE_SPACE -> new ShowTextOnIcon (showLeft, showRight);
            case BOTH_ALWAYS -> new ShowTextOnIcon (true, true);
        };
    }

    /**
     * Fill the {@link #animatedIconsList} with several icons that will mimic a movement when displayed in a row.
     * Icons assure as well a nice transition of colors.
     *
     * List is filled from inactive position to active position.
     * It will be read from the end to simulate the opposite movement.
     */
    protected void initialiseAnimatedIcons () {
        animatedIconsList = new ArrayList<> ();

        int frequency = settings.getSlideEffectFrequency ();

        Color baseStartColor = settings.getBaseColors ().inactive ();
        Color baseEndColor = settings.getBaseColors ().active ();
        Color mobileStartColor = settings.getMobileColors ().inactive ();
        Color mobileEndColor = settings.getMobileColors ().active ();
        Color textStartColor = settings.getTextColors ().inactive ();
        Color textEndColor = settings.getTextColors ().active ();

        Point mobileLeftPos = getMobileLeftPosition ();
        Point mobileRightPos = getMobileRightPosition ();

        for (int i = 1; i <= frequency; i++) {

            Color currentBaseColor = getCurrentColor (baseStartColor, baseEndColor, i, frequency);
            Color currentMobileColor = getCurrentColor (mobileStartColor, mobileEndColor, i, frequency);
            Color currentTextColor = getCurrentColor (textStartColor, textEndColor, i, frequency);

            int currentPosX = getCurrent (mobileLeftPos.x, mobileRightPos.x, i, frequency);
            int currentPosY = getCurrent (mobileLeftPos.y, mobileRightPos.y, i, frequency);

            ShowTextOnIcon showTextOnIcon = setShowText (i >= frequency / 2, i <= frequency / 2);

            animatedIconsList.add (new DPSwitchButtonIcon (settings, currentBaseColor, currentMobileColor, currentTextColor,
                    new Point (currentPosX, currentPosY), showTextOnIcon));
        }
    }

    private Color getCurrentColor (Color startColor, Color endColor, int currentStep, int numberOfStep) {
        int currentRed = getCurrent (startColor.getRed (), endColor.getRed (), currentStep, numberOfStep);
        int currentGreen = getCurrent (startColor.getGreen (), endColor.getGreen (), currentStep, numberOfStep);
        int currentBlue = getCurrent (startColor.getBlue (), endColor.getBlue (), currentStep, numberOfStep);

        return new Color (currentRed, currentGreen, currentBlue);
    }

    protected int getCurrent (double start, double end, double currentStep, double numberOfStep) {
        if (start == end)
            return (int) start;

        double min = Math.min (start, end);
        double max = Math.max (start, end);

        return (int) (min + ((max - min) / numberOfStep) * currentStep);
    }

    private ActionListener getSlideEffectListener () {
        return e -> {
            isAnimationRunning = true;
            simulateSlide ();
        };
    }

    /**
     * Set up a 'scheduled execution' that shows all icons in {@link #animatedIconsList} with the defined time/frequency
     * Shut down all running task at the beginning: allows clicking again on the button even if the movement is not over.
     * Start from first or last index depending on the button state (selected or not).
     */
    private void simulateSlide () {
        if (executor != null && !executor.isTerminated ())
            executor.shutdownNow (); // Make sure we have nothing running already
        executor = Executors.newSingleThreadScheduledExecutor ();

        int period = settings.getSlideEffectTime () / settings.getSlideEffectFrequency ();

        currentAnimatedIconIndex = isActive () ? 0 : animatedIconsList.size () - 1;
        executor.scheduleAtFixedRate (moveToNextIcon (isActive ()), 0, period, TimeUnit.MILLISECONDS);

        // If, for some reason, the flag is not set back to false. We don't want the button to keep (or keep trying)
        // displaying icons from the animation list.
        // Extra check : force the flag to false when the expected animation time is over.
        executor.schedule (() -> isAnimationRunning = false, settings.getSlideEffectTime (), TimeUnit.MILLISECONDS);
    }

    /**
     * A Runnable that set the current Icon and increase/decrease the index. Stop before getting out of bounds.
     *
     * @param increaseCount true if we should increase it, false if we should decrease it.
     */
    private Runnable moveToNextIcon (boolean increaseCount) {
        return () -> {
            setIcon (animatedIconsList.get (currentAnimatedIconIndex));
            currentAnimatedIconIndex += (increaseCount ? 1 : -1);

            if (currentAnimatedIconIndex >= animatedIconsList.size () - 1 || currentAnimatedIconIndex <= 0) {
                isAnimationRunning = false;
                executor.shutdownNow ();
            }
        };
    }

    public record ShowTextOnIcon (boolean showLeft, boolean showRight) {
        // Hold two boolean to show or not the text the icon
    }
}

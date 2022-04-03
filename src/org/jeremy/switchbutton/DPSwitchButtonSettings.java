package org.jeremy.switchbutton;

import java.awt.*;

/**
 * A settings holder for a {@link DPSwitchButton}.
 *
 * Can be instantiated with all default values and/or freely customized with setters.
 *
 * <br /><br />
 * Note about terminology:
 * <ul>
 *     <li>'base' represents the fixed part of the button,</li>
 *     <li>'mobile' represents the moving part of the button,</li>
 *     <li>'active' represents the state 'selected' or 'checked'.</li>
 * </ul>
 */
public class DPSwitchButtonSettings {

    // ------------------------------------------ Default values -------------------------------------------------
    public static final Dimension DEFAULT_BASE_DIMENSION = new Dimension (45, 22);
    public static final Dimension DEFAULT_MOBILE_DIMENSION = new Dimension (18, 18);

    public static final int DEFAULT_SLIDE_EFFECT_FREQUENCY = 40;
    public static final int DEFAULT_SLIDE_EFFECT_TIME = 200;

    public static final String DEFAULT_TEXT_LEFT = "\u2714";
    public static final String DEFAULT_TEXT_RIGHT = "\u2716";
    public static final ShowTextOption DEFAULT_SHOW_TEXT_OPTION = ShowTextOption.ONLY_ON_FREE_SPACE;
    public static final TextHolder DEFAULT_TEXT_HOLDER =
            new TextHolder (DEFAULT_TEXT_LEFT, DEFAULT_TEXT_RIGHT, DEFAULT_SHOW_TEXT_OPTION);

    // Base colors
    public static final Color DEFAULT_BASE_COLOR_ACTIVE = new Color (36, 160, 237);
    public static final Color DEFAULT_BASE_COLOR_INACTIVE = new Color (128, 128, 128);
    public static final Color DEFAULT_BASE_COLOR_ACTIVE_DISABLED = new Color (84, 104, 114);
    public static final Color DEFAULT_BASE_COLOR_INACTIVE_DISABLED = new Color (98, 98, 98);
    public static final Color DEFAULT_BASE_COLOR_ACTIVE_ROLLOVER = new Color (36, 160, 237);
    public static final Color DEFAULT_BASE_COLOR_INACTIVE_ROLLOVER = new Color (128, 128, 128);
    public static final ColorHolder DEFAULT_BASE_COLORS =
            new ColorHolder (DEFAULT_BASE_COLOR_ACTIVE, DEFAULT_BASE_COLOR_INACTIVE,
                    DEFAULT_BASE_COLOR_ACTIVE_DISABLED, DEFAULT_BASE_COLOR_INACTIVE_DISABLED,
                    DEFAULT_BASE_COLOR_ACTIVE_ROLLOVER, DEFAULT_BASE_COLOR_INACTIVE_ROLLOVER);

    // Mobile colors
    public static final Color DEFAULT_MOBILE_COLOR_ACTIVE = Color.WHITE;
    public static final Color DEFAULT_MOBILE_COLOR_INACTIVE = Color.WHITE;
    public static final Color DEFAULT_MOBILE_COLOR_ACTIVE_DISABLED = new Color (128, 128, 128);
    public static final Color DEFAULT_MOBILE_COLOR_INACTIVE_DISABLED = new Color (128, 128, 128);
    public static final Color DEFAULT_MOBILE_COLOR_ACTIVE_ROLLOVER = new Color (242, 242, 242);
    public static final Color DEFAULT_MOBILE_COLOR_INACTIVE_ROLLOVER = new Color (242, 242, 242);
    public static final ColorHolder DEFAULT_MOBILE_COLORS =
            new ColorHolder (DEFAULT_MOBILE_COLOR_ACTIVE, DEFAULT_MOBILE_COLOR_INACTIVE,
                    DEFAULT_MOBILE_COLOR_ACTIVE_DISABLED, DEFAULT_MOBILE_COLOR_INACTIVE_DISABLED,
                    DEFAULT_MOBILE_COLOR_ACTIVE_ROLLOVER, DEFAULT_MOBILE_COLOR_INACTIVE_ROLLOVER);

    // Text colors
    public static final Color DEFAULT_TEXT_COLOR_ACTIVE = Color.WHITE;
    public static final Color DEFAULT_TEXT_COLOR_INACTIVE = Color.WHITE;
    public static final Color DEFAULT_TEXT_COLOR_ACTIVE_DISABLED = Color.WHITE;
    public static final Color DEFAULT_TEXT_COLOR_INACTIVE_DISABLED = Color.WHITE;
    public static final Color DEFAULT_TEXT_COLOR_ACTIVE_ROLLOVER = Color.WHITE;
    public static final Color DEFAULT_TEXT_COLOR_INACTIVE_ROLLOVER = Color.WHITE;
    public static final ColorHolder DEFAULT_TEXT_COLORS =
            new ColorHolder (DEFAULT_TEXT_COLOR_ACTIVE, DEFAULT_TEXT_COLOR_INACTIVE,
                    DEFAULT_TEXT_COLOR_ACTIVE_DISABLED, DEFAULT_TEXT_COLOR_INACTIVE_DISABLED,
                    DEFAULT_TEXT_COLOR_ACTIVE_ROLLOVER, DEFAULT_TEXT_COLOR_INACTIVE_ROLLOVER);

    // ------------------------------------------ Fields -------------------------------------------------
    private Dimension baseDimension;
    private Dimension mobileDimension;

    private int slideEffectFrequency;
    private int slideEffectTime;

    private TextHolder textHolder;

    private ColorHolder baseColors;
    private ColorHolder mobileColors;
    private ColorHolder textColors;

    /**
     * Initialise settings with default values.
     */
    public DPSwitchButtonSettings () {
        this (DEFAULT_BASE_DIMENSION, DEFAULT_MOBILE_DIMENSION, DEFAULT_SLIDE_EFFECT_FREQUENCY, DEFAULT_SLIDE_EFFECT_TIME,
                DEFAULT_TEXT_HOLDER, DEFAULT_BASE_COLORS, DEFAULT_MOBILE_COLORS, DEFAULT_TEXT_COLORS);
    }

    /**
     * Initialise settings with custom values.
     *
     * @param baseDimension The dimension of the base part of the button
     * @param mobileDimension The width of the mobile part of the button
     * @param slideEffectFrequency Number of different icon used to simulate the button slide effect. 0 for no effect.
     * @param slideEffectTime Time, in milliseconds, that will last the slide effect
     * @param textHolder The {@link TextHolder} carrying text info.
     * @param baseColors {@link ColorHolder} for the base part
     * @param mobileColors {@link ColorHolder} for the mobile part
     * @param textColors {@link ColorHolder} for the text.
     */
    public DPSwitchButtonSettings (Dimension baseDimension, Dimension mobileDimension,
                                   int slideEffectFrequency, int slideEffectTime, TextHolder textHolder,
                                   ColorHolder baseColors, ColorHolder mobileColors, ColorHolder textColors) {
        this.baseDimension = baseDimension;
        this.mobileDimension = mobileDimension;

        this.slideEffectFrequency = slideEffectFrequency;
        this.slideEffectTime = slideEffectTime;

        this.textHolder = textHolder;

        this.baseColors = baseColors;
        this.mobileColors = mobileColors;
        this.textColors = textColors;
    }

    public Dimension getBaseDimension () {
        return baseDimension;
    }

    public void setBaseDimension (Dimension baseDimension) {
        this.baseDimension = baseDimension;
    }

    public Dimension getMobileDimension () {
        return mobileDimension;
    }

    public void setMobileDimension (Dimension mobileDimension) {
        this.mobileDimension = mobileDimension;
    }

    public int getSlideEffectFrequency () {
        return slideEffectFrequency;
    }

    public void setSlideEffectFrequency (int slideEffectFrequency) {
        this.slideEffectFrequency = slideEffectFrequency;
    }

    public int getSlideEffectTime () {
        return slideEffectTime;
    }

    public void setSlideEffectTime (int slideEffectTime) {
        this.slideEffectTime = slideEffectTime;
    }

    public TextHolder getTextHolder () {
        return textHolder;
    }

    public void setTextHolder (TextHolder textHolder) {
        this.textHolder = textHolder;
    }

    public ColorHolder getBaseColors () {
        return baseColors;
    }

    public void setBaseColors (ColorHolder baseColors) {
        this.baseColors = baseColors;
    }

    public ColorHolder getMobileColors () {
        return mobileColors;
    }

    public void setMobileColors (ColorHolder mobileColors) {
        this.mobileColors = mobileColors;
    }

    public ColorHolder getTextColors () {
        return textColors;
    }

    public void setTextColors (ColorHolder textColors) {
        this.textColors = textColors;
    }

    /**
     * Hold the necessary colors to paint an element of a {@link DPSwitchButton}.
     *
     * @param active Color when the button is active
     * @param inactive Color when the button is inactive
     * @param activeDisabled Color when the button is active and disabled
     * @param inactiveDisabled Color when the button is inactive and disabled
     * @param activeRollover Color when the button is active and hovered
     * @param inactiveRollover Color when the button is inactive and hovered
     */
    public record ColorHolder (Color active, Color inactive, Color activeDisabled, Color inactiveDisabled,
                               Color activeRollover, Color inactiveRollover) {
        // Holder for colors, depending on button state.
    }

    /**
     * Hold the necessary info to display text on a {@link DPSwitchButton}
     *
     * @param textLeft The text to display on the left of the button. Empty or null for no text.
     * @param textRight The text to display on the right of the button. Empty or null for no text.
     * @param showTextOption The {@link ShowTextOption} expected
     */
    public record TextHolder (String textLeft, String textRight, ShowTextOption showTextOption) {
        // Holder for text and 'show text option'.
    }

    /**
     * Define if and when the text is shown.
     */
    public enum ShowTextOption {
        /**
         * The switch button has no text on it.
         */
        NONE_NEVER,
        /**
         * The switch button shows only the text on the available side of itself (The part not covered by the mobile)
         */
        ONLY_ON_FREE_SPACE,
        /**
         * The switch button shows two texts, one on each half. Mobile should be large enough to contains the text.
         */
        BOTH_ALWAYS
    }
}

package dev.sakura.client.assets.i18n;

/**
 * Static factory that creates {@link TranslateComponent} instances
 * with the "sakura" prefix. Used for Sakura's own i18n keys.
 */
public class SakuraTranslateComponent {

    private static final String PREFIX = "sakura";

    public static TranslateComponent create(String prefix, String suffix) {
        return DefaultTranslateComponent.create(PREFIX + "." + prefix + "." + suffix);
    }

}


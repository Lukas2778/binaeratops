package de.dhbw.binaeratops.view;

import com.vaadin.flow.i18n.I18NProvider;

import java.text.MessageFormat;
import java.util.*;

public class TranslationProvider implements I18NProvider {

    public static final String BUNDLE_PREFIX = "language";

    public final Locale LOCALE_DE = Locale.GERMANY;
    public final Locale LOCALE_EN = Locale.US;

    private final List<Locale> locales = Collections
            .unmodifiableList(Arrays.asList(LOCALE_DE, LOCALE_EN));

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            return "";
        }

        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }
}
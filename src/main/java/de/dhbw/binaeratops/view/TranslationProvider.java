package de.dhbw.binaeratops.view;

import com.vaadin.flow.i18n.I18NProvider;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;

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

    private final ResourceBundle res = ResourceBundle.getBundle(BUNDLE_PREFIX);

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

    public String getUserMessage(UserMessage AUserMessage, Locale ALocale) {
        if (AUserMessage.getKey() == null) {
            return "<div>TranslationError</div>";
        }

        if (AUserMessage.getParams().size() == 0) {
            return res.getString(AUserMessage.getKey());
        } else if (AUserMessage.getParams().size() == 1) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0));
        } else if (AUserMessage.getParams().size() == 2) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1));
        } else if (AUserMessage.getParams().size() == 3) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2));
        } else if (AUserMessage.getParams().size() == 4) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3));
        } else if (AUserMessage.getParams().size() == 5) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3),
                    AUserMessage.getParams().get(4));
        } else if (AUserMessage.getParams().size() == 6) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3),
                    AUserMessage.getParams().get(4), AUserMessage.getParams().get(5));
        } else if (AUserMessage.getParams().size() == 7) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3),
                    AUserMessage.getParams().get(4), AUserMessage.getParams().get(5), AUserMessage.getParams().get(6));
        } else if (AUserMessage.getParams().size() == 8) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3),
                    AUserMessage.getParams().get(4), AUserMessage.getParams().get(5), AUserMessage.getParams().get(6),
                    AUserMessage.getParams().get(7));
        } else if (AUserMessage.getParams().size() == 9) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3),
                    AUserMessage.getParams().get(4), AUserMessage.getParams().get(5), AUserMessage.getParams().get(6),
                    AUserMessage.getParams().get(7), AUserMessage.getParams().get(8));
        } else if (AUserMessage.getParams().size() == 10) {
            return MessageFormat.format(res.getString(AUserMessage.getKey()), AUserMessage.getParams().get(0),
                    AUserMessage.getParams().get(1), AUserMessage.getParams().get(2), AUserMessage.getParams().get(3),
                    AUserMessage.getParams().get(4), AUserMessage.getParams().get(5), AUserMessage.getParams().get(6),
                    AUserMessage.getParams().get(7), AUserMessage.getParams().get(8), AUserMessage.getParams().get(9));
        }
        return "<div>TranslationError</div>";
    }
}
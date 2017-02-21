package com.fridayrss.manager;

import android.content.Context;

import com.fridayrss.interfaces.Manager;
import com.fridayrss.util.CachedValue;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitriy Dovbnya on 25.09.2014.
 */
public class SharedPrefManager implements Manager {

    private static final String NAME = "sharedPrefs";

    private static final String CONTENT_ACCEPTED = "content_accepted";

    private Set<CachedValue> cachedValues;

    private CachedValue<Boolean> contentAccepted;

    @Override
    public void init(Context context) {
        CachedValue.initialize(context.getSharedPreferences(NAME, Context.MODE_PRIVATE));
        cachedValues = new HashSet<>();
        cachedValues.add(contentAccepted = new CachedValue<>(CONTENT_ACCEPTED, false, Boolean.class));
    }

    public void setContentAccepted(boolean accepted) {
        contentAccepted.setValue(accepted);
    }

    public boolean isContentAccepted() {
        return contentAccepted.getValue();
    }

    @Override
    public void clear() {
        for (CachedValue value : cachedValues) {
            value.delete();
        }
    }

}

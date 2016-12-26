package g20.brunelplanner.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class NonPersistentCookieJar implements CookieJar {
    private static final String TAG = NonPersistentCookieJar.class.getSimpleName();
    private final Set<Cookie> cookieStore = new LinkedHashSet<>();

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.addAll(cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> matchingCookies = new ArrayList<>();
        Iterator<Cookie> it = cookieStore.iterator();
        while (it.hasNext()) {
            Cookie cookie = it.next();
            LogCookie(cookie);
            if (cookie.expiresAt() < System.currentTimeMillis()) {
                it.remove();
            } else if (cookie.matches(url)) {
                matchingCookies.add(cookie);
            }
        }
        return matchingCookies;
    }

    private void LogCookie(Cookie cookie) {
        Log.d(TAG, "String: " + cookie.toString());
        Log.d(TAG, "String: " + cookie.toString());
        Log.d(TAG, "Expires: " + cookie.expiresAt());
        Log.d(TAG, "Hash: " + cookie.hashCode());
        Log.d(TAG, "Path: " + cookie.path());
        Log.d(TAG, "Domain: " + cookie.domain());
        Log.d(TAG, "Name: " + cookie.name());
        Log.d(TAG, "Value: " + cookie.value());
    }
}
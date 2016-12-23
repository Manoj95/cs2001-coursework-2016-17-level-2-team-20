package g20.brunelplanner.network;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

import g20.brunelplanner.utils.NonPersistentCookieJar;
import g20.brunelplanner.views.activities.LoginActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BrunelService {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static BrunelService sInstance;

    private OkHttpClient mClient;

    private BrunelService() {
        NonPersistentCookieJar cookieJar = new NonPersistentCookieJar();
        mClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    public static BrunelService getTemporaryInstance() {
        return new BrunelService();
    }

    private static String BASE_URL = "https://teaching.brunel.ac.uk/SWS-1617/";
    private static String defaultRoute = "default.aspx";
    private static String loginRoute = "login.aspx";

    public Observable<String> authenticateStudent(final String studentID, final String studentPassword) {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                Request getLoginValues = new Request.Builder()
                        .url(BASE_URL + loginRoute)
                        .build();

                Response loginValues = mClient.newCall(getLoginValues).execute();

                Document doc = Jsoup.parse(loginValues.body().string());
                String viewState = doc.select("input[name=__VIEWSTATE]").first().attr("value");
                String viewStageGenerator = doc.select("input[name=__VIEWSTATEGENERATOR]").first().attr("value");
                String eventValidation = doc.select("input[name=__EVENTVALIDATION]").first().attr("value");

                Log.d(TAG, studentID);
                Log.d(TAG, studentPassword);

                RequestBody loginBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        // Constant
                        .addFormDataPart("__LASTFOCUS", "")
                        .addFormDataPart("__VIEWSTATE", viewState)
                        .addFormDataPart("__VIEWSTATEGENERATOR", viewStageGenerator)
                        .addFormDataPart("__EVENTTARGET", "LinkBtn_studentmodules")
                        // Constant
                        .addFormDataPart("__EVENTARGUMENT", "")
                        .addFormDataPart("__EVENTVALIDATION", eventValidation)
                        .addFormDataPart("tUserName", studentID)
                        .addFormDataPart("tPassword", studentPassword)
                        .addFormDataPart("bLogin", "Login")
                        .build();

                Request loginRequest = new Request.Builder()
                        .url(BASE_URL + defaultRoute)
                        .addHeader("Host", "teaching.brunel.ac.uk")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .post(loginBody)
                        .build();


                Response response = mClient.newCall(loginRequest).execute();
//                response.body().string()

                if (true) {
                    throw new Exception("Valid");
                }

                return Observable.just("Networking complete");
            }
        });
    }
}

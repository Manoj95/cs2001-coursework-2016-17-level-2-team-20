package g20.brunelplanner.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BrunelService {

    private static BrunelService sInstance = new BrunelService();
    private OkHttpClient mClient;

    private BrunelService() {
        mClient = new OkHttpClient.Builder()
                .build();
    }

    public static BrunelService getInstance() {
        return sInstance;
    }

    public Observable<String> authenticateStudent(final String studentID, final String studentPassword) {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                String BASE_URL = "https://teaching.brunel.ac.uk/SWS-1617/";

                Request getLoginValues = new Request.Builder()
                        .url(BASE_URL + "Login.aspx")
                        .build();

                Response loginHeaders = mClient.newCall(getLoginValues).execute();

                Document loginHtml = Jsoup.parse(loginHeaders.body().string());
                String viewState = loginHtml.select("input[name=__VIEWSTATE]").first().attr("value");
                String viewStageGenerator = loginHtml.select("input[name=__VIEWSTATEGENERATOR]").first().attr("value");
                String eventValidation = loginHtml.select("input[name=__EVENTVALIDATION]").first().attr("value");

                RequestBody loginBody = new FormBody.Builder()
                        .add("__VIEWSTATE", viewState)
                        .add("__VIEWSTATEGENERATOR", viewStageGenerator)
                        .add("__EVENTTARGET", "LinkBtn_studentmodules")
                        .add("__EVENTVALIDATION", eventValidation)
                        .add("tUserName", studentID)
                        .add("tPassword", studentPassword)
                        .add("bLogin", "Login")
                        .build();

                Request loginRequest = new Request.Builder()
                        .url(BASE_URL + "Login.aspx")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .post(loginBody)
                        .build();

                Response loginResponse = mClient.newCall(loginRequest).execute();
                Document doc2 = Jsoup.parse(loginResponse.body().string());

                // Really hacky way to check if logged in
                if (doc2.select("form[name=form1]").first().attr("action").equals("Login.aspx")) {
                    throw new Exception("Invalid");
                }

                return Observable.just("Valid");
            }
        });
    }
}

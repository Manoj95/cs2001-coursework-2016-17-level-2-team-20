package g20.brunelplanner.controllers.networks;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Callable;

import g20.brunelplanner.models.Timetable;
import g20.brunelplanner.models.Weeks;
import g20.brunelplanner.utils.NonPersistentCookieJar;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BrunelService {

    private static final String TAG = BrunelService.class.getSimpleName();
    private static BrunelService sInstance = new BrunelService();
    private static OkHttpClient mClient;

    private BrunelService() {
        // The cookie jar might not be needed
        NonPersistentCookieJar cookieJar = new NonPersistentCookieJar();
        mClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    public static BrunelService getInstance() {
        return sInstance;
    }

    // TODO: Amend to fit MVP architecture
    public Observable<JSONArray> getTimetable(final String studentID, final String studentPassword) {
        return Observable.defer(new Callable<ObservableSource<? extends JSONArray>>() {
            @Override
            public ObservableSource<? extends JSONArray> call() throws Exception {

                String LOGIN_URL = "https://teaching.brunel.ac.uk/SWS-1617/Login.aspx";
                String BASE_URL = "https://teaching.brunel.ac.uk/SWS-1617/default.aspx";

                Request getLoginValues = new Request.Builder()
                        .url(LOGIN_URL)
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
                        .url(LOGIN_URL)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .addHeader("Cache-Control", "no-cache")
                        .post(loginBody)
                        .build();

                Response loginResponse = mClient.newCall(loginRequest).execute();
                Document redirect = Jsoup.parse(loginResponse.body().string());

                // Hacky way to check if user is logged in
                if (redirect.select("form[name=form1]").first().attr("action").equals("Login.aspx")) {
                    throw new Exception("Invalid");
                }

                String redirectState = redirect.select("input[name=__VIEWSTATE]").first().attr("value");
                String redirectStageGenerator = redirect.select("input[name=__VIEWSTATEGENERATOR]").first().attr("value");
                String redirectValidation = redirect.select("input[name=__EVENTVALIDATION]").first().attr("value");

                RequestBody moduleBody = new FormBody.Builder()
                        .add("__EVENTTARGET", "LinkBtn_studentmodules")
                        .add("__VIEWSTATE", redirectState)
                        .add("__VIEWSTATEGENERATOR", redirectStageGenerator)
                        .add("__EVENTVALIDATION", redirectValidation)
                        .add("tLinkType", "information")
                        .build();

                Request moduleRequest = new Request.Builder()
                        .url(BASE_URL)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .post(moduleBody)
                        .build();

                Response moduleResponse = mClient.newCall(moduleRequest).execute();

                Document moduleHTML = Jsoup.parse(moduleResponse.body().string());
                Elements options = moduleHTML.select("#dlObject > option");
                String[] studentModules = new String[options.size()];
                for (int i = 0; i < options.size(); i++) {
                    studentModules[i] = options.get(i).text();
                }

                String timetableState = moduleHTML.select("input[name=__VIEWSTATE]").first().attr("value");
                String timetableStageGenerator = moduleHTML.select("input[name=__VIEWSTATEGENERATOR]").first().attr("value");
                String timetableValidation = moduleHTML.select("input[name=__EVENTVALIDATION]").first().attr("value");

                FormBody.Builder test = new FormBody.Builder();
                test.add("__VIEWSTATE", timetableState);
                test.add("__VIEWSTATEGENERATOR", timetableStageGenerator);
                test.add("__EVENTVALIDATION", timetableValidation);
                test.add("tLinkType", "studentmodules");
                for (String module: studentModules) {
                    test.add("dlObject", module);
                }
                test.add("lbWeeks", "1-16");
                test.add("lbDays", "1-7");
                test.add("dlType", "TextSpreadsheet;swsurl;SWSCUST Object TextSpreadsheet&combined=yes");
                test.add("bGetTimetable", "View Timetable");

                RequestBody timetableBody = test.build();

                Request timetableRequest = new Request.Builder()
                        .url(BASE_URL)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
                        .post(timetableBody)
                        .build();

                Response timetableResponse = mClient.newCall(timetableRequest).execute();

                Document timetableHtml = Jsoup.parse(timetableResponse.body().string());
                String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
                Elements tables = timetableHtml.getElementsByClass("spreadsheet");

                JSONArray timetable = new JSONArray();
                int primaryKey = 1;
                for (int i = 0; i < tables.size(); i++) {
                    Elements rows = tables.get(i).getElementsByTag("tr");

                    if (rows.size() != 0) {
                        rows.remove(0);
                    }

                    for (Element row: rows) {
                        JSONObject item = new JSONObject();

                        item.put("id", primaryKey);
                        primaryKey++;

                        item.put("day", weekdays[i]);
                        item.put("activity", row.getElementsByTag("td").get(0).text().replaceAll("( ?<.*>)", ""));
                        item.put("description", row.getElementsByTag("td").get(1).text());
                        item.put("start", row.getElementsByTag("td").get(2).text());
                        item.put("end", row.getElementsByTag("td").get(3).text());
                        item.put("weeks", getWeeks(row.getElementsByTag("td").get(4).text()));
                        item.put("room", row.getElementsByTag("td").get(5).text());
                        item.put("staff", row.getElementsByTag("td").get(6).text());
                        item.put("type", row.getElementsByTag("td").get(7).text());

                        timetable.put(item);
                    }

                }

                Type token = new TypeToken<RealmList<Weeks>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .registerTypeAdapter(token, new TypeAdapter<RealmList<Weeks>>() {

                            @Override
                            public void write(JsonWriter out, RealmList<Weeks> value) throws IOException {
                                // Ignore
                            }

                            @Override
                            public RealmList<Weeks> read(JsonReader in) throws IOException {
                                RealmList<Weeks> list = new RealmList<>();
                                in.beginArray();

//                        if (in.peek() == JsonToken.NULL) {
//                            in.nextNull();
//                            return null;
//                        }

                                while (in.hasNext()) {
                                    Weeks ri = new Weeks();
                                    ri.setVal(in.nextInt());
                                    list.add(ri);
                                }
                                in.endArray();
                                return list;
                            }
                        })
                        .create();

                final List<Timetable> objects = gson.fromJson(timetable.toString(), new TypeToken<List<Timetable>>(){}.getType());

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(objects);
                    }
                });
                realm.close();

                return Observable.just(timetable);
            }
        });
    }

    private static JSONArray getWeeks(String weekString) {
        JSONArray weeks = new JSONArray();
        String[] weekArray = weekString.split(",[ ]*");

        for (String week: weekArray) {
            if (week.contains("-")) {
                int start = Integer.parseInt(week.split("-")[0]);
                int end = Integer.parseInt(week.split("-")[1]);
                for (int j = start; j <= end; j++) {
                    weeks.put(j);
                }
            } else {
                weeks.put(Integer.parseInt(week));
            }
        }

        return weeks;
    }
}

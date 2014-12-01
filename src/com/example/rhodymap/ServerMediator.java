package com.example.rhodymap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ServerMediator
{

    private static final String ECAMPUS_URL = "https://appsaprod.uri.edu:9503/psp/sahrprod_m2/?cmd=login&languageCd=ENG";

    private static final String STUDENT_CENTER_URL = "https://appsaprod.uri.edu:9503/"
            + "psp/sahrprod_m2/EMPLOYEE/HRMS/h/"
            + "?cmd=getCachedPglt&pageletname=URI_STUDENT_CENTER"
            + "&tab=DEFAULT&PORTALPARAM_COMPWIDTH=Wide&ptlayout=N";

    private static final String EVENTS_ADDRESS = "http://events.uri.edu/";

    /**
     * Gets the user's class schedule from eCampus given a valid username and
     * password combination.
     * 
     * @param username
     * @param password
     * @param context
     *            Android context to reference DataManager
     * @return
     * @throws IOException
     *             if an error in the network's connection arises.
     * @throws LoginException
     *             if the given username and/or password is invalid.
     */
    public static void getClasses(final String username, final String password,
            final MapManager m)
    {

        // Creates a thread which sends the HTTP request
        new Thread() {
            private final String name = username;
            private final String pass = password;

            // Action of the thread
            public void run()
            {
                // Login the student and get schedule.
                try
                {
                    Map<String, String> cookies = loginUser(name, pass);
                    final List<Class> schedule = getSchedule(cookies, m);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {

                        @Override
                        public void run()
                        {
                            m.addClasses(schedule);
                        }
                    });
                }
                catch (LoginException | IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * Returns eCampus session cookies.
     * 
     * @param username
     * @param password
     * @throws IOException
     * @throws LoginException
     */
    private static Map<String, String> loginUser(String username,
            String password) throws IOException, LoginException
    {
        String data = "timezoneOffset=240&userid=" + username + "&pwd="
                + password;
        int contentLength = data.length();

        // The actual HTTP request
        Connection connection = Jsoup
                .connect(ECAMPUS_URL)
                .header("Host", "appsaprod.uri.edu:9503")
                .header("Origin", "https://appsaprod.uri.edu:9503")
                .header("Connection", "keep-alive")
                .header("Content-Length", Integer.toString(contentLength))
                .header("Pragma", "no-cache")
                .header("Cache-Control", "no-cache")
                .header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp, */*;q=0.8")
                .header("Origin", "https://appsaprod.uri.edu:9503")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) "
                                + "AppleWebKit/537.36 (KHTML, like Gecko) "
                                + "Chrome/37.0.2062.120 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Referer",
                        "https://appsaprod.uri.edu:9503/psp/sahrprod_m2/?cmd=login")
                .header("Accept-Encoding", "gzip,deflate")
                .header("Accept-Language", "en-US,en;q=0.8")
                .data("timezoneOffset", "240").data("userid", username)
                .data("pwd", password);

        connection.post();

        // If the HTTP response doesn't have this cookie, then wrong login
        // credentials were used.
        if (!connection.response().hasCookie("PS_TOKENEXPIRE"))
        {
            throw new LoginException("Invalid Username/Password");
        }

        return connection.response().cookies();
    }

    /**
     * @param cookies
     * @param m
     * @return
     * @throws IOException
     */
    private static List<Class> getSchedule(Map<String, String> cookies,
            MapManager m) throws IOException
    {
        List<Class> schedule = new ArrayList<Class>();

        Document studentCenter = Jsoup.connect(STUDENT_CENTER_URL)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip,deflate")
                .header("Accept-Language", "en-US,en;q=0.8")
                .header("Cache-Control", "max-age=0")
                .header("Connection", "keep-alive")
                .header("Content-Typ", "application/x-www-form-urlencoded")
                .cookies(cookies).get();

        // Holds each HTML element which has class information.
        Elements classes = studentCenter.select(".PSLEVEL3GRID");

        DataManager data = new DataManager(m);

        // Adds each class to a list.
        for (int i = 0; i < classes.size(); i += 2)
        {
            // Parses the response
            Element currentCell = classes.get(i);
            String className = currentCell.select("span").html().split("<br>")[0];

            currentCell = classes.get(i + 1);
            String[] information = currentCell.select("span").html()
                    .split("<br>");

            String classTimes = information[0];

            if (classTimes.equals("ONLINE"))
            {
                continue;
            }

            String buildingName = information[1].split(" ")[1] + " Hall";

            Building building = data.getBuildings(buildingName).get(0);

            Log.v("ServerMediator", "|" + buildingName + "|");
            schedule.add(new Class(className, classTimes, building));
        }

        return schedule;
    }

    /**
     * Sends HTTP request in a separate thread to get trending campus events.
     * 
     * @param m
     */
    public static void getEvents(final MapManager m)
    {

        new Thread() 
        {
            public void run()
            {
                // Login the student and get schedule.
                try
                {
                    final List<Event> events = getEventsFromURI(m);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() 
                    {

                        @Override
                        public void run()
                        {
                            m.addEvents(events);
                        }
                    });
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static List<Event> getEventsFromURI(MapManager m)
            throws IOException
    {
        Document doc = new Document("");

        try
        {
            doc = Jsoup.connect(EVENTS_ADDRESS).get();

        }
        catch (IOException e)
        {
            throw e;
        }

        // TODO trending and featured events.
        Elements eventElements = doc.select(".event_item");

        DataManager data = new DataManager(m);
        List<Event> events = new ArrayList<Event>();

        for (Element item : eventElements)
        {
            String building = item.select(".event_item_venue").html();

            if (building == null || building.length() == 0)
            {
                continue; // Skip to the next iteration.
            }

            Building b = data.getBuildings(building.substring(0, 8)).get(0);

            String meetingTime = item.select(".dtstart").html();
            String name = item.select(".summary a").html();

            events.add(new Event(name, meetingTime, b));
        }

        return events;
    }
}

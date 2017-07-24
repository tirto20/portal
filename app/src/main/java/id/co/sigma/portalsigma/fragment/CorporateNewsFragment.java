package id.co.sigma.portalsigma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import id.co.sigma.portalsigma.R;
import id.co.sigma.portalsigma.adapter.FeedNewsAdapter;
import id.co.sigma.portalsigma.controller.AppController;
import id.co.sigma.portalsigma.model.FeedItem;
import id.co.sigma.portalsigma.model.MyFeedItem;
import id.co.sigma.portalsigma.volley.Config;

/**
 * Created by Aries Satriana on 15/09/2016.
 */
public class CorporateNewsFragment extends Fragment {
    private static final String TAG = CorporateNewsFragment.class.getSimpleName();
    private ListView listView;
    private FeedNewsAdapter listAdapter;
    //    private List<FeedItem> feedItems;
    private List<MyFeedItem> feedItems;
    private StringBuilder URL_FEED = null;
//    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    public CorporateNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_corporate_news, container, false);
        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<MyFeedItem>();
        listAdapter = new FeedNewsAdapter(this.getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
//        super.getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
//        super.getActivity().getActionBar().setIcon(
//                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        URL_FEED = new StringBuilder(Config.BASE_URL);
        URL_FEED.append("corporatenews");
        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED.toString());
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonMyFeedItems(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED.toString(), null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonMyFeedItems(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
        // Inflate the layout for this fragment
        return view;
    }

    private String formatDateToString(String date, String format) {
//        TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat calFormat = new SimpleDateFormat(format);

        try {
//            calendar.setTimeZone(timeZone1);
            calendar.setTime(calFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calFormat.applyPattern("dd MMMM yyyy");
        return calFormat.format(calendar.getTime()) ;// 09 Jan 2016
    }

    private void parseJsonMyFeedItems(JSONObject response) {
        try {
            String imagePath="http://"+response.getString("imagepath");
            JSONArray feedArray = response.getJSONArray("feed");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
//                JSONObject feedObj = feedArray.getJSONObject(i);
                MyFeedItem item = new MyFeedItem();
                //1. ID
                item.setId(Integer.parseInt(feedObj.getString("id")));
                //2. Name
                item.setName(feedObj.getString("name"));

                // 3. profile Picture
                item.setProfilePic(feedObj.isNull("profilePicture") ? "" : imagePath+"/"+feedObj.getString("profilePicture")); // profilePicture

                // 4. TimeStamp
                item.setTimeStamp(formatDateToString(feedObj.getString("input_date"),"yyyy-MM-dd")); // input_date

                // 5. Status Msg
                item.setStatusMsg(feedObj.getString("caption"));

                // 6. FeedImage
                item.setFeedImage(feedObj.isNull("filename") ? "" : imagePath+"/"+feedObj.getString("filename"));

                feedItems.add(item);
            }
//            Collections.sort(feedItems,MyFeedItem.DESCENDING_COMPARATOR);
            Collections.sort(feedItems);
            // notify data changes to list adapter
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

package id.co.sigma.portalsigma.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import id.co.sigma.portalsigma.R;
import id.co.sigma.portalsigma.controller.AppController;
import id.co.sigma.portalsigma.model.FeedItem;
import id.co.sigma.portalsigma.model.MyFeedItem;
import id.co.sigma.portalsigma.view.FeedImageView;

/**
 * Created by Aries Satriana on 25/09/2016.
 */
public class FeedMindAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MyFeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedMindAdapter(Activity activity, List<MyFeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_mind, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        TextView content=(TextView)convertView.findViewById(R.id.txtContent);
        //content.setText(Html.fromHtml());

        NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);

        MyFeedItem item = feedItems.get(position);

        // 1.Name
        name.setText(item.getName());

        // Converting timestamp into x ago format
        String timeAgo=item.getTimeStamp();


//        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
//                Long.parseLong(item.getTimeStamp()),
//                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

        // 2.TimeStamp
        timestamp.setText(timeAgo);

        // 3.Status Message
        // Check for empty status message
        if (!TextUtils.isEmpty(item.getStatusMsg())) {
            statusMsg.setText(item.getStatusMsg());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // 4.URL
        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // 5. Profile Pic
        // user profile pic
        StringBuilder sb=new StringBuilder("");
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // 6. FeedImage
        // Feed image
        if (item.getFeedImage() != null) {
            feedImageView.setImageUrl(item.getFeedImage(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        // 7. Content
        if (!TextUtils.isEmpty(item.getContent())) {
            content.setText(Html.fromHtml(item.getContent()));
            content.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            content.setVisibility(View.GONE);
        }

        return convertView;
    }

}

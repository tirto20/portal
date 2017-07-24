package id.co.sigma.portalsigma.model;

import java.util.Comparator;

/**
 * Created by Aries Satriana on 03/10/2016.
 */
public class MyFeedItem implements Comparable<MyFeedItem>{
    private int id;//1
    private String name;//2
    private String profilePic;//3
    private String timeStamp;//4
    private String statusMsg ;//5
    private String url;//6
    private String feedImage;//7
    private String content;//8




    public MyFeedItem(){}

    public MyFeedItem(int id,String profilePic,String name,String timeStamp,String statusMsg,String url,String feedImage,String content){
        super();
        this.setId(id);
        this.setProfilePic(profilePic);
        this.setName(name);
        this.setTimeStamp(timeStamp);
        this.setStatusMsg(statusMsg);
        this.setUrl(url);
        this.setFeedImage(feedImage);
        this.setContent(content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFeedImage() {
        return feedImage;
    }

    public void setFeedImage(String feedImage) {
        this.feedImage = feedImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(MyFeedItem o) {
        return o.getId()-this.getId();
    }

//    public static final Comparator<MyFeedItem> DESCENDING_COMPARATOR = new Comparator<MyFeedItem>() {
//        // Overriding the compare method to sort the age
//        public int compare(MyFeedItem d, MyFeedItem d1) {
//            return d1.getId() - d.getId();
//        }
//    };

}

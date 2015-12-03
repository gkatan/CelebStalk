package jaemolee.myapplication;

public class DashItem {
    private String author;
    private String date;
    private String PROFILE_IMG_URL;
    private String SOCIAL_MEDIA_TYPE;
    private String content;
    private String tw_username;
    private String tmb_username;
    private String fb_username;

    public DashItem(){
        super();
        this.author = "unknown";
        this.date = "1/1/2015";
    }

    public DashItem(String author, String date, String profImgURL, String socMedType, String content){
        this.author = author;
        this.date = date;
        this.PROFILE_IMG_URL = profImgURL;
        this.SOCIAL_MEDIA_TYPE = socMedType;
        this.content = content;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String authorName){
        this.author = authorName;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String datetime){
        this.date = datetime;
    }

    public String getSocMedType(){
        return this.SOCIAL_MEDIA_TYPE;
    }

    public void setSocMedType(String socmed){
        this.SOCIAL_MEDIA_TYPE = socmed;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String post){
        this.content = post;
    }

    public String getProfImgURL(){
        return this.PROFILE_IMG_URL;
    }

    public void setProfImg(String url){
        this.PROFILE_IMG_URL = url;
    }

    public String getFBUsername(){
        return this.fb_username;
    }

    public void setFBUsername(String un){
        this.fb_username = un;
    }

    public String getTWUsername(){
        return this.tw_username;
    }

    public void setTWUsername(String un){
        this.tw_username = un;
    }

    public String getTMBUsername(){
        return this.tmb_username;
    }

    public void setTMBUsername(String un){
        this.tmb_username = un;
    }
}

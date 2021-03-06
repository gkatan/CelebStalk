package jaemolee.myapplication;

public class Profile {

    private String name;
    private String description;
    private String image; // This should be a text path to the source.
    private int stalkingFlag;
    private String facebookName;
    private String twitterName;
    private String tumblrName;

    // TODO - add Twitter/facebook account names.

    // constructors
    public Profile() {
        name = null;
        description = null;
        image = null;
        stalkingFlag = 0;
        this.facebookName = "";
        this.twitterName = "";
        this.tumblrName = "";
    }

    public Profile(String name, String description, String image, int stalkingFlag) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.stalkingFlag = stalkingFlag;
        this.facebookName = "";
        this.twitterName = "";
        this.tumblrName = "";
    }

    public Profile(String name, String description, String image, int stalkingFlag, String twitterName, String facebookName, String tumblrName) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.stalkingFlag = stalkingFlag;
        this.twitterName = twitterName;
        this.facebookName = facebookName;
        this.tumblrName = tumblrName;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescriptioion(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStalkingFlag(int stalkingFlag){
        this.stalkingFlag = stalkingFlag;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImage() {
        return this.image;
    }

    public int getStalkingFlag() {
        return this.stalkingFlag;
    }

    public String getTwitterName() {
        return this.twitterName;
    }

    public String getFacebookName() {
        return this.facebookName;
    }

    public String getTumblrName() {
        return this.tumblrName;
    }
}



package jaemolee.myapplication;

public class Profile {

    String name;
    String description;
    String image; // This should be a text path to the source.
    int stalkingFlag;

    // TODO - add Twitter/facebook account names.

    // constructors
    public Profile() {
        name = null;
        description = null;
        image = null;
        stalkingFlag = 0;
    }

    public Profile(String name, String description, String image, int stalkingFlag) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.stalkingFlag = stalkingFlag;
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
}


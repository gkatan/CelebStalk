package jaemolee.myapplication;

public class Action {

    String name;
    String forum;
    String text;
    long id; // ??
    String date;


    public Action (String name, String forum, String text, String date) {
        this.name = name;
        this.forum = forum;
        this.text = text;
        this.date = date;
    }

    public Action (String name, String forum, String text, String date, int id) {
        this.name = name;
        this.forum = forum;
        this.text = text;
        this.date = date;
        this.id = id;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public void setId (long id) {
        this.id = id;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public String getForum() {
        return this.forum;
    }

    public String getText() {
        return this.text;
    }

    public String getDate() {
        return this.date;
    }

    public long getId() {
        return this.id;
    }
}

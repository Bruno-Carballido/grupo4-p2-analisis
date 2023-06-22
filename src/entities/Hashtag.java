package entities;

import uy.edu.um.prog2.adt.LinkedList;
import uy.edu.um.prog2.adt.LinkedListImpl;

public class Hashtag {
    private long id;
    private String text;

    private LinkedList<Tweet> tweets;

    public Hashtag(long id, String text) {
        this.id = id;
        this.text = text;
        this.tweets = new LinkedListImpl<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LinkedList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(LinkedList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void addTweets(Tweet tweet) {
        this.tweets.add(tweet);
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

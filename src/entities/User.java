package entities;

import uy.edu.um.prog2.adt.LinkedList;
import uy.edu.um.prog2.adt.LinkedListImpl;

public class User {
    private long id;
    private String name;

    private boolean verificado;

    private LinkedList<Tweet> tweets = new LinkedListImpl<>();

    public User(long id, String name, boolean verificado) {
        this.id = id;
        this.name = name;
        this.verificado = verificado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(LinkedList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void appendTweets(Tweet tweet) {
        LinkedList<Tweet> t = this.tweets;
        t.add(tweet);
        this.tweets = t;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
}

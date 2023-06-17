package entities;

import uy.edu.um.prog2.adt.LinkedListImpl;

public class Hashtag {
    private long id;
    private String text;
    private LinkedListImpl<Tweet> listatweets = new LinkedListImpl<Tweet>();

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

    public LinkedListImpl<Tweet> getListatweets() {
        return listatweets;
    }

    public void setListatweets(LinkedListImpl<Tweet> listatweets) {
        this.listatweets = listatweets;
    }

    public Hashtag(long id, String text, LinkedListImpl<Tweet> listatweets) {
        this.id = id;
        this.text = text;
        this.listatweets = listatweets;
    }
}

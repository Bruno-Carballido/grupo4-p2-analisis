package entities;

import uy.edu.um.prog2.adt.LinkedListImpl;

public class Hashtag {
    private long id;
    private String text;

    public Hashtag(long id, String text) {
        this.id = id;
        this.text = text;
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

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

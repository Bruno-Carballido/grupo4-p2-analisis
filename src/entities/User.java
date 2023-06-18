package entities;

import uy.edu.um.prog2.adt.LinkedList;
import uy.edu.um.prog2.adt.LinkedListImpl;

public class User {
    private long id;
    private String name;
    private boolean verificado;
    private double favourite;

    public User(long id, String name, boolean verificado, double favourite) {
        this.id = id;
        this.name = name;
        this.verificado = verificado;
        this.favourite = favourite;
    }

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

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public double getFavourite() {
        return favourite;
    }

    public void setFavourite(double favourite) {
        this.favourite = favourite;
    }
}

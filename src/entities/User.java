package entities;

import java.time.LocalDate;

public class User {
    private long id;
    private String name;
    private boolean verificado;
    private double favourite;

    private LocalDate updateDate;

    public User(long id, String name, boolean verificado, double favourite, LocalDate date) {
        this.id = id;
        this.name = name;
        this.verificado = verificado;
        this.favourite = favourite;
        this.updateDate = date;
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

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return name + " | Favoritos: " + favourite;
    }
}

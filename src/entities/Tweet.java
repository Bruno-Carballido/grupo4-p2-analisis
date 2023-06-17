package entities;

public class Tweet {
    private long id;
    private String content;
    private String source;
    private String[] date;
    private boolean isRetweet;
    private User usuario;

    public Tweet(long id, String content, String source, String[] date, boolean isRetweet) {
        this.id = id;
        this.content = content;
        this.source = source;
        this.date = date;
        this.isRetweet = isRetweet;
    }

    public Tweet(long id, String content, String source, String[] date, boolean isRetweet, User usuario) {
        this.id = id;
        this.content = content;
        this.source = source;
        this.date = date;
        this.isRetweet = isRetweet;
        this.usuario = usuario;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

}

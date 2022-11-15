package com.example.java8keesun;

import java.util.Optional;

public class OnlineClass {

    private Integer id;
    private String title;
    private boolean isClosed;

    public Progress progress;

    public OnlineClass(Integer id, String title, boolean isClosed) {
        this.id = id;
        this.title = title;
        this.isClosed = isClosed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Optional<Progress> getProgress() {
        return Optional.ofNullable(progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}

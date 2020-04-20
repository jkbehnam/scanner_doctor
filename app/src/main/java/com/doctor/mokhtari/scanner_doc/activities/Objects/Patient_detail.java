package com.doctor.mokhtari.scanner_doc.activities.Objects;

public class Patient_detail {
    private String title;
    private String content;
public Patient_detail(String title, String content){
    this.title=title;
    this.content=content;
}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.pnlinh.cuasotinhyeu.crawler.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false, unique = true)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "time")
    private Date time;

    @Column(name = "link")
    private String url;

    @Column(name = "category")
    private String category;

    @Transient
    private String description;

    @Transient
    private String content;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    public Post() {
    }

    public Post(Builder builder) {
        this.title = builder.title;
        this.time = builder.time;
        this.url = builder.url;
        this.category = builder.category;
        this.description = builder.description;
        this.content = builder.content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static class Builder {
        public String title;
        public Date time;
        public String url;
        public String category;
        public String description;
        public String content;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder time(Date time) {
            this.time = time;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Post build() {
            return new Post(this);
        }

    }
}

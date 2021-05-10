package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

/** модель данных */
public class Post {
    private int id;
    private String name;
    private String text;
    private String link;
    private LocalDateTime data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getData() {
        return data;
    }

    public static class Builder {
        private Post post;

        public Builder() {
            post = new Post();
        }

        public Builder builderId(int id) {
            post.id = id;
            return this;
        }
        public Builder builderName(String name) {
            post.name = name;
            return this;
        }
        public Builder builderText(String text) {
            post.text = text;
            return this;
        }
        public Builder builderLink(String link) {
            post.link = link;
            return this;
        }
        public Builder builderData(LocalDateTime data) {
            post.data = data;
            return this;
        }
        public Post build() {
            return post;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(name, post.name)
                && Objects.equals(text, post.text)
                && Objects.equals(link, post.link)
                && Objects.equals(data, post.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, link, data);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", text='" + text + '\''
                + ", link='" + link + '\''
                + ", data=" + data
                + '}';
    }
}

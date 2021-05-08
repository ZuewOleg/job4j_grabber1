package ru.job4j.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String link;
    private String topicName;
    private String author;
    private String description;
    private int replies;
    private int views;
    private LocalDateTime postDate;
    // много полей -> используем класс Builder

    public static class Builder {
        private Post post;

        public Builder(Post post) {
            post =  new Post();
        }

        public Builder builderLink(String link) {
            post.link = link;
            return this;
        }

        public Builder builderTopicName(String topicName) {
            post.topicName = topicName;
            return this;
        }

        public Builder builderAuthor(String author) {
            post.author = author;
            return this;
        }

        public Builder builderDescription(String description) {
            post.description = description;
            return this;
        }

        public Builder builderReplies(int replies) {
            post.replies = replies;
            return this;
        }

        public Builder builderViews(int views) {
            post.views = views;
            return this;
        }

        public Builder builderPostDate(LocalDateTime postDate) {
            post.postDate = postDate;
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
        return Objects.equals(link, post.link) && Objects.equals(topicName, post.topicName)
                && Objects.equals(author, post.author) && Objects.equals(description, post.description)
                && Objects.equals(postDate, post.postDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, topicName, author, description, postDate);
    }

    @Override
    public String toString() {
        return "Post{"
                + "link='" + link + '\''
                + ", topicName='" + topicName + '\''
                + ", author='" + author + '\''
                + ", description='" + description + '\''
                + ", replies=" + replies
                + ", views=" + views
                + ", postDate=" + postDate
                + '}';
    }
}

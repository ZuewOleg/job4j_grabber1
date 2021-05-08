package ru.job4j.grabber;

import ru.job4j.utils.Post;

import java.io.IOException;
import java.util.List;

public interface Parse {
    List<Post> list(String link) throws IOException; // загружает список объявлений по ссылке

    Post detail(String link) throws IOException; // загружает детали объявления по ссылке
}

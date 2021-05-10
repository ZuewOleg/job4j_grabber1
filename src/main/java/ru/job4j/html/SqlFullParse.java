package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlFullParse implements Parse {
    public static LocalDateTime parse(String dataText) {
        return new SqlRuDateTimeParser().parse(dataText);
    }

    @Override
    public List<Post> list(String link) throws IOException { // Метод list загружает список всех постов
        // принимает переменную link -> адрес сайта
        List<Post> rsl = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            String links = href.attr("href");
            rsl.add(detail(links));
        }
        return rsl;
    }

    @Override
    public Post detail(String link) throws IOException { // Метод detail загружает детали одного поста
        // ссылку, вакансию, описание, дату обновления
        Post rsl = null;
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgTable");
        Element href = row.get(0);
        Element data = row.first();
            rsl = new Post.Builder()
                    .builderLink(href.attr("href"))
                    .builderName(data.child(1).child(0).text())
                    .builderText(row.get(1).text())
                    .builderData(parse(data.child(5).text()))
                    .build();
        return rsl;
    }

    public static void main(String[] args) throws IOException {
        SqlFullParse result = new SqlFullParse();
        List<Post> list = result.list("https://www.sql.ru/forum/job-offers");
        for (Post posts : list) {
            System.out.println(posts);
        }
    }
}


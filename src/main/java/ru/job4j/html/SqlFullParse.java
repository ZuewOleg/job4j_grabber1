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
        // нужно парсить описание не в виде HTML, а в виде текста, т.е. вызывать метод text()
        Post rsl = null;
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgTable");
        String description = row.first().select(".msgBody").get(1).text();
        String name = row.first().select(".messageHeader").text();
        String date = row.last().select(".msgFooter").text();
        date = date.substring(0, date.indexOf('[') - 1);
            rsl = new Post.Builder()
                    .builderLink(link)
                    .builderName(name)
                    .builderText(description)
                    .builderData(parse(date))
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


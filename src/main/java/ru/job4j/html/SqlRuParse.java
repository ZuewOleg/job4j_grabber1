package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.time.LocalDateTime;

public class SqlRuParse {
    public static LocalDateTime parse(String dataText) {
        return new SqlRuDateTimeParser().parse(dataText);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) { // цикл чтобы парсить 5 страниц
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get(); // url: ".../" + i,
            // полсе адреса ставим "/", чтобы перейти на адрес страницы и прибавляем i
            // было "https://www.sql.ru/forum/job-offers", стало "https://www.sql.ru/forum/job-offers/" + i
            // где i количесиво нужных сраниц, т.е. страниц до которых нужно парсить
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                // Ячейка с именем имеет аттрибут class=postslisttopic. jsoup может извлечь все элементы с этим аттрибутом
                Element href = td.child(0);
                /* Elements data = doc.getElementsByClass("forumTable").get(0).getElementsByTag("tr"); */
                Element data = td.parent();
                LocalDateTime ld = parse(data.child(5).text());
                // Нам нужен первый элемент. Это ссылка. У нее мы получаем адрес, текст и дату обновления.
                System.out.println(href.attr("href")); // ссылка
                System.out.println(href.text()); // текст
                /*System.out.println(doc.getElementsByTag("tr").get(5).text()); // дата */
                System.out.println(ld);
            }
        }
    }
}

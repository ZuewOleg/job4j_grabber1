package ru.job4j.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;

public class DownloadPost {
    public static LocalDateTime parse(String dataText) {
        return new SqlRuDateTimeParser().parse(dataText);
    }

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect(
                "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"
        ).get();
        Elements row = doc.select(".msgBody");
        for (Element td : row) {
            // Ячейка с именем имеет аттрибут class=msgBody. jsoup может извлечь все элементы с этим аттрибутом
            Element href = td.child(0);
            /* Elements data = doc.getElementsByClass("forumTable").get(0).getElementsByTag("tr"); */
            Element data = td.parent();
            LocalDateTime ld = parse(data.child(5).text());
            // Нам нужен первый элемент. Это ссылка. У нее мы получаем адрес, текст и дату обновления.
            Post post = new Post.Builder()
                    .builderLink(href.attr("href"))
                    .builderTopicName(data.child(1).child(0).text())
                    .builderAuthor(data.child(2).text())
                    .builderDescription(row.get(1).text())
                    .builderReplies(Integer.parseInt(data.child(3).text()))
                    .builderViews(Integer.parseInt(data.child(4).text()))
                    .builderPostDate(ld).build();
            System.out.println(post);
        }
    }
}

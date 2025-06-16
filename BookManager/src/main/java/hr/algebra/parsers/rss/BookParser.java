package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Article;
import hr.algebra.model.Book;
import hr.algebra.utilities.FileUtils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookParser {

    private static final String RSS_URL = "https://rss.nytimes.com/services/xml/rss/nyt/Books.xml";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    public static List<Book> parse() throws IOException, XMLStreamException {
        List<Book> books = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Book book = null;
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String localName = startElement.getName().getLocalPart();
                        tagType = TagType.from(localName);

                        if (tagType.isPresent() && tagType.get() == TagType.ITEM) {
                            book = new Book();
                            books.add(book);
                        }

                        if (tagType.isPresent() && (tagType.get() == TagType.ENCLOSURE || tagType.get() == TagType.MEDIA_CONTENT)) {
                            if (book != null && book.getPicturePath() == null) {
                                Attribute urlAttr = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                                if (urlAttr != null) {
                                    handlePicture(book, urlAttr.getValue());
                                }
                            }
                        }
                    }

                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && book != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            if (data.isEmpty()) continue;

                            switch (tagType.get()) {
                                case TITLE -> book.setTitle(data);
                                case LINK -> book.setLink(data);
                                case DESCRIPTION -> book.setDescription(data);
                                case PUB_DATE, DATE -> {
                                    try {
                                        book.setPublishedDate(LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME));
                                    } catch (Exception ex) {
                                        try {
                                            book.setPublishedDate(LocalDateTime.parse(data, DateTimeFormatter.ISO_DATE_TIME));
                                        } catch (Exception ignored) {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return books;
    }

    private static void handlePicture(Book book, String pictureUrl) {
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            book.setPicturePath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(BookParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        LINK("link"),
        DESCRIPTION("description"),
        ENCLOSURE("enclosure"),
        MEDIA_CONTENT("content"), // media:content
        PUB_DATE("pubDate"),
        DATE("date");             // dc:date

        private final String name;

        TagType(String name) {
            this.name = name;
        }

        public static Optional<TagType> from(String localName) {
            for (TagType t : values()) {
                if (t.name.equalsIgnoreCase(localName)) {
                    return Optional.of(t);
                }
            }
            return Optional.empty();
        }
    }
}
package com.unir.webdev.books.infrastructure.utils;

import com.unir.webdev.books.infrastructure.persistence.BookRepositoryJPA;
import com.unir.webdev.books.infrastructure.persistence.entity.BookEntity;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Author;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Available;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.BookName;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Image;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Isbn;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Language;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Rate;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.ReleaseYear;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class StartData {
    BookRepositoryJPA bookRepositoryJPA;

    @EventListener (ApplicationReadyEvent.class)
    public void persistDataAfterStart() {
        bookRepositoryJPA.saveAll(List.of(new BookEntity(UUID.randomUUID(),
                                                         new BookName("Cien años de " + "soledad"), new Isbn("978-0307474728"), new Image("https://images-na" + ".ssl-images-amazon" + ".com/images/I" + "/51ct4ckpvDL.jpg"), new Author("Gabriel García " + "Márquez"), new ReleaseYear(1967), new Rate(4.7), new Language("Español"), new Available(true)), new BookEntity(UUID.randomUUID(), new BookName("1984"), new Isbn("978-0451524935"), new Image("https://images-na" + ".ssl-images-amazon" + ".com/images/I" + "/41K7ymN5P7L.jpg"), new Author("George Orwell"), new ReleaseYear(1949), new Rate(4.6), new Language("Inglés"), new Available(true)), new BookEntity(UUID.randomUUID(), new BookName("El Principito"), new Isbn("978-0156012195"), new Image("https://images-na" + ".ssl-images-amazon" + ".com/images/I" + "/51YGcM9n8DL.jpg"), new Author("Antoine de " + "Saint-Exupéry"), new ReleaseYear(1943), new Rate(4.7), new Language("Francés"), new Available(true)), new BookEntity(UUID.randomUUID(), new BookName("To Kill a " + "Mockingbird"), new Isbn("978-0446310789"), new Image("https://images-na" + ".ssl-images-amazon" + ".com/images/I" + "/81gepf1e0lL.jpg"), new Author("Harper Lee"), new ReleaseYear(1960), new Rate(4.8), new Language("Inglés"), new Available(true)), new BookEntity(UUID.randomUUID(), new BookName("El señor de los " + "anillos"), new Isbn("978-0261103252"), new Image("https://images-na" + ".ssl-images-amazon" + ".com/images/I" + "/51EstVXM1tL.jpg"), new Author("J.R.R. Tolkien"), new ReleaseYear(1954), new Rate(4.9), new Language("Inglés"), new Available(true)), new BookEntity(UUID.randomUUID(), new BookName("Orgullo y " + "Prejuicio"), new Isbn("978-0141439518"), new Image("https://images-na" + ".ssl-images-amazon" + ".com/images/I" + "/81bR4c6y5-L.jpg"), new Author("Jane Austen"), new ReleaseYear(1813), new Rate(4.6), new Language("Inglés"), new Available(true))));
    }
}

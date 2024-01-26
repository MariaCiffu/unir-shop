package com.unir.webdev.books.application;

import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.domain.repository.BookRepository;
import com.unir.webdev.books.domain.response.Result;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@JqwikSpringSupport
@SpringBootTest
class GetAllBooksUseCaseTest {
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private GetAllBooksUseCase getAllBooksUseCase;

    /*@Property
    void nameIsAddedToHello(@ForAll @AlphaChars @StringLength (min = 1) String name) {
        String greeting = getAllBooksUseCase.getAllProducts(name);
        Assertions.assertTrue(greeting.contains(name));
    }*/
    @Test
    void getAllProducts() {
        Mockito.when(bookRepository.getAllProducts())
               .thenReturn(List.of(new Book()));
        Result<List<Book>, Object> allProducts = getAllBooksUseCase.getAllProducts();
        Assertions.assertAll(
                () -> Assertions.assertTrue(allProducts.isSuccess(), "El resultado " +
                                                                     "debería ser un " +
                                                                     "éxito"),

                () -> Assertions.assertEquals(1, allProducts.getSuccess()
                                                            .size(), "El " + "tama" +
                                                                     "ño de" + " la " +
                                                                     "lista de éxito " +
                                                                     "debería ser 1"));
    }
}
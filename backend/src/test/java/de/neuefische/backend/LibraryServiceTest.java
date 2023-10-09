package de.neuefische.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

	private LibraryRepository libraryRepository;
	private LibraryService libraryService;

	@BeforeEach
	void setUp() {
		libraryRepository = mock(LibraryRepository.class);
		libraryService = new LibraryService(libraryRepository);
	}

	@Test
	void whenGetAllBooks_calledWithEmptyRepo_returnEmptyList() {
		// Given
		when(libraryRepository.findAll()).thenReturn(List.of());

		// When
		List<Book> actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		List<Book> expected = List.of();
		assertEquals(expected, actual);
	}

	@Test
	void whenGetAllBooks_calledWithNonEmptyRepo_returnListOfRepoContent() {
		// Given
		when(libraryRepository.findAll()).thenReturn(List.of(
				new Book("123","Title1","Author1"),
				new Book("124","Title2","Author2"),
				new Book("125","Title3","Author3"),
				new Book("126","Title4","Author4"),
				new Book("127","Title5","Author5"),
				new Book("128","Title6","Author6")
		));

		// When
		List<Book> actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		List<Book> expected = List.of(
				new Book("123","Title1","Author1"),
				new Book("124","Title2","Author2"),
				new Book("125","Title3","Author3"),
				new Book("126","Title4","Author4"),
				new Book("127","Title5","Author5"),
				new Book("128","Title6","Author6")
		);
		assertEquals(expected, actual);
	}

	@Test
	void findBookById_Exist() {
		//GIVEN
		String id = "12";
		Book book12 = new Book(id,"title 12","author 12");

		when(libraryRepository.findById(id)).thenReturn(Optional.of(book12));

		//WHEN
		Book actual = libraryService.getBookById(id);

		//THEN
		Book expected = new Book("12","title 12","author 12");
		verify(libraryRepository).findById(id);
		assertEquals(expected,actual);
	}

	@Test
	void findBookById_NotExist(){
		//GIVEN
		String id ="33";
		Book book = new Book("33","title 33","author 33");

		when(libraryRepository.findById(id)).thenReturn(Optional.empty());
		//WHEN
		//THEN
		assertThrows(NoSuchElementException.class, ()->libraryService.getBookById(id));
	}


}
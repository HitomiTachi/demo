package com.example.demo.Service;
import com.example.demo.Model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    // Giả lập Database bằng List
    private List<Book> books = new ArrayList<>();

    // Thêm dữ liệu mẫu khi khởi chạy
    public BookService() {
        books.add(new Book(1, "Lập trình Java", "Nguyễn Văn A"));
        books.add(new Book(2, "Spring Boot Action", "John Doe"));
    }

    // 1. Lấy tất cả
    public List<Book> getAllBooks() {
        return books;
    }

    // 2. Tìm theo ID
    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // 3. Thêm mới
    public void addBook(Book book) {
        books.add(book);
    }

    // 4. Cập nhật
    public void updateBook(int id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.getId() == id) {
                // Cập nhật thông tin
                b.setTitle(updatedBook.getTitle());
                b.setAuthor(updatedBook.getAuthor());
                return; // Kết thúc khi tìm thấy
            }
        }
    }

    // 5. Xóa
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}
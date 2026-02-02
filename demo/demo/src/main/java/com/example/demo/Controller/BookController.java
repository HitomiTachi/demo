package com.example.demo.Controller;
import com.example.demo.Model.Book;
import com.example.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 1. Hiển thị danh sách tất cả sách
    @GetMapping
    public String getAllBooks(Model model, @RequestParam(required = false) String search) {
        List<Book> books;
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search);
            model.addAttribute("searchKeyword", search);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "books/list";
    }

    // 2. Hiển thị form thêm sách mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    // 3. Xử lý thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        // Tự động tạo ID nếu chưa có
        if (book.getId() == 0) {
            int maxId = bookService.getAllBooks().stream()
                    .mapToInt(Book::getId)
                    .max()
                    .orElse(0);
            book.setId(maxId + 1);
        }
        bookService.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Thêm sách thành công!");
        return "redirect:/books";
    }

    // 4. Hiển thị form sửa sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "books/edit";
    }

    // 5. Xử lý cập nhật sách
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute Book updatedBook, RedirectAttributes redirectAttributes) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
            return "redirect:/books";
        }
        updatedBook.setId(id);
        bookService.updateBook(id, updatedBook);
        redirectAttributes.addFlashAttribute("message", "Cập nhật sách thành công!");
        return "redirect:/books";
    }

    // 6. Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
        } else {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("message", "Xóa sách thành công!");
        }
        return "redirect:/books";
    }
}

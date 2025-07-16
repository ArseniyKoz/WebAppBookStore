package org.example.BookStore.providers;

import org.example.BookStore.util.ValueOfEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Имя не указано")
    @Column(name="name")
    private String name;  //
    @Pattern(regexp = "^\\p{L}+ \\p{L}+$", message = "Неккоректное имя автора")
    @NotBlank(message = "Автор не указан")
    @Column(name="author")
    private String author; //
    @ValueOfEnum(enumClass = lang.class, message = "Недопустимый язык")
    @NotBlank(message = "Язык не указан")
    @Column(name="language")
    private String language; //
    @Column(name="publisher")
    @NotBlank(message = "Издательство не указано")
    private String publisher;
    @Column(name="year")
    @NotNull(message = "Год выпуска не указан")
    @Positive(message = "Год выпуска не может быть меньше или равен 0")
    private Integer year; //
    @Column(name="genre")
    @NotBlank(message = "Жанр не указан")
    private String genre; //
    @Column(name="isbn")
    @NotBlank(message = "ISBN не указан")
//    @Pattern(regexp = "^\\d{3}-\\d-\\d{2}-\\d{6}-\\d$",message = "Неккоректный ISBN")
    private String ISBN; //
    @Column(name="price")
    @NotNull(message = "Цена не указана")
    private Double price;
    @Column(name="pagecount")
    @NotNull(message = "Количество страниц не указано")
    private Integer pageCount;
    @Column(name="annotation")
    private String annotation;
    @Column(name="rating")
    private Double rating;
    @NotNull(message = "Критерей новинки не указан")
    @Column(name="novelty")
    private Boolean novelty;
    @NotNull(message = "Количество на складе не указано")
    @Column(name="quantity")
    private Integer quantity;
    @Column(name="url")
    private String url;
    @NotNull
    @Column(name = "active")
    private Boolean active = true;

}


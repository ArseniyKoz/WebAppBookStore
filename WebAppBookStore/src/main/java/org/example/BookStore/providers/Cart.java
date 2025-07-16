package org.example.BookStore.providers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Person person;

	@ManyToOne
	private Book book;

	private Integer quantity;
	
	@Transient
	private Double totalPrice;
	
	@Transient
	public Double totalOrderPrice;

}

package co.istad.productapisimpledemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// generate value
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer usrId; //user that create the product
    @ManyToOne(fetch = FetchType.LAZY)
    //
    @JoinColumn(name = "category_id")
    private Category category;
}

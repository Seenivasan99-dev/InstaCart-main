package com.mycart.mycart.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String filename;
    private String filetype;
    @Lob
    private Blob image;
    private String downloadurl;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}

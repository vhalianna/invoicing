package com.tuempresa.invoicing.model;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Product {
 
    @Id @Column(length=9)
    int number;
 
    @Column(length=50) @Required
    String description;
 
    @ManyToOne( // The reference is persisted as a database relationship
    fetch=FetchType.LAZY, // The reference is loaded on demand
    optional=true) // The reference can have no value
    @DescriptionsList // Thus the reference is displayed using a combo
    Category category; // A regular Java reference
    
    @Money // The price property is used to store money
    BigDecimal price;  // BigDecimal is typically used for money

    @Stereotype("IMAGES_GALLERY") // A complete image gallery is available
    @Column(length=32) // The 32 length string is for storing the key of the gallery
    String photos;
     
    @TextArea// This is for a big text, a text area or equivalent will be used
    String remarks;
    
    public static Product findByNumber(int number) throws NoResultException { 			
 		Query query = org.openxava.jpa.XPersistence.getManager().createQuery("from Product as p where p.number = :number"); 
		query.setParameter("number", number);
		return (Product) query.getSingleResult();
 	}
    
}
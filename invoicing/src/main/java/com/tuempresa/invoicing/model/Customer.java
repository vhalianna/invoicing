package com.tuempresa.invoicing.model;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Getter @Setter
@View(name="Simple", // This view is used only when “Simple” is specified
members="number, name" // Shows only number and name in the same line
)

public class Customer {
	
	 @Id
	 @Column(length=6)
	 int number;
	 
	 @Column(length=50)
	 @Required
	 String name;
	 
	 @Embedded @NoFrame// This is the way to reference an embeddable class
	 Address address; // A regular Java reference

}

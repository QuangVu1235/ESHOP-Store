package com.project.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2290493639756120130L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sale")
	private Double sale;

	@Column(name = "description")
	private String description;
}

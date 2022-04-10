package com.project.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Products implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6296035743361313L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "price")
	private Double price;
	
	@Column(name = "priceSale")
	private Double priceSale;

	@Column(name = "imgUrl")
	private String imgUrl;
	
	@Column(name = "imgUrl1")
	private String imgUrl1;
	
	@Column(name = "imgUrl2")
	private String imgUrl2;
	
	@Column(name = "imgUrl3")
	private String imgUrl3;

	@Column(name = "description")
	private String description;

	@Column(name = "slug")
	private String slug;

	@Column(name = "isDeleted")
	private Boolean isDeleted;
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "unitId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"application", "hibernateLazyInitializer"})
	private UnitTypes unitType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "typeId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"application", "hibernateLazyInitializer"})
	private ProductTypes productType;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "saleId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"application", "hibernateLazyInitializer"})
	private Sale sale;
	
	
	



}

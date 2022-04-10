package com.project.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4227420991556590903L;

	@Id
	private String name;
	private Integer quantity;	
	private Double price;
	private String imgUrl;
	private String imgUrl1;
	private String imgUrl2;
	private String imgUrl3;
	private String description;
	private String slug;

}

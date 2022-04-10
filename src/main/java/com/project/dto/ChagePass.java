package com.project.dto;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChagePass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -238380471401083483L;
	private String oldpass;
	private String newpass;
	private String confirm;
	
}

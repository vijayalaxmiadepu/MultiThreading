package com.learning.springbootmultithreading.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	private int id;
	private String name;
    private String email;
    private List<String> phoneNumbers;
}

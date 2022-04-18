package com.learning.springbootmultithreading;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learning.springbootmultithreading.model.Customer;
import com.learning.springbootmultithreading.model.CustomerDB;
import com.learning.springbootmultithreading.model.Employee;
import com.learning.springbootmultithreading.model.EmployeeDB;


@SpringBootApplication
public class SpringbootMultithreadingApplication {

	public static void main(String[] args) {
		mapVsFlatmap();
		mapAndReduce();
		sortByEmployeeNamesUsingComparator();
		consumerFunctionalInterfaceExample();
		SpringApplication.run(SpringbootMultithreadingApplication.class, args);
	}

	
	private static void mapVsFlatmap() {
		List<Customer> customers = CustomerDB.getAll();
		
		List<String> emails = customers.stream().map(customer -> customer.getEmail()).collect(Collectors.toList());
		System.out.println("Emails: "+emails);
		
		List<List<String>> phoneNumbers = customers.stream().map(customer -> customer.getPhoneNumbers()).collect(Collectors.toList());
		System.out.println("PhoneNumbers: "+phoneNumbers);
		
		List<String> phones = customers.stream().flatMap(customer -> customer.getPhoneNumbers().stream()).collect(Collectors.toList());
		System.out.println("Phones::" +phones);
		
	}
	
	private static void mapAndReduce() {
		List<Integer> numbers = Arrays.asList(3,9,8,34,10,3,8,10);
		
		List<String> words = Arrays.asList("Vijayalaxmi", "VijayaManojKumar", "Vijayalakshmi");
		
		//before java 8
		int sum =0;
		for(int no: numbers) {
			sum = sum+no;
		}
		System.out.println("sum before java8:: "+sum);
		
		//in java8 with mapToInt
		Integer sumWithMapToInt = numbers.stream().mapToInt(i->i).sum();
		System.out.println("sumWithMapToInt::"+sumWithMapToInt);
		
		//The boxed method converts the int primitive values of an IntStream into a stream of Integer objects
		IntStream getDistinctNum = numbers.stream().mapToInt(i->i).distinct().sorted();
		System.out.println("getDistinctNum::"+getDistinctNum.boxed().collect(Collectors.toList()));
		
		//In java8 with Reduce
		Integer sumWithReduce = numbers.stream().reduce(0, (a,b)-> a + b);
		System.out.println("sumWithReduce::"+sumWithReduce);
		
		//In java8 with sumWithMethodReference
		Optional<Integer> sumWithMethodReference = numbers.stream().reduce(Integer::sum);
		System.out.println(sumWithMethodReference);
		
		//In java8 with minWithMethodReference
		Optional<Integer> minWithMethodReference = numbers.stream().reduce(Integer::min);
		System.out.println(minWithMethodReference);
		
		//In java8 with minWithMethodReference
		Optional<Integer> maxWithMethodReference = numbers.stream().reduce(Integer::max);
		System.out.println(maxWithMethodReference);
		
		
		// to get max value with maxValueWithReduce
		Integer maxValueWithReduce = numbers.stream().reduce(0, (a,b) -> a > b ? a : b);
		System.out.println(maxValueWithReduce);
		
		//get max length string
		String maxLengthString = words.stream().reduce((w1, w2) -> w1.length() > w2.length() ? w1 : w2).get(); 
		System.out.println(maxLengthString);
		
		Double avgSalary = EmployeeDB.getEmployees().stream().filter(emp -> emp.getGrade().equals("A")).map(emp -> emp.getSalary()).mapToDouble(i->i).average().getAsDouble();
		System.out.println(avgSalary);
		
	}
	
	private static void sortByEmployeeNamesUsingComparator() {
		List<Employee> emp = EmployeeDB.getEmployees();
		System.out.println("Unsorted List::"+emp);
		
		// In java8 with lambda expression
		Collections.sort(emp, ( e1,  e2) -> e2.getName().compareTo(e1.getName()));
		
		
		//before java8
		Collections.sort(emp, new Comparator<Employee>() {

			@Override
			public int compare(Employee e1, Employee e2) {
				return e2.getName().compareTo(e1.getName());
			}
		});
		
		System.out.println("Sorted List::" +emp);
	}
	
	private static void consumerFunctionalInterfaceExample() {
		// lambda with foreach
		List<Integer> numbers = Arrays.asList(3,9,8,34,10,3,8,10);
		numbers.stream().forEach(t -> System.out.println("numbers::"+t));
	}
	
}

package com.example.dbdemo.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>,
        QueryByExampleExecutor<Employee>   {

    List<Employee> findByLastNameContaining(@Param("lastname") String lastname);
    List<Employee> findByLastName(@Param("lastname") String lastname);
    List<Employee> findByEmailContaining(@Param("email") String email);
//    List<Employee> findByFirstNameContaining(@Param("name") String name);
//    List<Employee> findByFirstNameContainingAndLastNameContaining(@Param("firstname") String firstname, @Param("lastname") String lastname);
//    List<Employee> findByAgeGreaterThanEqual(@Param("age") Integer age);
//    List<Employee> findByAgeLessThan(@Param("age") Integer age);

}
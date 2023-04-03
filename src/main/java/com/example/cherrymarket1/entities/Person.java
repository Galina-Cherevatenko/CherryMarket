package com.example.cherrymarket1.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2, max = 100, message = "Address should be between 2 and 100 characters")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 12, message = "Phone should be between 10 and 12 characters")
    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    @NotEmpty(message="Email should not be empty")
    @Email
    private String email;

    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;
    @OneToMany(mappedBy = "owner")
    private List<Order> orders;

    public Person(int id, String name, String address, String phone, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Person(String name, String address, String phone, String email, String password, String role) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Person() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order){
        if(this.orders==null)
            this.orders = new ArrayList<>();
        this.orders.add(order);
        order.setOwner(this);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && address.equals(person.address) && phone.equals(person.phone) && email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone, email);
    }
}

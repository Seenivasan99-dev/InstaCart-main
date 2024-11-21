package com.mycart.mycart.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String LastName;

    private String email;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private Cart cart;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER,cascade =
            {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private Collection<Role> roles=new HashSet<>();
}

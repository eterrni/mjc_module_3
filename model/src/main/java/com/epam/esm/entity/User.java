package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orderList;

}

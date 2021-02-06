package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private int id;

    @Column(nullable = false)
    private BigDecimal price;
    @Column(insertable = false, updatable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_user", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "gift_certificate_has_orders",
            joinColumns = @JoinColumn(name = "orders_id_order", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id", nullable = false)
    )
    private List<GiftCertificate> giftCertificateList;

}


package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;
    @Column
    private Integer duration;
    @Column(name = "create_date", insertable = false, updatable = false)
    private LocalDateTime createDate;
    @Column(name = "last_update_date", insertable = false, updatable = false)
    private LocalDateTime lastUpdateDate;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "gift_certificate_has_tag",
            joinColumns = {@JoinColumn(name = "gift_certificate_id_gift_certificate")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id_tag")}
    )
    private List<Tag> tags;

}

package com.example.moneymanagerbe.domain.entity;

import com.example.moneymanagerbe.domain.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category extends DateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)", unique = true)
    private String id;

    @Nationalized
    @Column(nullable = false)
    private String type;

    @Nationalized
    @Column(nullable = false)
    private String name;

    @Column
    private String imageUrl;

    // Link to table Transaction
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    // Link to table User
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_CATEGORY"))
    private User user;
}

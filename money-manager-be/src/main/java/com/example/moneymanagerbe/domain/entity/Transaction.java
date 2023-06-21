package com.example.moneymanagerbe.domain.entity;

import com.example.moneymanagerbe.domain.entity.common.DateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transactions")
public class Transaction extends DateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)", unique = true)
    private String id;

    @Nationalized
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float total;

    @Column(nullable = false)
    private LocalDate date;

    @Nationalized
    private String location;

    @Nationalized
    private String withPerson;

    private String imageUrl;

    // Link to table User
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_TRANSACTION"))
    private User user;

    // Link to table Category
    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY"))
    private Category category;

    // Link to table Budget
    @ManyToOne
    @JoinColumn(name = "budget_id", foreignKey = @ForeignKey(name = "FK_BUDGET"))
    private Budget budget;
}

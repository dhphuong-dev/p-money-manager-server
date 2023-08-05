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
@Table(name = "wallets")
public class Wallet extends DateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)", unique = true)
    private String id;

    @Nationalized
    @JoinColumn(nullable = false)
    private String name;

    @JoinColumn(nullable = false)
    private float total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wallet")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    // Link to table User
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_WALLET"))
    private User user;
    
    public float getTotal() {
        float sum = 0;
        for (Transaction transaction : transactions) sum += transaction.getTotal();
        return sum;
    }
}

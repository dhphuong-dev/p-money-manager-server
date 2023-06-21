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
@Table(name = "users")
public class User extends DateAuditing {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)", unique = true)
  private String id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @Nationalized
  @Column(nullable = false)
  private String fullName;

  private String avatar;

  // Link to table Budget
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  private Set<Budget> budgets = new HashSet<>();

  // Link to table Category
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  private Set<Category> categories = new HashSet<>();

  // Link to table Transaction
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  private Set<Transaction> transactions = new HashSet<>();

  //Link to table Role
  @ManyToOne
  @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
  private Role role;

}

package com.devsu.corebancario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "account ")
@Data
@NoArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_number", nullable = false)
  private String accountNumber;

  @Column(name = "account_type")
  private String accountType;

  private String status;

  @Column(name = "initial_balance")
  private Double initialBalance;


  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  @JsonBackReference
  private Client client;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
  @JsonManagedReference
  private List<Movement> movements = new ArrayList<>();

}

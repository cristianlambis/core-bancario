package com.devsu.corebancario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;


@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
public class Movement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;

  @Column(name = "movement_type")
  private String movementType;

  private double value;

  private double balance;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @JsonBackReference
  private Account account;

}

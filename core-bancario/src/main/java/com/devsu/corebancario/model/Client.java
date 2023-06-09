package com.devsu.corebancario.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "clients")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Person {


  @Column(name = "client_id")
  private String clientId;

  private char[] password;

  private String status;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
  @JsonManagedReference
  private List<Account> accounts;

}

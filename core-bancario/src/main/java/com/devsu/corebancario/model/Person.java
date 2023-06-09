package com.devsu.corebancario.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "full_name")
  private String fullName;

  private String gender;

  private Integer age;

  @Column(name = "identification_number")
  private Long identificationNumber;

  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

}

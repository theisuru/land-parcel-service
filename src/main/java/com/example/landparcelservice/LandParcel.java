package com.example.landparcelservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LandParcel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long objectId;
  private String name;
  private Status status;
  private double area;
  private boolean constraints;

  enum Status {
    SAVED,
    SHORT_LISTED,
    UNDER_CONSIDERATION,
    APPROVED
  }
}

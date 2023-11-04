package tn.esprit.tiramisu.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Operator implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idOperateur;
    String fname;
    String lname;
    String password;
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Invoice> invoices;

}
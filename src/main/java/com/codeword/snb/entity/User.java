package com.codeword.snb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String cellNo;
    private String idNo;
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();

}

package com.thai27.chiatien.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "chia_tien_user")
@Data
public class ChiaTienUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;

    private String fullName;

    @ManyToMany(targetEntity = Bank.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bank> banks;

    @ManyToMany(targetEntity = ChiaTienGroup.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiaTienGroup> chiaTienGroups;

}

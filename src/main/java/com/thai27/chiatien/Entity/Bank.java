package com.thai27.chiatien.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "bank")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String bankName;

    private String bankNumber;

    private String qrCode;

    @ManyToMany(targetEntity = ChiaTienUser.class, mappedBy = "banks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChiaTienUser> chiaTienUsers;
}

package com.thai27.chiatien.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "chia_tien_group")
@Data
public class ChiaTienGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String groupName;

    @ManyToMany(targetEntity = ChiaTienUser.class, mappedBy = "chiaTienGroups", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChiaTienUser> chiaTienUsers;
}

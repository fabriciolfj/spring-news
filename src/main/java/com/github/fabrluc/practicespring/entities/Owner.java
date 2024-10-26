package com.github.fabrluc.practicespring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NamedEntityGraphs({
        @NamedEntityGraph(name = "owner-with-pets",
                attributeNodes = {
                        @NamedAttributeNode("pets")
                })
})
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private List<Pet> pets = new ArrayList<>();
}

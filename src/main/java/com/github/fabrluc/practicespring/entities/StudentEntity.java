package com.github.fabrluc.practicespring.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity {

    @Id
    @Column(name = "student_id", length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admit_year", length = 4)
    private String admitYear;

    //@Convert(converter = AddressAttributeConverter.class)
    @Column(name = "address", length = 500, columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Address address;
}

package com.github.fabrluc.practicespring.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Product {

    @Id
    private Long id;

    @NotNull
    @Size(max = 255, message = "the property 'name' must be less then or equal to 255 characteres")
    private String name;

    @NotNull
    private BigDecimal price;
}

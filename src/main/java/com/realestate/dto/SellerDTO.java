package com.realestate.dto;

import com.realestate.entity.person.Seller;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 números")
    private String cpf;

    private String rg;

    @Email(message = "Email inválido")
    private String email;

    private String phone;
    private String address;
    private LocalDate registrationDate;

    public static SellerDTO fromEntity(Seller seller) {
        return new SellerDTO(
                seller.getId(),
                seller.getName(),
                seller.getCpf(),
                seller.getRg(),
                seller.getEmail(),
                seller.getPhone(),
                seller.getAddress(),
                seller.getRegistrationDate()
        );
    }

    public Seller toEntity() {
        Seller seller = new Seller();
        seller.setId(this.id);
        seller.setName(this.name);
        seller.setCpf(this.cpf);
        seller.setRg(this.rg);
        seller.setEmail(this.email);
        seller.setPhone(this.phone);
        seller.setAddress(this.address);
        return seller;
    }
}


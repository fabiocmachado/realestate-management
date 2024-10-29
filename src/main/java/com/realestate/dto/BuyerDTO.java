package com.realestate.dto;

import com.realestate.entity.person.Buyer;
import com.realestate.entity.person.Seller;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDTO {

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

    public static BuyerDTO fromEntity(Buyer buyer) {
        return new BuyerDTO(
                buyer.getId(),
                buyer.getName(),
                buyer.getCpf(),
                buyer.getRg(),
                buyer.getEmail(),
                buyer.getPhone(),
                buyer.getAddress(),
                buyer.getRegistrationDate()
        );
    }

    public Buyer toEntity() {
        Buyer buyer = new Buyer();
        buyer.setId(this.id);
        buyer.setName(this.name);
        buyer.setCpf(this.cpf);
        buyer.setRg(this.rg);
        buyer.setEmail(this.email);
        buyer.setPhone(this.phone);
        buyer.setAddress(this.address);
        return buyer;
    }

}


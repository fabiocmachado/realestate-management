package com.realestate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realestate.entity.person.Buyer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$",
            message = "Formato de telefone inválido")
    private String phone;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 números")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter apenas números")
    private String cpf;

    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstContactDate;

    public static BuyerDTO fromEntity(Buyer buyer) {
        return new BuyerDTO(
                buyer.getId(),
                buyer.getName(),
                buyer.getCpf(),
                buyer.getRg(),
                buyer.getEmail(),
                buyer.getPhone(),
                buyer.getAddress(),
                buyer.getRegistrationDate(),
                buyer.getFirstContactDate()
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
        buyer.setFirstContactDate(this.firstContactDate);
        return buyer;
    }
}
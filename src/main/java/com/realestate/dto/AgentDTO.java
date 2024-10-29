package com.realestate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realestate.entity.person.Agent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {
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

    @NotBlank(message = "O número da licença é obrigatório")
    private String licenseNumber;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 números")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter apenas números")
    private String cpf;

    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiringDate;

    private Integer propertyCount;

    public static AgentDTO fromEntity(Agent agent) {
        return AgentDTO.builder()
                .id(agent.getId())
                .name(agent.getName())
                .email(agent.getEmail())
                .phone(agent.getPhone())
                .licenseNumber(agent.getLicenseNumber())
                .address(agent.getAddress())
                .cpf(agent.getCpf())
                .rg(agent.getRg())
                .hiringDate(agent.getHiringDate())
                .propertyCount(agent.getProspectedProperties() != null ?
                        agent.getProspectedProperties().size() : 0)
                .build();
    }

    public Agent toEntity() {
        return Agent.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .licenseNumber(this.licenseNumber)
                .address(this.address)
                .cpf(this.cpf)
                .rg(this.rg)
                .hiringDate(this.hiringDate)
                .prospectedProperties(new ArrayList<>())
                .build();
    }
}
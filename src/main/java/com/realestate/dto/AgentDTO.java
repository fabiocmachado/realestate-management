package com.realestate.dto;

import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Buyer;
import com.realestate.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone inválido")
    private String phone;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O número da licença é obrigatório")
    private String licenseNumber;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    private Buyer responsibleBuyer;

    private LocalDate registrationDate;

    private Boolean hasAdminPermissions = false;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    private UserRole role;

    public static AgentDTO fromEntity(Agent agent) {
        if (agent == null) {
            return null;
        }

        AgentDTO dto = new AgentDTO();
        dto.setId(agent.getId());
        dto.setName(agent.getName());
        dto.setEmail(agent.getEmail());
        dto.setPhone(agent.getPhone());
        dto.setCpf(agent.getCpf());
        dto.setLicenseNumber(agent.getLicenseNumber());
        dto.setAddress(agent.getAddress());
        dto.setRg(agent.getRg());
        dto.setPassword(agent.getPassword());
        dto.setRegistrationDate(agent.getRegistrationDate());
        dto.setHasAdminPermissions(agent.getHasAdminPermissions());
        dto.setRole(agent.getRole());
        return dto;
    }

    public Agent toEntity() {
        Agent agent = new Agent();
        agent.setId(this.id);
        agent.setName(this.name);
        agent.setEmail(this.email);
        agent.setPhone(this.phone);
        agent.setCpf(this.cpf);
        agent.setLicenseNumber(this.licenseNumber);
        agent.setAddress(this.address);
        agent.setRg(this.rg);
        agent.setPassword(this.password);
        agent.setRegistrationDate(this.registrationDate);
        agent.setHasAdminPermissions(this.hasAdminPermissions);
        agent.setRole(this.role);
        return agent;
    }
}

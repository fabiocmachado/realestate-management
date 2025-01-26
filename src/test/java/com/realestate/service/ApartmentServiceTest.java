package com.realestate.service;

import com.realestate.dto.ApartmentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Apartment;
import com.realestate.mapper.ApartmentMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.ApartmentRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApartmentServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private ApartmentMapper apartmentMapper;

    @InjectMocks
    private ApartmentService apartmentService;

    private ApartmentDTO apartmentDTO;
    private Apartment apartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando objeto mockado ApartmentDTO
        apartmentDTO = new ApartmentDTO();
        apartmentDTO.setSellerId(1L);
        apartmentDTO.setAgentId(1L);
        apartmentDTO.setPropertyCode("12345");

        // Criando objeto mockado Apartment
        apartment = new Apartment();
        apartment.setPropertyCode("12345");
    }

    @Test
    void testCreateApartment() {
        // Substituindo o mock para Seller e Agent corretamente
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller())); // Criando um mock real de Seller
        when(agentRepository.findById(anyLong())).thenReturn(Optional.of(new Agent())); // Criando um mock real de Agent
        when(apartmentMapper.toEntity(any())).thenReturn(apartment);
        when(apartmentRepository.save(any())).thenReturn(apartment);
        when(apartmentMapper.toDTO(any())).thenReturn(apartmentDTO);

        ApartmentDTO createdApartment = apartmentService.createApartment(apartmentDTO);

        assertNotNull(createdApartment);
        assertEquals(apartmentDTO.getPropertyCode(), createdApartment.getPropertyCode());

        verify(sellerRepository).findById(anyLong());
        verify(agentRepository).findById(anyLong());
        verify(apartmentRepository).save(any());
        verify(apartmentMapper).toDTO(any());
    }


    @Test
    void testGetApartmentByCode() {
        when(apartmentRepository.findByPropertyCode(anyString())).thenReturn(apartment);
        when(apartmentMapper.toDTO(any())).thenReturn(apartmentDTO);

        ApartmentDTO retrievedApartment = apartmentService.getApartmentByCode("12345");

        assertNotNull(retrievedApartment);
        assertEquals("12345", retrievedApartment.getPropertyCode());

        verify(apartmentRepository).findByPropertyCode(anyString());
    }

    @Test
    void testGetApartmentByCode_NotFound() {
        when(apartmentRepository.findByPropertyCode(anyString())).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            apartmentService.getApartmentByCode("12345");
        });

        assertEquals("Apartment not found with code: 12345", exception.getMessage());
    }

    @Test
    void testGetAllApartments() {
        Pageable pageable = PageRequest.of(0, 10);

        // Criando uma lista de apartamentos para o mock
        List<Apartment> apartmentsList = new ArrayList<>();
        apartmentsList.add(apartment); // Adicionando um apartamento à lista

        // Criando um mock de Page<Apartment> usando PageImpl
        Page<Apartment> apartmentPage = new PageImpl<>(apartmentsList, pageable, apartmentsList.size());

        when(apartmentRepository.findAll(pageable)).thenReturn(apartmentPage);
        when(apartmentMapper.toDTO(any())).thenReturn(apartmentDTO);

        // Chamando o método no service
        Page<ApartmentDTO> apartments = apartmentService.getAllApartments(pageable);

        assertNotNull(apartments);
        assertEquals(1, apartments.getTotalElements());
        verify(apartmentRepository).findAll(pageable);
    }

    @Test
    void testUpdateApartment() {
        // Criando instâncias reais de Seller e Agent nos mocks
        when(apartmentRepository.findByPropertyCode(anyString())).thenReturn(apartment);
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller())); // Mock para Seller
        when(agentRepository.findById(anyLong())).thenReturn(Optional.of(new Agent())); // Mock para Agent
        when(apartmentRepository.save(any())).thenReturn(apartment);
        when(apartmentMapper.toDTO(any())).thenReturn(apartmentDTO);

        ApartmentDTO updatedApartment = apartmentService.updateApartment("12345", apartmentDTO);

        assertNotNull(updatedApartment);
        assertEquals("12345", updatedApartment.getPropertyCode());

        verify(apartmentRepository).findByPropertyCode(anyString());
        verify(sellerRepository).findById(anyLong());
        verify(agentRepository).findById(anyLong());
        verify(apartmentRepository).save(any());
        verify(apartmentMapper).toDTO(any());
    }


    @Test
    void testUpdateApartment_NotFound() {
        when(apartmentRepository.findByPropertyCode(anyString())).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            apartmentService.updateApartment("12345", apartmentDTO);
        });

        assertEquals("Apartment not found with code: 12345", exception.getMessage());
    }

    @Test
    void testDeleteApartment() {
        when(apartmentRepository.findByPropertyCode(anyString())).thenReturn(apartment);

        apartmentService.deleteApartment("12345");

        verify(apartmentRepository).delete(apartment);
    }

    @Test
    void testDeleteApartment_NotFound() {
        when(apartmentRepository.findByPropertyCode(anyString())).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            apartmentService.deleteApartment("12345");
        });

        assertEquals("Apartment not found with code: 12345", exception.getMessage());
    }
}

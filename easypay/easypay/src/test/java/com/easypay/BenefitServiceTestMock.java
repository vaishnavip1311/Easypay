package com.easypay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.Benefit;
import com.easypay.repository.BenefitRepository;
import com.easypay.service.BenefitService;


@SpringBootTest
public class BenefitServiceTestMock {

    @InjectMocks
    private BenefitService benefitService;

    @Mock
    private BenefitRepository benefitRepository;

    private Benefit benefit;

    @BeforeEach
    public void init() {
        benefit = new Benefit();
        benefit.setId(1);
        benefit.setBenefitName("Health Insurance");
        benefit.setBenefitType("Medical");
        benefit.setAllowanceAmount(2000.00);
        benefit.setDescription("Covers medical expenses");
        System.out.println("Benefit created: " + benefit);
    }

    @Test
    public void testAddBenefit_Success() {
        when(benefitRepository.save(benefit)).thenReturn(benefit);
        Benefit result = benefitService.addBenefit(benefit);
        assertEquals(benefit, result);
    }

    @Test
    public void testGetAllBenefits() {
        when(benefitRepository.findAll()).thenReturn(List.of(benefit));
        List<Benefit> list = benefitService.getAllBenefits();
        assertEquals(1, list.size());
        assertEquals(benefit.getBenefitName(), list.get(0).getBenefitName());
    }

    @Test
    public void testGetAllBenefits_EmptyList() {
        when(benefitRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(benefitService.getAllBenefits().isEmpty());
    }

    @Test
    public void testGetBenefitById_Valid() {
        when(benefitRepository.findById(1)).thenReturn(Optional.of(benefit));
        Benefit result = benefitService.getBenefit(1);
        assertEquals(benefit, result);
    }

    @Test
    public void testGetBenefitById_Invalid() {
        when(benefitRepository.findById(99)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> benefitService.getBenefit(99));
        assertEquals("Id given invalid".toLowerCase(), ex.getMessage().toLowerCase());
    }

    @Test
    public void testUpdateBenefit_FullUpdate() {
        Benefit updated = new Benefit();
        updated.setBenefitName("Life Cover");
        updated.setBenefitType("Insurance");
        updated.setAllowanceAmount(5000.0);
        updated.setDescription("Covers full life term");

        when(benefitRepository.findById(1)).thenReturn(Optional.of(benefit));
        when(benefitRepository.save(any(Benefit.class))).thenReturn(benefit);

        Benefit result = benefitService.updateBenefit(1, updated);

        assertEquals("Life Cover", result.getBenefitName());
        assertEquals("Insurance", result.getBenefitType());
        assertEquals(5000.0, result.getAllowanceAmount());
        assertEquals("Covers full life term", result.getDescription());
    }

    @Test
    public void testUpdateBenefit_PartialUpdate() {
        Benefit partial = new Benefit();
        partial.setBenefitName("Food Coupons"); 

        when(benefitRepository.findById(1)).thenReturn(Optional.of(benefit));
        when(benefitRepository.save(any(Benefit.class))).thenReturn(benefit);

        Benefit result = benefitService.updateBenefit(1, partial);
        assertEquals("Food Coupons", result.getBenefitName());
        assertEquals("Medical", result.getBenefitType()); // unchanged
    }

    @Test
    public void testUpdateBenefit_EmptyUpdateObject() {
        Benefit empty = new Benefit(); 

        when(benefitRepository.findById(1)).thenReturn(Optional.of(benefit));
        when(benefitRepository.save(any(Benefit.class))).thenReturn(benefit);

        Benefit result = benefitService.updateBenefit(1, empty);
        assertEquals("Health Insurance", result.getBenefitName());
        assertEquals(2000.0, result.getAllowanceAmount());
    }

    @Test
    public void testUpdateBenefit_InvalidId() {
        when(benefitRepository.findById(100)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> benefitService.updateBenefit(100, new Benefit()));
        assertEquals("Benefit not found id given is invalid.".toLowerCase(), ex.getMessage().toLowerCase());
    }

    @Test
    public void testDeleteBenefit_Valid() {
        when(benefitRepository.existsById(1)).thenReturn(true);
        doNothing().when(benefitRepository).deleteById(1);

        boolean result = benefitService.deleteBenefitById(1);
        assertTrue(result);
        verify(benefitRepository).deleteById(1);
    }

    @Test
    public void testDeleteBenefit_InvalidId() {
        when(benefitRepository.existsById(88)).thenReturn(false);
        boolean result = benefitService.deleteBenefitById(88);
        assertFalse(result);
        verify(benefitRepository, never()).deleteById(88);
    }

    @AfterEach
    public void clear() {
        benefit = null;
        System.out.println("Benefit object cleared: " + benefit);
    }
}
package com.easypay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.easypay.model.Payslip;
import com.easypay.repository.PayrollRepository;
import com.easypay.repository.PayslipRepository;
import com.easypay.service.PayslipService;

@SpringBootTest
public class PayslipServiceTest {
	
	@InjectMocks
	private PayslipService payslipService;
	
	@Mock
    private PayslipRepository payslipRepository;

    @Mock
    private PayrollRepository payrollRepository;
    
    @Test
    public void getAllPayslipsTest() {
    	List<Payslip> payslipList = Arrays.asList(new Payslip(), new Payslip());
        when(payslipRepository.findAll()).thenReturn(payslipList);

        // Act
        List<Payslip> result = payslipService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(payslipRepository).findAll();
    }

}

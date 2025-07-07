package com.easypay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.easypay.model.Benefit;
import com.easypay.model.Employee;
import com.easypay.model.EmployeeBenefit;
import com.easypay.repository.BenefitRepository;
import com.easypay.repository.EmployeeBenefitRepository;
import com.easypay.repository.EmployeeRepository;
import com.easypay.service.EmployeeBenefitService;

@SpringBootTest
public class EmployeeBenefitServiceTestMock {

    @InjectMocks
    private EmployeeBenefitService employeeBenefitService;

    @Mock
    private EmployeeBenefitRepository employeeBenefitRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BenefitRepository benefitRepository;

    private Employee employee;
    private Benefit benefit1;
    private Benefit benefit2;

    @BeforeEach
    public void setup() {
        employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setEmployementType("part-time");

        benefit1 = new Benefit();
        benefit1.setId(101);
        benefit1.setBenefitName("Internet Reimbursement");

        benefit2 = new Benefit();
        benefit2.setId(102);
        benefit2.setBenefitName("Meal Card Allowance");

        System.out.println("Test data initialized.");
    }

    @Test
    public void testAssignBenefitToEmployee_PartTime() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(benefitRepository.findAll()).thenReturn(List.of(benefit1, benefit2));
        when(employeeBenefitRepository.save(any(EmployeeBenefit.class))).thenReturn(new EmployeeBenefit());

        employeeBenefitService.assignBenefitToEmployee(1);

        verify(employeeBenefitRepository, times(2)).save(any(EmployeeBenefit.class));
    }

    @Test
    public void testAssignBenefitToEmployee_Intern() {
        employee.setEmployementType("intern");
        Benefit internBenefit = new Benefit();
        internBenefit.setId(103);
        internBenefit.setBenefitName("Learning & Development");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(benefitRepository.findAll()).thenReturn(List.of(internBenefit, benefit1));

        employeeBenefitService.assignBenefitToEmployee(1);

        verify(employeeBenefitRepository, times(1)).save(any(EmployeeBenefit.class));
    }

    @Test
    public void testAssignBenefitToEmployee_Contract() {
        employee.setEmployementType("contract");

        Benefit wfh = new Benefit();
        wfh.setId(104);
        wfh.setBenefitName("Work-from-Home Setup");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(benefitRepository.findAll()).thenReturn(List.of(benefit1, wfh));

        employeeBenefitService.assignBenefitToEmployee(1);

        verify(employeeBenefitRepository, times(2)).save(any(EmployeeBenefit.class));
    }

    @Test
    public void testAssignBenefitToEmployee_FullTime() {
        employee.setEmployementType("full-time");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(benefitRepository.findAll()).thenReturn(List.of(benefit1, benefit2));

        employeeBenefitService.assignBenefitToEmployee(1);

        verify(employeeBenefitRepository, times(2)).save(any(EmployeeBenefit.class));
    }

    @Test
    public void testAssignBenefitToEmployee_InvalidEmployee() {
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> employeeBenefitService.assignBenefitToEmployee(999));
        assertEquals("Invalid id given", ex.getMessage());
    }

    @Test
    public void testAssignBenefitToEmployee_UnknownType() {
        employee.setEmployementType("freelancer");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> employeeBenefitService.assignBenefitToEmployee(1));
        assertEquals("Unknown employment type: freelancer", ex.getMessage());
    }

    @Test
    public void testGetBenefitForEmployee() {
        EmployeeBenefit eb = new EmployeeBenefit();
        eb.setStatus("active");
        when(employeeBenefitRepository.getBenefitsByEmployeeId(1)).thenReturn(List.of(eb));

        List<EmployeeBenefit> list = employeeBenefitService.getBenefitForEmployee(1);
        assertEquals(1, list.size());
        assertEquals("active", list.get(0).getStatus());
    }

    @Test
    public void testUnassignBenefit_Success() {
        when(employeeBenefitRepository.unassignBenefit(1, 101)).thenReturn(1);

        assertDoesNotThrow(() -> employeeBenefitService.unassignBenefitForEmployee(1, 101));
    }

    @Test
    public void testUnassignBenefit_Failure() {
        when(employeeBenefitRepository.unassignBenefit(1, 999)).thenReturn(0);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> employeeBenefitService.unassignBenefitForEmployee(1, 999));
        assertEquals("No active benefit found for employee.", ex.getMessage());
    }

    @AfterEach
    public void clear() {
        employee = null;
        benefit1 = null;
        benefit2 = null;
        System.out.println("Test objects cleared.");
    }
}
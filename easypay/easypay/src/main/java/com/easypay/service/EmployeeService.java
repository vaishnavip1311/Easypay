package com.easypay.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.model.Employee;
import com.easypay.model.JobTitle;
import com.easypay.model.PayrollPolicy;
import com.easypay.model.User;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.JobTitleRepository;
import com.easypay.repository.PayrollPolicyRepository;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	private UserService userService;
	private JobTitleRepository jobTitleRepository;
	private PayrollPolicyRepository payrollPolicyRepository;

	public EmployeeService(EmployeeRepository employeeRepository, UserService userService,
			JobTitleRepository jobTitleRepository, PayrollPolicyRepository payrollPolicyRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.userService = userService;
		this.jobTitleRepository = jobTitleRepository;
		this.payrollPolicyRepository = payrollPolicyRepository;
	}

	public Employee insertEmployee(Employee employee, int jobTitleId) {
		//Add user with EMPLOYEE role
		User user = employee.getUser();
		user.setRole("EMPLOYEE");
		user = userService.addUser(user);
		employee.setUser(user);
		
		//Assign Job Title
		JobTitle jobTitle = jobTitleRepository.findById(jobTitleId)
				.orElseThrow(()->new RuntimeException("JobTitle Not Found"));
		employee.setJobTitle(jobTitle);

		//Determine policy name based on job title
		  String jobTitleName = jobTitle.getTitleName().toLowerCase();
		    String policyName;

		    switch (jobTitleName) {
		        case "intern":
		            policyName = "Intern Payroll Policy";
		            break;
		        case "junior software developer":
		            policyName = "Probation Period Policy";
		            break;
		        case "software engineer":
		        	policyName = "Standard Monthly Payroll";
		            break;
		        case "senior software engineer":
		        	policyName = "Senior Executive Policy";
		            break;
		        case "remote developer":
		            policyName = "Remote Work Policy";
		            break;
		        case "team lead":
		        	policyName = "Senior Executive Policy";
		            break;
		        case "project manager":
		            policyName = "Senior Executive Policy";
		            break;
		        case "qa engineer":
		        	policyName = "Standard Monthly Payroll";
		            break;
		        case "devops engineer":
		        	policyName = "Standard Monthly Payroll";
		            break;
		        case "system administrator":
		        	policyName = "Standard Monthly Payroll";
		            break;
		        case "ux/ui designer":
		        	policyName = "Standard Monthly Payroll";
		            break;
		        case "business analyst":
		        	policyName = "Standard Monthly Payroll";
		            break;
		        case "cto(chief tech officer)":
		            policyName = "Senior Executive Policy";
		            break;
		        default:
		            policyName = "Standard Monthly Payroll";
		            break;
		    }

		    // Step 4: Fetch an active and effective payroll policy
		    List<PayrollPolicy> activePolicies = payrollPolicyRepository.findAll().stream()
		            .filter(p -> "active".equalsIgnoreCase(p.getStatus()))
		            .filter(p -> {
		                try {
		                    return LocalDate.parse(p.getEffectiveFrom()).isBefore(LocalDate.now().plusDays(1));
		                } catch (Exception e) {
		                    return false;
		                }
		            })
		            .toList();

		    PayrollPolicy selectedPolicy = activePolicies.stream()
		            .filter(p -> p.getName().equalsIgnoreCase(policyName))
		            .findFirst()
		            .orElseThrow(() ->
		                    new RuntimeException("No active payroll policy found for job title: " + jobTitleName));

		    // Step 5: Set policy and save
		    employee.setPayrollPolicy(selectedPolicy);
		    return employeeRepository.save(employee);
	}

	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(int id) {
		return employeeRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Invalid ID Given"));
	}
	
	public Employee getEmployeeByUsername(String username) {
		return employeeRepository.getEmployeeByUsername(username);
	}

	

}


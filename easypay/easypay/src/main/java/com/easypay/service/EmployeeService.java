package com.easypay.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.BankDetails;
import com.easypay.model.Employee;
import com.easypay.model.JobTitle;
import com.easypay.model.PayrollPolicy;
import com.easypay.model.User;
import com.easypay.repository.BankDetailsRepository;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.JobTitleRepository;
import com.easypay.repository.PayrollPolicyRepository;
import com.easypay.repository.UserRepository;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	private UserService userService;
	private JobTitleRepository jobTitleRepository;
	private PayrollPolicyRepository payrollPolicyRepository;
	private UserRepository userRepository;
	private BankDetailsRepository bankDetailsRepository;

	public EmployeeService(EmployeeRepository employeeRepository, UserService userService,
			JobTitleRepository jobTitleRepository, PayrollPolicyRepository payrollPolicyRepository,
			UserRepository userRepository, BankDetailsRepository bankDetailsRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.userService = userService;
		this.jobTitleRepository = jobTitleRepository;
		this.payrollPolicyRepository = payrollPolicyRepository;
		this.userRepository = userRepository;
		this.bankDetailsRepository = bankDetailsRepository;
	}

	public Employee insertEmployee(Employee employee, int jobTitleId) {
		//Add user with EMPLOYEE role
		User user = employee.getUser();
		user.setRole("EMPLOYEE");
		user = userService.addUser(user);
		employee.setUser(user);
		
		BankDetails bankDetails = employee.getBankDetails();
		bankDetailsRepository.save(bankDetails);
		
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

	public Employee updateEmploye(int employeeId, Employee updatedemployee) {
		Employee dbEmployee = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found id given is invalid."));
		
		if(updatedemployee.getName() != null)
			dbEmployee.setName(updatedemployee.getName());
		if(updatedemployee.getEmail() != null)
			dbEmployee.setEmail(updatedemployee.getEmail());
		if(updatedemployee.getGender() != null)
			dbEmployee.setGender(updatedemployee.getGender());
		if(updatedemployee.getPhone() != null)
			dbEmployee.setPhone(updatedemployee.getPhone());
		if(updatedemployee.getAddress() != null)
			dbEmployee.setAddress(updatedemployee.getAddress());
		
		User user = dbEmployee.getUser();
		user.setUsername(updatedemployee.getEmail());
		
		userRepository.save(user);
		
		return employeeRepository.save(dbEmployee);
	}

	public Employee uploadProfilePic(String username, MultipartFile file) throws IOException{
		/* Fetch Author Info by username */
        Employee employee = employeeRepository.getEmployeeByUsername(username);
        
        /* extension check: jpg,jpeg,png,gif,svg : */
        String originalFileName = file.getOriginalFilename(); // profile_pic.png
        
        String extension = originalFileName.split("\\.")[1]; // png
        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))) {
            throw new RuntimeException("File Extension " + extension + " not allowed " + "Allowed Extensions"
                    + List.of("jpg", "jpeg", "png", "gif", "svg"));
        }
        
        /* Check the file size */
        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
            throw new RuntimeException("Image Oversized. Max allowed size is " + kbs);
        }

        /* Check if Directory exists, else create one */
        String uploadFolder = "C:\\Users\\Vaishnavi patil\\Desktop\\FSD Training Project\\easypay-ui\\public\\images";
        
        Files.createDirectories(Path.of(uploadFolder));
        /* Define the full path */
        Path path = Paths.get(uploadFolder, "\\", originalFileName);
        /* Upload file in the above path */
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        /* Set url of file or image in author object */
        employee.setProfilePic(originalFileName);
        /* Save author Object */
        return employeeRepository.save(employee);
	}

	

}


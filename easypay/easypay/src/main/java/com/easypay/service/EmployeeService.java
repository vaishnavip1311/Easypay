package com.easypay.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	Logger logger = LoggerFactory.getLogger(EmployeeService.class);

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
		logger.info("Inserting employee: {}", employee.getName());
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
		logger.info("Assigned JobTitle: {}", jobTitle.getTitleName());

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
		    logger.info("Determined Payroll Policy: {}", policyName);

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
		    logger.info("Payroll policy assigned: {}", selectedPolicy.getName());
		    return employeeRepository.save(employee);
	}

	public List<Employee> getAll(int page, int size) {
		logger.info("Fetching all employees with pagination - page: {}, size: {}", page, size);
		/** Activate Pageable Interface */
        Pageable pageable = PageRequest.of(page, size);
		return employeeRepository.findAll(pageable).getContent();
	}

	public Employee getEmployeeById(int id) {
		logger.info("Fetching employee by ID: {}", id);
		return employeeRepository.findById(id)
				.orElseThrow(()-> {
					logger.error("Invalid employee ID: {}", id);
					return new RuntimeException("Invalid ID Given");
				});
	}
	
	public Employee getEmployeeByUsername(String username) {
		logger.info("Fetching employee by username: {}", username);
		return employeeRepository.getEmployeeByUsername(username);
	}

	public Employee updateEmploye(int employeeId, Employee updatedemployee) {
		logger.info("Updating employee with ID: {}", employeeId);
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
		
		logger.info("Employee updated successfully for ID: {}", employeeId);
		return employeeRepository.save(dbEmployee);
	}

	public Employee uploadProfilePic(MultipartFile file, int employeeId) throws IOException{
		logger.info("Uploading profile picture for employee ID: {}", employeeId);
		/* Fetch Author Info by username */
        Employee employee = employeeRepository.findById(employeeId)
        		.orElseThrow(()-> new ResourceNotFoundException("Id given is invalid"));
        
        /* extension check: jpg,jpeg,png,gif,svg : */
        String originalFileName = file.getOriginalFilename(); // profile_pic.png
        
        String extension = originalFileName.split("\\.")[1]; // png
        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))) {
        	logger.error("Employee not found for profile pic upload, ID: {}", employeeId);
        	throw new RuntimeException("File Extension " + extension + " not allowed " + "Allowed Extensions"
                    + List.of("jpg", "jpeg", "png", "gif", "svg"));
        }
        
        /* Check the file size */
        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
        	logger.warn("File size too large: {} KB", kbs);
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
        logger.info("Profile picture uploaded: {}", originalFileName);
        /* Save author Object */
        return employeeRepository.save(employee);
	}
	
	public Employee updateProfilePic(String username, MultipartFile file) throws IOException{
		logger.info("Updating profile picture for user: {}", username);
		/* Fetch Author Info by username */
        Employee employee = employeeRepository.getEmployeeByUsername(username);
        
        /* extension check: jpg,jpeg,png,gif,svg : */
        String originalFileName = file.getOriginalFilename(); // profile_pic.png
        
        String extension = originalFileName.split("\\.")[1]; // png
        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))) {
        	logger.warn("Invalid file extension: {}", extension);
        	throw new RuntimeException("File Extension " + extension + " not allowed " + "Allowed Extensions"
                    + List.of("jpg", "jpeg", "png", "gif", "svg"));
        }
        
        /* Check the file size */
        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
        	logger.warn("File size too large: {} KB", kbs);
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
        logger.info("Profile picture updated for user: {}", username);
        /* Save author Object */
        return employeeRepository.save(employee);
	}

	public ResponseEntity<?> updateStatus(int id , String status) {
		logger.info("Updating status of employee ID: {} to {}", id, status);
		int updatedCount = employeeRepository.updateStatus(id, status);

	    if (updatedCount > 0) {
	    	logger.info("Status updated successfully for ID: {}", id);
	        return ResponseEntity.ok("Employee status updated");
	    } else {
	    	logger.warn("Failed to update status. Employee not found with ID: {}", id);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
	    }
	}

	

}


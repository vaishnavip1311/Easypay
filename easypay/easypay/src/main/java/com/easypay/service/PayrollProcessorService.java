package com.easypay.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.PayrollProcessor;
import com.easypay.model.User;
import com.easypay.repository.PayrollProcessorRepository;
import com.easypay.repository.UserRepository;

@Service
public class PayrollProcessorService {
	
	private PayrollProcessorRepository payrollProcessorRepository;
	private UserRepository userRepository;

	public PayrollProcessorService(PayrollProcessorRepository payrollProcessorRepository,
			UserRepository userRepository) {
		super();
		this.payrollProcessorRepository = payrollProcessorRepository;
		this.userRepository = userRepository;
	}

	public PayrollProcessor updateEmploye(int processorId, PayrollProcessor updatedProcessor) {
		PayrollProcessor dbpayrollProcessor = payrollProcessorRepository.findById(processorId)
				.orElseThrow(()-> new ResourceNotFoundException("Processor not found!! Id given is invalid."));

		if(updatedProcessor.getName() != null)
			dbpayrollProcessor.setName(updatedProcessor.getName());
		if(updatedProcessor.getEmail() != null)
			dbpayrollProcessor.setEmail(updatedProcessor.getEmail());
		if(updatedProcessor.getGender() != null)
			dbpayrollProcessor.setGender(updatedProcessor.getGender());
		if(updatedProcessor.getPhoneNumber() != null)
			dbpayrollProcessor.setPhoneNumber(updatedProcessor.getPhoneNumber());
		if(updatedProcessor.getAddress() != null)
			dbpayrollProcessor.setAddress(updatedProcessor.getAddress());
		if(updatedProcessor.getBirthDate() != null)
			dbpayrollProcessor.setBirthDate(updatedProcessor.getBirthDate());

		User user = dbpayrollProcessor.getUser();
		user.setUsername(updatedProcessor.getEmail());

		userRepository.save(user);

		return payrollProcessorRepository.save(dbpayrollProcessor);
	}

	public PayrollProcessor uploadProfilePic(String username, MultipartFile file) throws IOException{
		/* Fetch Author Info by username */
		PayrollProcessor payrollProcessor = payrollProcessorRepository.findProcessorByUsername(username);
	    
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
	    payrollProcessor.setProfilePic(originalFileName);
	    /* Save author Object */
	    return payrollProcessorRepository.save(payrollProcessor);
	}

	public PayrollProcessor getProcessorByUsername(String username) {
		return payrollProcessorRepository.findProcessorByUsername(username);
	}

}



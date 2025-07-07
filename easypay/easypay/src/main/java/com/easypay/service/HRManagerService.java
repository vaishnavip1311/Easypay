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
import com.easypay.model.HRManager;
import com.easypay.model.User;
import com.easypay.repository.HRManagerRepository;
import com.easypay.repository.UserRepository;

@Service
public class HRManagerService {
	
	private HRManagerRepository hrManagerRepository;
	private UserRepository userRepository;
	
	public HRManagerService(HRManagerRepository hrManagerRepository, UserRepository userRepository) {
		super();
		this.hrManagerRepository = hrManagerRepository;
		this.userRepository = userRepository;
	}

	public HRManager uploadProfilePic(String username, MultipartFile file) throws IOException{
		/* Fetch Author Info by username */
        HRManager hrManager = hrManagerRepository.getHrManagerByUsername(username);
        
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
        hrManager.setProfilePic(originalFileName);
        /* Save author Object */
        return hrManagerRepository.save(hrManager);
	}

	public HRManager getHrManagerByUsername(String username) {
		return hrManagerRepository.getHrManagerByUsername(username);
	}

	public HRManager updateEmploye(int hrId, HRManager updatedHrManager) {
		HRManager dbHrManager = hrManagerRepository.findById(hrId)
			.orElseThrow(()-> new ResourceNotFoundException("HR Manager not found!! Id given is invalid."));
	
	if(updatedHrManager.getName() != null)
		dbHrManager.setName(updatedHrManager.getName());
	if(updatedHrManager.getEmail() != null)
		dbHrManager.setEmail(updatedHrManager.getEmail());
	if(updatedHrManager.getGender() != null)
		dbHrManager.setGender(updatedHrManager.getGender());
	if(updatedHrManager.getPhoneNumber() != null)
		dbHrManager.setPhoneNumber(updatedHrManager.getPhoneNumber());
	if(updatedHrManager.getAddress() != null)
		dbHrManager.setAddress(updatedHrManager.getAddress());
	if(updatedHrManager.getBirthDate() != null)
		dbHrManager.setBirthDate(updatedHrManager.getBirthDate());
	
	User user = dbHrManager.getUser();
	user.setUsername(updatedHrManager.getEmail());
	
	userRepository.save(user);
	
	return hrManagerRepository.save(dbHrManager);
	}
	
	

}

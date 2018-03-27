package co.simplon.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import co.simplon.service.StorageService;

@RestController
public class UploadController {
	
	@Autowired
	StorageService storageService;
	
	List<String> files = new ArrayList<String>();
	
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file")MultipartFile file, Model model) {
		try {
			storageService.store(file);
			model.addAttribute("message","You successfully uploaded"  + file.getOriginalFilename()+"!");
					files.add(file.getOriginalFilename());
		}catch(Exception e ) {
			model.addAttribute("message", "Fail to upload" + file.getOriginalFilename()+"!");
		}
		return "uploadForm";
		
	}

}

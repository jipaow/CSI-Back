package co.simplon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.model.DataArme;
import co.simplon.service.ArmeService;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/csi")
public class ArmeController {
	
	@Autowired
	ArmeService armeService;
	
	@RequestMapping(value ="/armes", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArme(){
		DataArme dataArme = null;
		try {
			dataArme= armeService.getAllArme();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dataArme);
		
	}

}

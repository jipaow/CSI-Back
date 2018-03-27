package co.simplon.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.simplon.model.Enquete;
import co.simplon.model.Suspect;
import co.simplon.service.EnqueteService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/csi")
public class EnqueteController {
	
	@Autowired
	EnqueteService enqueteService;
	
	@RequestMapping(value = "/enquetes", method = RequestMethod.GET)
	public ResponseEntity <?> getAllEnquete(){
		List <Enquete> listEnquete = null;
		try {
			listEnquete= enqueteService.getAllEnquete();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(listEnquete);
	}
	
	@RequestMapping(value ="/enquete/{id}" , method = RequestMethod.GET)
	public ResponseEntity<?> getEnquete(@PathVariable int id){
		Enquete enquete = null;
				
		try {
			enquete =enqueteService.getEnquete(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		if(enquete == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		return ResponseEntity.status(HttpStatus.OK).body(enquete);
	}
	
	@RequestMapping(value="/enquete", method = RequestMethod.POST)
	public ResponseEntity<?> insertEnquete(@RequestBody Enquete enquete){
		Enquete resultEnquete = null;
		String nom = enquete.getNom();
		if((nom == null) || (nom.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le nom n'est pas saisi !");	
		Date date = enquete.getDateCreation();
		if((date == null))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saisissez une date de creation !");
		String localisation = enquete.getLocalisation();
		if((localisation == null) || (localisation.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saisissez une localisation !");
		
		
		
		try {
			resultEnquete = enqueteService.insertEnquete(enquete);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(resultEnquete);
	}
	
	@RequestMapping(value="/archiver", method = RequestMethod.POST)
	public ResponseEntity<?> archiverEnquete(@RequestBody Enquete enquete){
		Enquete resultEnquete = null;
		
		try {
			resultEnquete = enqueteService.archiverEnquete(enquete);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(resultEnquete);
	}
	
	@RequestMapping(value ="/enquete/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEnquete(@RequestBody Enquete enquete,@PathVariable int id) throws Exception{
		
		Enquete result = null;
		
		
		try {
			result = enqueteService.updateEnquete(id,enquete);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return  ResponseEntity.status(HttpStatus.CREATED).body(result);
		
	}
	
	@RequestMapping(value="supprimerJointure/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> supprimerJointureEnquete(@PathVariable int id){
		
		try {
			enqueteService.supprimerJointureEnquete(id);
		 } catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@RequestMapping(value="supprimer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> supprimerEnquete(@PathVariable int id){
		
		try {
			System.out.println("test");
			enqueteService.supprimerEnquete(id);
			System.out.println("test ok");
			
		 } catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@RequestMapping(value = "/suspect/link", method = RequestMethod.POST)
    public ResponseEntity<?> addSuspectToEnquete(@RequestBody Enquete enquete){
        Enquete resultEnquete = null;
       
       try {
            resultEnquete = enqueteService.addSuspectToEnquete(enquete);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultEnquete);
        
    }
	
}

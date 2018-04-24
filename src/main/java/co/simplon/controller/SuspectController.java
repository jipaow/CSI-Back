package co.simplon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;
import co.simplon.service.SuspectService;
/**
 * 
 * @author jean philippe
 * classe controller de l'api rest qui 
 * permet de faire le lien avec la partie front,
 * Chaque methode lie une URI aux methodes du service SuspectService.
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/csi")
public class SuspectController {
	
	 @Autowired
	 private SuspectService suspectService;
	 
	    //chemin pour consulter tous les suspects
		@RequestMapping(value = "/suspects", method = RequestMethod.GET)
		public ResponseEntity <?> getAllSuspect(){
			DataSuspect dataSuspect = null;
			try {
				dataSuspect= suspectService.getAllSuspect();
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(dataSuspect);
		}
		
		//chemin pour consulter un suspect par son id
		@RequestMapping(value = "/suspect/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getSuspect(@PathVariable int id){
			//Suspect suspect = null;
			DataSuspect dataSuspect = null;
					
			try {
				dataSuspect =suspectService.getSuspect(id);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
			if(dataSuspect == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
			return ResponseEntity.status(HttpStatus.OK).body(dataSuspect);
		}
		
	
		// chemin pour créer un suspect
		@RequestMapping(value ="/suspect", method = RequestMethod.POST)
		public ResponseEntity<?> insertSuspect(@RequestBody Suspect suspect){
			Suspect resultSuspect = null;
			int resultVerif = 0;
			
			try {
				resultVerif = suspectService.verifSuspectExiste(suspect);
				if (resultVerif != 0){
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Le suspect existe déjà");
				} else {
			try {
				resultSuspect = suspectService.insertSuspect(suspect);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).body(resultSuspect);
			
		}
				} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
			}
		
		
		//chemin pour mettre à jour les infos d'un suspect par son id
		@RequestMapping(value ="/suspect/{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateSuspect(@RequestBody Suspect suspect,@PathVariable int id) throws Exception{
			
			Suspect result = null;
			
			String nom = suspect.getNom();
		    if((nom == null) || (nom.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le nom n'est pas saisi !");
			
			String prenom = suspect.getPrenom();
			if((prenom == null) || (prenom.isEmpty()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le prenom n'est pas saisi !");
			
			String genre =suspect.getGenre();
			if((genre == null) || (genre.isEmpty()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("saisissez le sexe du suspect !");
			
			String adresseConnue = suspect.getAdresseConnues();
			if((adresseConnue == null) || (adresseConnue.isEmpty()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("saisissez votre chance de rentrer une adresse !");
			
			try {
				result = suspectService.updateSuspect(id,suspect);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			
			return  ResponseEntity.status(HttpStatus.CREATED).body(result);
			
		}

		@RequestMapping(value="/archiverSuspect/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> archiverSuspect(@PathVariable int id ) {
			try {
				suspectService.archiverSuspect(id);
				suspectService.supprimerJointureSuspect(id);
				suspectService.supprimerSuspect(id);
			}catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		
        }
}

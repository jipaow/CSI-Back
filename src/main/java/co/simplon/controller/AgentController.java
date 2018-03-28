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

import co.simplon.model.Agent;
import co.simplon.model.DataAgent;
import co.simplon.service.AgentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/csi")
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	@RequestMapping(value = "/agents", method = RequestMethod.GET)
	public ResponseEntity <?> getAllAgent(){
		DataAgent dataAgent = null;
		try {
			dataAgent = agentService.getAllAgent();
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(dataAgent);	
	}
	
	@RequestMapping(value= "/agent/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAgent(@PathVariable int id){
		DataAgent dataAgent= null;
		try {
			dataAgent = agentService.getAgent(id);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(dataAgent);
		
	}
	
	@RequestMapping(value ="/agent", method = RequestMethod.POST)
	public ResponseEntity<?> insertAgent(@RequestBody Agent agent){
	    Agent resultAgent = null;
	    int resultVerif = 0;
	    try {
	    	resultVerif = agentService.verifAgentExiste(agent);
	    	if (resultVerif !=0) {
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("l'agent existe déjà");
	    	}
	    	else {
	    		try {
	    			resultAgent = agentService.insertAgent(agent);
	    		}catch (Exception e) {
	    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  
		        }
		    }
	    	return ResponseEntity.status(HttpStatus.OK).body(resultAgent);
	    	
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	  
	@RequestMapping(value="/agent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> upDateAgent(@RequestBody Agent agent, @PathVariable int id){
		Agent result = null;
		
		try {
			result = agentService.upDateAgent(id, agent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	    	
	@RequestMapping(value = "/agent/link", method = RequestMethod.POST)
  public ResponseEntity<?> addAgentToEnquete(@RequestBody Agent agent){
      Agent resultAgent = null;
     
     try {
          resultAgent = agentService.addAgentToEnquete(agent);
          
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(resultAgent);
      
  }		
	

}

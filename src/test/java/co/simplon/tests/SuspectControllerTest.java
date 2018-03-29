package co.simplon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import co.simplon.controller.SuspectController;
import co.simplon.dao.SuspectDAO;
import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;
import co.simplon.service.SuspectService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SuspectController.class, secure= false)
public class SuspectControllerTest {
	

	
	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private SuspectService suspectService;
	private SuspectDAO suspectDao;
	
	
	
	
	
	//test d'un get dans le cas où il n'existe aucun suspect
     @Test
     public void getAllSuspectTest() throws Exception {
    	 
    	 DataSuspect mockDataSuspect = new DataSuspect("suspect",Arrays.asList());
    	 Mockito.when(suspectService.getAllSuspect()).thenReturn(mockDataSuspect);
    	 
    	 RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/csi/suspects").accept(MediaType.APPLICATION_JSON);
    	 
    	 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	 
    	 System.out.println(result.getResponse());
    	 String expected = "{type:suspect, data:[]}";
    	  
    	 JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
     }
     
     //Test d'un get de suspect par Id
     @Test
     public void getSuspectbyIdTest() throws Exception {
    	 Suspect suspect = new Suspect();
    	 suspect.setId(1);
    	 suspect.setNom("testNom");
    	 suspect.setPrenom("testPrenom");
    	 suspect.setGenre("M");
  
    	 DataSuspect mockDataSuspect = new DataSuspect("suspect",Arrays.asList(suspect));
    	 Mockito.when(suspectService.getSuspect(1)).thenReturn(mockDataSuspect);
    	 
    	 RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/csi/suspect/1").accept(MediaType.APPLICATION_JSON);
    	 
    	 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	 
    	 System.out.println(result.getResponse());
    	 String expected = "{type:suspect, data:[{id:1, nom:testNom, prenom:testPrenom, genre:M}]}";
    	 
    	 JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    	 
     }
     
     
     
}
     

			        

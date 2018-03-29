package co.simplon.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import co.simplon.CsibackApplication;
import co.simplon.dao.SuspectDAO;
import co.simplon.model.Suspect;
import co.simplon.service.SuspectService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsibackApplication.class)
public class TestDAO {
	
	static Suspect suspect;
	static Suspect newSuspect;
	static SuspectService suspectService;
	
	@Autowired
	SuspectDAO suspectDAO;
	
	@BeforeClass
	public static void initSuspect() throws Exception{
		suspectService = new SuspectService();
		suspect = new Suspect();
	}
	
	
	@Rollback(true)
    @Test
	public void testUpdateSuspect() {

		newSuspect = null;
		suspect = createMock("Saisrien", "Jean");
		
		try {
			newSuspect = suspectDAO.updateSuspect(suspect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(newSuspect != null);
		assertEquals("Saisrien", newSuspect.getNom());
	}
	
	@Test
	public void testInsertSuspect() {
		try {
			suspect = createMock("Anne", "Honime");
			newSuspect = suspectDAO.insertSuspect(suspect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(newSuspect != null);
	}
	
	private Suspect createMock(String nom, String prenom) {
		Suspect mock = new Suspect();
		mock.setNom(nom);
		mock.setPrenom(prenom);
     	mock.setId(new Integer(1));

		return mock;
	}
	
	

	
	
	

}

package co.simplon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import co.simplon.model.Arme;
import co.simplon.model.DataArme;
/**
 * Fonctionnalité à implementer pour une V2
 * */

@Repository
public class jdbcArmeDAO implements ArmeDAO {

	private DataSource datasource;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public jdbcArmeDAO(JdbcTemplate jdbcTemplate) {
		this.datasource = jdbcTemplate.getDataSource();
		
	}
	
	@Override
	public DataArme listArme() throws Exception {
		DataArme dataArme =  new DataArme();
		Arme arme;
		String sql;
		PreparedStatement prep = null;
		ResultSet rs;
		
		try {
			sql = "SELECT DISTINCT * FROM arme";
			prep = datasource.getConnection().prepareStatement(sql);
			
			rs = prep.executeQuery();
			
			logSQL(prep);
			
			while (rs.next()) {
				arme = getArmeFromResultSet(rs);
				dataArme.getData().add(arme);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("SQL ERROR requete dataArme:" + prep.toString(),e);
			throw e;
		}finally {
			prep.close();
		}
		
		return dataArme;
	}

	@Override
	public DataArme getArme(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Arme insertArme(Arme arme) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Arme upDateArme(Arme arme) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Arme addArmeToEnquete(Arme arme) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int verifArmeExiste(Arme arme) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private Arme getArmeFromResultSet(ResultSet rs) throws SQLException {
		Arme arme = new Arme();
		arme.setNumSerie(rs.getInt("numero_serie"));
		arme.setMarque(rs.getString("marque"));
		arme.setModele(rs.getString("modele"));
		arme.setTypeArme(rs.getString("type_arme"));
		arme.setTypeBalistique(rs.getString("type_balistique"));
	
		return arme;
	}
	
	
	
	private void logSQL(PreparedStatement pstmt) {
		String sql;
			
			if (pstmt == null)
				return;
		
			sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
			log.debug(sql);
			
		}
	

}

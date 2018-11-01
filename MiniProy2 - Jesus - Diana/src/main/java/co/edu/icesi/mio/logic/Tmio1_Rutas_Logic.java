package co.edu.icesi.mio.logic;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.mio.dao.*;
import co.edu.icesi.mio.dao.*;
import co.edu.icesi.mio.model.Tmio1Conductore;
import co.edu.icesi.mio.model.Tmio1Ruta;

@Service
public class Tmio1_Rutas_Logic implements IRutasLogic {
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
    private ITmio1_Rutas_DAO rutasDAO;
	
	
//	 el n�mero de ruta tenga tres caracteres; el d�a inicio y fin sean num�ricos
//	 entre 1 y 7 y el inicio sea menor o igual al fin; la hora inicio y fin sean num�ricos entre 1 y
//	 1440 y el inicio sea menor o igual al fin; activa sea S o N.

	public boolean validarRuta(Tmio1Ruta ruta) {
		
		try {
			boolean ret = false;
			/*
			 * validacion completa	
			 */
			if(ruta.getDiaFin()!=null && ruta.getDiaInicio()!=null&&
					ruta.getHoraInicio()!= null && ruta.getHoraFin()!=null) {
				ruta.getDiaFin().intValueExact();
				ruta.getDiaInicio().intValueExact();
				ruta.getHoraFin().intValueExact();
				ruta.getHoraInicio().intValueExact();
			}
			
			
			// validar numero ruta
			if(ruta.getNumero()!=null && !ruta.getNumero().equals("") && ruta.getNumero().length()==3) {
				// validar dia inicio y fin
				if(ruta.getDiaFin()!=null && ruta.getDiaInicio()!=null&& ruta.getDiaFin().compareTo(new BigDecimal(1))>=0 &&
						ruta.getDiaFin().compareTo(new BigDecimal(7))<=0 && ruta.getDiaInicio().compareTo(new BigDecimal(1))>=0 &&
						ruta.getDiaInicio().compareTo(new BigDecimal(7))<=0 && ruta.getDiaInicio().compareTo(ruta.getDiaFin())<=0) {
					//validar hora inicio y fin
					if(ruta.getHoraInicio()!= null && ruta.getHoraFin()!=null&& ruta.getHoraInicio().compareTo(new BigDecimal(1))>=0
							&&ruta.getHoraInicio().compareTo(new BigDecimal(1440))<=0&& ruta.getHoraFin().compareTo(new BigDecimal(1))>=0
							&&ruta.getHoraFin().compareTo(new BigDecimal(1440))<=0 && 
							ruta.getHoraInicio().compareTo(ruta.getHoraFin())<=0) {
						//validar activa
						if(ruta.getActiva()!=null&& (ruta.getActiva().equals("S")||ruta.getActiva().equals("N"))) {
							ret=true;
						}
					}
				}
			}
			return ret;
		} catch (NumberFormatException e) {
			return false;
		}
		
	}
	

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean crearRuta(Tmio1Ruta ruta) {
		
		// falta hacer todas las validaciones
		
		if(validarRuta(ruta)) {

			
			rutasDAO.save(em, ruta);
			
			return true;
		}else {
			return false;	
		}
		
	}
	

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean actualizarRuta(Tmio1Ruta ruta) {
		
		// falta hacer todas las validaciones
		if(validarRuta(ruta)) {

			
			rutasDAO.update(em, ruta);
		
			
			return true;
		}else {
			return false;	
		}
		
	}
	

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean borrarRuta(Tmio1Ruta ruta) {
		
		// falta hacer todas las validaciones
		if(validarRuta(ruta)) {

		
			rutasDAO.delete(em, ruta);
			
			
			return true;
		}else {
			return false;	
		}
		
	}
	

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Tmio1Ruta> buscarRutaRango(BigDecimal DiaInicio, BigDecimal DiaFin) {
		
		
		// falta hacer todas las validaciones
		if(DiaFin!=null && DiaInicio!=null&& DiaFin.compareTo(new BigDecimal(1))>=0 &&
				DiaFin.compareTo(new BigDecimal(7))<=0 && DiaInicio.compareTo(new BigDecimal(1))>=0 &&
				DiaInicio.compareTo(new BigDecimal(7))<=0 && DiaInicio.compareTo(DiaFin)<=0) {

			
			List<Tmio1Ruta> act=rutasDAO.findByRangeOfDays(em, DiaInicio, DiaFin);
		
			
			return act;
		}else {
			return null;	
		}
		
	}


	@Override
	public Tmio1Ruta buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	



}

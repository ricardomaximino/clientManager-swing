package es.seas.feedback.cliente.manager.model.dao.hibernate.config;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Esta clase Converte LocalDate para java.sqlDate y java.sql.Date para 
 * LocalDate.
 * @author Ricardo Maximino
 * Esta clase implementa javax.persistence.AttributeConverter para que hibernate
 * no cree columnas del tipo BLOB para las variables de tipo LocalDate y 
 * para que hibernate también sepa convertir de java.sql.Date para LocalDate.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date >{
    
    /**
     * Este metodo convierte de LocalDate para java.sql.Date.
     * @param localDate del tipo java.time.LocalDate.
     * @return del tipo java.sql.Date.
     */
    @Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		return (localDate == null? null : Date.valueOf(localDate));
	}

        /**
         * Este metodo convierte de java.sql.Date para LocalDate.
         * @param dbData del tipo java.sql.Date.
         * @return del tipo java.time.LocalDate.
         */
	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		return (dbData == null? null : dbData.toLocalDate());
	}
}

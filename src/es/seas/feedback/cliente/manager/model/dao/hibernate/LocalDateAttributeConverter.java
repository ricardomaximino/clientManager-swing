/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.dao.hibernate;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Ricardo
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date >{
    @Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		return (localDate == null? null : Date.valueOf(localDate));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		return (dbData == null? null : dbData.toLocalDate());
	}
}

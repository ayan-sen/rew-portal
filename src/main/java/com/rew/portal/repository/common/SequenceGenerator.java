package com.rew.portal.repository.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.rew.portal.model.common.PkGenerationSignature;

public class SequenceGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session,
			Object obj) throws HibernateException {
		
		PkGenerationSignature sig = (PkGenerationSignature) obj;
		
        Connection connection = session.connection();

        try {
            Statement statement=connection.createStatement();

            String sql = StringUtils.join("select count(",sig.getIdColName(),") as Id from ",sig.getTableName());
            
            ResultSet rs=statement.executeQuery(sql);

            if(rs.next())
            {
                int id=rs.getInt(1)+101;
                String generatedId = "";
                if(sig.enableSuffix()) {
                	generatedId = StringUtils.join(sig.getPrefix(),new Integer(id).toString(),"/",this.getCurrentFinancialYear());
                } else {
                	generatedId = StringUtils.join(sig.getPrefix(),new Integer(id).toString());
                }
                System.out.println("Generated Id: " + generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
	
	private String getCurrentFinancialYear() {
		
		LocalDate date = LocalDate.now();
		if(date.getYear() == 2020) {
			if(date.getMonthValue() > 7) {
				return StringUtils.join(date.getYear(),"-",(date.getYear() + 1) % 100);
			} else {
				return StringUtils.join((date.getYear() - 1),"-",(date.getYear()% 100));
			}
		} else {
			if(date.getMonthValue() > 3) {
				return StringUtils.join(date.getYear(),"-",(date.getYear() + 1)% 100);
			} else {
				return StringUtils.join((date.getYear() - 1),"-",(date.getYear()% 100));
			}
		}
		
	}
}

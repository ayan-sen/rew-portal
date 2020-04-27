package com.rew.portal.repository.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                String generatedId = sig.getPrefix() + new Integer(id).toString();
                System.out.println("Generated Id: " + generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

package vn.myclass.core.common.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import vn.myclass.core.common.constant.CoreConstant;

import java.util.Map;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Object[] buildNameQuery(Map<String, Object> property, String whereClause, String sortExpression, String sortDirection) {
        StringBuilder nameQuery = new StringBuilder();
        if (property != null && property.size() > 0) {
            String[] params = new String[property.size()];
            Object[] values = new Object[property.size()];
            if (property != null && property.size() > 0) {
                int i = 0 ;
                for(Map.Entry item: property.entrySet()) {
                    params[i] = (String) item.getKey();
                    values[i] = item.getValue();
                    i++;
                }
            }
            for (int i1 = 0; i1 < params.length ; i1++) {
                nameQuery.append(" and ").append("LOWER("+params[i1]+") LIKE '%' || :"+params[i1]+" || '%' ");
            }
            if (whereClause != null) {
                nameQuery.append(whereClause);
            }
            if (sortExpression != null && sortDirection != null) {
                nameQuery.append(" order by ").append(sortExpression);
                nameQuery.append(" " +(sortDirection.equals(CoreConstant.SORT_ASC)?"asc":"desc"));
            }
            return new Object[]{nameQuery, params, values};
        }
        return new Object[]{nameQuery.toString()};
    }
}

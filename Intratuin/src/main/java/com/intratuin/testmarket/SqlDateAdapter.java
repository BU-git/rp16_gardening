package com.intratuin.testmarket;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SqlDateAdapter extends XmlAdapter<java.util.Date, java.sql.Date> {
    public java.util.Date marshal(java.sql.Date sqlDate) throws Exception {
        if (sqlDate == null) {
            return null;
        }
        return new java.util.Date(sqlDate.getTime());
    }

    public java.sql.Date unmarshal(java.util.Date utilDate) throws Exception {
        if (utilDate == null) {
            return null;
        }
        return new java.sql.Date(utilDate.getTime());
    }
}

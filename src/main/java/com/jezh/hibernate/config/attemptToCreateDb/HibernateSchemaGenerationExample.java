package com.jezh.hibernate.config.attemptToCreateDb;

//import com.sun.org.apache.bcel.internal.generic.RET;
import org.hibernate.MappingException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.io.IOException;
import java.util.EnumSet;

//http://www.javarticles.com/2015/06/generating-database-schema-using-hibernate.html
public class HibernateSchemaGenerationExample {

    public static StandardServiceRegistryImpl buildCfg() {
        return (StandardServiceRegistryImpl)new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    }

    public static void main(String[] args) throws MappingException, IOException {
        ServiceRegistry serviceRegistry = buildCfg();
        MetadataImplementor metadata = (MetadataImplementor) new MetadataSources(serviceRegistry).buildMetadata();
        SchemaExport schemaExport = new SchemaExport(/*metadata*/);
        schemaExport.setOutputFile("hbm2schema.sql");
        schemaExport.create(EnumSet.allOf(TargetType.class), metadata);
        ((StandardServiceRegistryImpl)serviceRegistry).destroy();
    }
}

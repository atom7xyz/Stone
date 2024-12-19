package xyz.sorridi.stone.common.data.base.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import xyz.sorridi.stone.common.data.base.DataOrigin;

import java.util.Optional;

public class HibernateConfig extends DataOrigin
{
    private static SessionFactory sessionFactory;

    private String hibernateHbm2ddlAuto, hibernateShowSql;

    public HibernateConfig setHibernateHbm2ddlAuto(String hibernateHbm2ddlAuto)
    {
        this.hibernateHbm2ddlAuto = hibernateHbm2ddlAuto;
        return this;
    }

    public HibernateConfig setShowSql(String showSql)
    {
        this.hibernateShowSql = showSql;
        return this;
    }

    @Override
    public void setup()
    {
        super.setup();

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        configuration.setProperty("hibernate.show_sql", hibernateShowSql);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.datasource", dataSource)
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static Optional<SessionFactory> getSessionFactory()
    {
        return Optional.of(sessionFactory);
    }

    @Override
    public void shutdown()
    {
        super.shutdown();
        getSessionFactory().ifPresent(SessionFactory::close);
    }

}
package xyz.sorridi.stone.common.data.base.hibernate;


import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@AllArgsConstructor
public class SessionProvider
{
    private final SessionFactory factory;

    public Session getSession()
    {
        return factory.openSession();
    }
}

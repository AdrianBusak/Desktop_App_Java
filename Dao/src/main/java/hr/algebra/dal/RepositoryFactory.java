package hr.algebra.dal;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RepositoryFactory {

    private static final String PATH = "/config/repository.properties";
    private static final Map<Class<?>, Object> repositories = new HashMap<>();

    static {
        Properties properties = new Properties();

        try (InputStream is = RepositoryFactory.class.getResourceAsStream(PATH)) {
            properties.load(is);

            for (String interfaceName : properties.stringPropertyNames()) {
                String implName = properties.getProperty(interfaceName);

                Class<?> interfaceClass = Class.forName(interfaceName);
                Class<?> implClass = Class.forName(implName);

                Object instance = implClass.getDeclaredConstructor().newInstance();
                repositories.put(interfaceClass, instance);
            }

        } catch (Exception ex) {
            Logger.getLogger(RepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private RepositoryFactory() {
    }

    public static <T> T getRepository(Class<T> clazz) {
        return (T) repositories.get(clazz);
    }
}

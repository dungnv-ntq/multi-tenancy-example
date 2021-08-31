package dung.example.multitenancy.tenants;

import dung.example.multitenancy.entities.DataSourceConfig;
import dung.example.multitenancy.repositories.DataSourceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TenantDataSource {
    @Autowired
    private DataSourceConfigRepository repository;

    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<DataSourceConfig> configList = repository.findAll();

        return configList.stream()
                .collect(Collectors.toMap(DataSourceConfig::getName, s -> getDataSource(s.getName())));
    }

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }

        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }

        return dataSource;
    }

    public DataSource createDataSource(String name) {
        DataSourceConfig config = repository.findByName(name);
        if (config != null) {
             return DataSourceBuilder.create()
                    .driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl())
                     .build();
        }

        return null;
    }
}

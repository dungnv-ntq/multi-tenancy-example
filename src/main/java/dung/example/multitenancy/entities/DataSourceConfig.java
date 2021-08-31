package dung.example.multitenancy.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "datasourceconfig")
@Data
public class DataSourceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "driver_class_name")
    private String driverClassName;
    private String url;
    private String name;
    private String username;
    private String password;
    private boolean initialize;
}

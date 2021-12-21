package azoth.gcp.api.clients.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    private long id;
    private String name;
    private String lastName;
    private int age;
}

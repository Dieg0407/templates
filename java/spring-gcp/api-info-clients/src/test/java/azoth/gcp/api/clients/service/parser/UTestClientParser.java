package azoth.gcp.api.clients.service.parser;

import azoth.gcp.api.clients.model.ClientEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class UTestClientParser {

    @Test
    public void testParseEntityToDTO() {
        var clientParser = new ClientParser();
        var entity = new ClientEntity(1, "Diego", "Pastor", 23);
        var dto = clientParser.parseEntity(entity);

        assertThat(entity.getId()).isEqualTo(dto.id());
        assertThat(entity.getName()).isEqualTo(dto.name());
        assertThat(entity.getLastName()).isEqualTo(dto.lastName());
        assertThat(entity.getAge()).isEqualTo(dto.age());

        var entity2 = new ClientEntity(2, "Ruben", "Guerrero", 21);
        var dto2 = clientParser.parseEntity(entity2);

        assertThat(entity2.getId()).isEqualTo(dto2.id());
        assertThat(entity2.getName()).isEqualTo(dto2.name());
        assertThat(entity2.getLastName()).isEqualTo(dto2.lastName());
        assertThat(entity2.getAge()).isEqualTo(dto2.age());
    }
}

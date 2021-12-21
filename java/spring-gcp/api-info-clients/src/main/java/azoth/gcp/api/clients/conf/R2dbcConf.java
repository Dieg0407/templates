package azoth.gcp.api.clients.conf;

import azoth.gcp.api.clients.repo.ClientRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackageClasses = { ClientRepository.class })
public class R2dbcConf {
}

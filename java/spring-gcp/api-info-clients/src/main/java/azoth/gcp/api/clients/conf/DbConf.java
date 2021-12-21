package azoth.gcp.api.clients.conf;

import azoth.gcp.api.clients.repo.ClientRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = { ClientRepository.class })
public class DbConf {
}

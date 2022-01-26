package azth.saml2.login.backend.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/api/info", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfoController {

    @GetMapping("")
    public Info getInfo(
            @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal
    ) {
        return new Info(
                OffsetDateTime.now(),
                principal.getName(),
                principal.getRelyingPartyRegistrationId()
        );
    }

    public record Info(OffsetDateTime serverTime, String user, String relyingPartyId) {
    }
}

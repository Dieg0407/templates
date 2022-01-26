package azth.saml2.login.backend.controller.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class IndexController {
    /*
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        String emailAddress = principal.getFirstAttribute("emailAddress");
        model.addAttribute("emailAddress", emailAddress);
        model.addAttribute("userAttributes", principal.getAttributes());
        return "index";
    }*/

    @RequestMapping("/")
    public ModelAndView index(Model model, HttpSession session, @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        return new ModelAndView("redirect:"+"http://localhost:3000");
    }

    @GetMapping("/slo")
    public String sloForm(Model model, @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        return "slo-form";
    }
}

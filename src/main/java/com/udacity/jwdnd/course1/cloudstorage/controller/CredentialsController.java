package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialsController {

    private CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping("credential")
    public String addEditCredential(Credentials credentialsForm, Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        credentialsForm.setUserid(user.getUserId());


        if (credentialsForm.getCredentialid() > 0){

            credentialsService.editCredential(credentialsForm);
            System.out.println("credential updated");
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }
        else {
            Credentials c = new Credentials(credentialsForm.getUrl(),credentialsForm.getUsername(),
                    credentialsForm.getKey(),credentialsForm.getPassword(),user.getUserId());
            credentialsService.addCredential(c, user.getUserId());
            System.out.println("credential added");
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }
    }

    @GetMapping("/credentials/delete")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialid, Model model){
        if (credentialid > 0){
            credentialsService.deletecredential(credentialid);
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }
        model.addAttribute("error", true);
        model.addAttribute("error", "There is error deleting the credential, try again!");
        return "redirect:/result?error";

    }



}

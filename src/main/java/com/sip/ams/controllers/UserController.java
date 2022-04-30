package com.sip.ams.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sip.ams.entities.User;
import com.sip.ams.repositories.UserRrepository;
import com.sip.ams.services.SecurityService;
import com.sip.ams.services.SecurityServiceImpl;
@Controller
public class UserController {
	@Autowired
	private UserRrepository userrepo;

	@Autowired
    private SecurityService securityService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userForm.setRole(userForm.getRole());
        userrepo.save(userForm);

        securityService.autoLogin(userForm.getLogin(), userForm.getPasswordConfirm());

        return "redirect:/provider/list";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
    	String username = SecurityServiceImpl.getloggeduser();
    	User user = userrepo.findByUsername(username);
    	if(user.getRole().toString() == "Admin")
    		return "redirect:/provider/list" ;
    	else
    		return "redirect:/article/list";
    }
    
	@GetMapping("/find/{id}")
    public User getUserByUserName(@PathVariable("id") String id) {
		User user = userrepo.findByUsername(id);
       
    		   return  user;
    }
    
	
	@GetMapping("/list")
    public List<User> getAllUsers() {
        return (List<User>) userrepo.findAll();
    }
	@PostMapping("/add")
	public User addNewUser(@RequestBody User user) {
		return userrepo.save(user);
	}
	
	@PutMapping("/edit/{id}")
	 public ResponseEntity<User> updateUser(@PathVariable("id") String id,@Valid @RequestBody User user )
	{  
		
		User s = userrepo.findByUsername(id);
		
		
		if(s != null) {
			if(user.getEmail()!=null)
			s.setEmail(user.getEmail());
			if(user.getId()>0)
			s.setId(user.getId());
			if(user.getLogin()!=null)
			s.setLogin(user.getLogin());
			if(user.getNom()!=null)
			s.setNom(user.getNom());
			if(user.getPassword()!=null)
			s.setPassword(user.getPassword());
			if(user.getPrenom()!=null)
			s.setPrenom(user.getPrenom());
			if(user.getRole()!=null)
			s.setRole(user.getRole());
		
		
	
		}
		userrepo.save(s);
        return new ResponseEntity<User>(s, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
    public List<User> deleteScrapedUser(@PathVariable("id") Long id){
		Optional<User> user = userrepo.findById(id);
		User u = user.get();
		userrepo.delete(u);
		 return (List<User>) userrepo.findAll();
    }

	

}

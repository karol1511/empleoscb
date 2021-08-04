package com.carogomez.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.carogomez.model.Perfil;
import com.carogomez.model.Usuario;
import com.carogomez.model.Vacante;
import com.carogomez.service.IntUsuariosService;
import com.carogomez.service.IntVacantesServices;

@Controller
public class HomeController {
	
	@Autowired
	private IntUsuariosService usuariosService;
	
	@Autowired
	private IntVacantesServices vacantesService;
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication autn) {
		String username = autn.getName();
		System.out.println("Nombre usuario: "  + username);
		for(GrantedAuthority rol: autn.getAuthorities()) {
			System.out.println("Rol  : "  + rol.getAuthority());	
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/guardar")
	public String guardarUsuario(Usuario usuario) {
		usuario.setPassword("{noop}"+ usuario.getPassword());
		usuario.setEstatus(1);
		usuario.setFechaRegistro(LocalDate.now());
		/*crear perfil o rol, todos los que se registren tendran el rol de "Usuario" */
		Perfil per = new Perfil();
		per.setId(3); //rol usuario
		usuario.agregar(per);
		
		usuariosService.guardar(usuario);
		return "formLogin";	
		
	}
	
	@GetMapping("/registro")
	public String registroUsuario(Usuario usuario) {
		return "formRegistro";
		
	}
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		List<Vacante> vacantes = vacantesService.obtenerDestacadas();
		model.addAttribute("vacantes", vacantes);
		return "home";

	}
}

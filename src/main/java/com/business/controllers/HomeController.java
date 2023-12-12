package com.business.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.business.entities.Product;
import com.business.loginCredentials.AdminLogin;
import com.business.services.ProductServices;

@Controller
public class HomeController {

	@Autowired
	private ProductServices productServices;

	// Mapeamento para a página inicial
	@GetMapping("/home")
	public String home() {
		return "Home";
	}

	// Mapeamento para a página de produtos
	@GetMapping("/products")
	public String products(Model model) {
		// Obtendo todos os produtos e adicionando ao modelo para visualização na página
		List<Product> allProducts = this.productServices.getAllProducts();
		model.addAttribute("products", allProducts);
		return "Products";
	}

	// Mapeamento para a página de localização
	@GetMapping("/location")
	public String location() {
		return "Locate_us";
	}

	// Mapeamento para a página "Sobre nós"
	@GetMapping("/about")
	public String about() {
		return "About";
	}

	// Mapeamento para a página de login
	@GetMapping("/login")
	public String login(Model model) {
		// Adicionando um objeto de AdminLogin ao modelo para o formulário de login na página
		model.addAttribute("adminLogin", new AdminLogin());
		return "Login";
	}
}

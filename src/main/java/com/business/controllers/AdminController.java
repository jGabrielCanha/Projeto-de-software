package com.business.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.business.entities.Admin;
import com.business.entities.Orders;
import com.business.entities.Product;
import com.business.entities.User;
import com.business.loginCredentials.AdminLogin;
import com.business.loginCredentials.UserLogin;
import com.business.services.AdminServices;
import com.business.services.OrderServices;
import com.business.services.ProductServices;
import com.business.services.UserServices;

import jakarta.validation.Valid;

@Controller
public class AdminController {
	@Autowired
	private UserServices services;
	@Autowired
	private AdminServices adminServices;
	@Autowired
	private ProductServices productServices;	
	@Autowired
	private OrderServices orderServices;

	private String email;
	private User user;

	// Validando login
	@GetMapping("/adminLogin")
	public String  getAllData(  @ModelAttribute("adminLogin") AdminLogin login, Model model)
	{
		String email=login.getEmail();
		String password=login.getPassword();
		if(adminServices.validateAdminCredentials(email, password))
		{
			return "redirect:/admin/services";
		}
		else {
			model.addAttribute("error", "Invalid email or password");
			return "Login";
		}

	}

	@GetMapping("/userlogin")
	public String userLogin( @ModelAttribute("userLogin") UserLogin login,Model model)
	{

		email=login.getUserEmail();
		String password=login.getUserPassword();
		if(services.validateLoginCredentials(email, password))
		{
			user = this.services.getUserByEmail(email);
			List<Orders> orders = this.orderServices.getOrdersForUser(user);
			model.addAttribute("orders", orders);
			model.addAttribute("name", user.getUname());
			return "BuyProduct";
		}
		else
		{
			model.addAttribute("error2", "Invalid email or password");
			return "Login";
		}

	}
	//Pesquisando produto por nome
	@PostMapping("/product/search")
	public String seachHandler(@RequestParam("productName") String name,Model model)
	{

		Product product=this.productServices.getProductByName(name);
		if(product==null)
		{
			model.addAttribute("message", "SORRY...!  Product Unavailable");
			model.addAttribute("product", product);
			List<Orders> orders = this.orderServices.getOrdersForUser(user);
			model.addAttribute("orders", orders);
			return "BuyProduct";
		}
		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("product", product);
		return "BuyProduct";

	}

	//Prestação de serviços
	@GetMapping("/admin/services")
	public String returnBack(Model model)
	{
		List<User> users= this.services.getAllUser();
		List<Admin>admins=this.adminServices.getAll(); 
		List<Product>products=this.productServices.getAllProducts();
		List<Orders> orders = this.orderServices.getOrders();
		model.addAttribute("users",users);
		model.addAttribute("admins", admins);
		model.addAttribute("products", products);
		model.addAttribute("orders", orders);

		return "Admin_Page";
	}

	//Invocando a página addAdmin
	@GetMapping("/addAdmin")
	public String addAdminPage()
	{
		return "Add_Admin";
	}

	//Lidando com AddAdmin
	@PostMapping("addingAdmin")
	public String addAdmin( @ModelAttribute Admin admin)
	{

		this.adminServices.addAdmin(admin);
		return "redirect:/admin/services";

	}

	//invocando a página updateAdmin
	@GetMapping("/updateAdmin/{adminId}")
	public String update(@PathVariable("adminId") int id,Model model)
	{
		Admin admin = this.adminServices.getAdmin(id);
		model.addAttribute("admin", admin);
		return "Update_Admin";
	}

	//Tratamento da página de atualização
	@GetMapping("/updatingAdmin/{id}")
	public String updateAdmin(@ModelAttribute Admin admin,@PathVariable("id") int id)
	{
		this.adminServices.update(admin, id);
		return "redirect:/admin/services";
	}

	//Lidando com operação de exclusão
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable("id") int id)
	{
		this.adminServices.delete(id);
		return "redirect:/admin/services";
	}

	//Invocando a página AddProduct
	@GetMapping("/addProduct")
	public String addProduct()
	{
		return "Add_Product";
	}

	//Invocando a página de atualização do produto
	@GetMapping("/updateProduct/{productId}")
	public String updateProduct(@PathVariable("productId") int id,Model model)
	{
		Product product=this.productServices.getProduct(id);
		System.out.println(product);
		model.addAttribute("product", product);
		return "Update_Product";
	}

	//Invocando a página AddUser
	@GetMapping("/addUser")
	public String addUser()
	{
		return "Add_User";
	}

	//Invocando a página UpdateUser
	@GetMapping("/updateUser/{userId}")
	public String updateUserPage(@PathVariable("userId") int id,Model model)
	{
		User user = this.services.getUser(id);
		model.addAttribute("user", user);
		return "Update_User";
	}
	//Fazendo pedido
	@PostMapping("/product/order")
	public String orderHandler(@ModelAttribute() Orders order,Model model)
	{
		double  totalAmount = countTotal(order.getoPrice(),order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		Date d=new Date();
		order.setOrderDate(d);
		this.orderServices.saveOrder(order);
		model.addAttribute("amount",totalAmount);
		return "Order_success";
	}

	@GetMapping("/product/back")
	public String back(Model model)
	{
		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		return "BuyProduct";
	}

	public static double countTotal(double price,int quantity)
	{
		double res=price*quantity;
		return res;
	}

}

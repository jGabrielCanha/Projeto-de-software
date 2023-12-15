package com.business.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity // Indica que essa classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
public class Product {
	@Id // Indica a chave primária da entidade
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de valor automático para a chave primária
	private int pid; // Identificador único do produto

	private String pname; // Nome do produto
	private double pprice; // Preço do produto
	private String pdescription; // Descrição do produto

	// Métodos getters e setters para cada atributo
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	public String getPdescription() {
		return pdescription;
	}
	public void setPdescription(String pdescription) {
		this.pdescription = pdescription;
	}
	@Override
	public String toString() {
		// Retorna uma representação em string do objeto Product
		return "Product [pid=" + pid + ", pname=" + pname + ", pprice=" + pprice + ", pdescription=" + pdescription
				+ "]";
	}



}

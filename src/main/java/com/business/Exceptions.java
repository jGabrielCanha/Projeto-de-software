package com.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Exceptions
{

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	// Define a resposta HTTP para o erro como um erro interno do servidor

	@ExceptionHandler(value=Exception.class)
	// Indica que este método irá lidar com exceções do tipo 'Exception'
	public String handler()
	{
		System.out.println("Exception Handled....!!!!");
		// Imprime uma mensagem quando uma exceção é tratada
		return "exception";
		// Retorna uma string 'exception' (poderia ser o nome de uma página de erro)
	}
}

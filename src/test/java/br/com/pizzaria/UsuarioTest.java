package br.com.pizzaria;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.pizzaria.model.UsuarioModel;
import br.com.pizzaria.repository.UsuarioRepository;
import br.com.pizzaria.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTest {

	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	/*Repository*/
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new UsuarioModel(0L, "João da Silva", "joao@email.com.br", "13465278", "Rua Eunice Leoni", "Bom Retiro", "12345", "Paulínia", "13142-146", "19991234567"));
		
		usuarioRepository.save(new UsuarioModel(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "Rua Eunice Leoni", "Bom Retiro", "12345", "Paulínia", "13142-146", "19991234567"));
		
		usuarioRepository.save(new UsuarioModel(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "Rua Eunice Leoni", "Bom Retiro", "12345", "Paulínia", "13142-146", "19991234567"));

		
	}
	
	@Test
	@Order(1)
	@DisplayName("Retornar 1 usuário")
	public void deveRetornarUmUsuario(){
		Optional<UsuarioModel> usuario = usuarioRepository.findByEmail("joao@email.com.br");
		assertTrue(usuario.get().getEmail().equals("joao@email.com.br"));
		
	}
	
	@Test
	@Order(2)
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		List<UsuarioModel> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
	}
	
	/*Controller*/
	
	@Test
	@Order(3)
	@DisplayName("Cadastrar um Usuário")
	public void deveCriarUmUsuario() {
		HttpEntity<UsuarioModel> requisicao = new HttpEntity<UsuarioModel>(new UsuarioModel(0L, "Paulo Antunes", "paulo_antuness3@email.com.br", "123456789", "Rua Eunice", "Bom Retiro", "1234", "Paulínia", "13142-146", "19991234567"));
	
		ResponseEntity<UsuarioModel> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, UsuarioModel.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getEmail(), resposta.getBody().getEmail());
		assertEquals(requisicao.getBody().getEndereco(), resposta.getBody().getEndereco());
		assertEquals(requisicao.getBody().getBairro(), resposta.getBody().getBairro());
		assertEquals(requisicao.getBody().getNumero(), resposta.getBody().getNumero());
		assertEquals(requisicao.getBody().getCidade(), resposta.getBody().getCidade());
		assertEquals(requisicao.getBody().getCep(), resposta.getBody().getCep());
		assertEquals(requisicao.getBody().getTelefone(), resposta.getBody().getTelefone());
	}
	
	@Test
	@Order(4)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new UsuarioModel(0L, 
				"Maria da Silva", "maria_silva@email.com.br", "13465278", "Rua Eunice Leoni", "Bom Retiro", "12345", "Paulínia", "13142-146", "19991234567"));

		HttpEntity<UsuarioModel> requisicao = new HttpEntity<UsuarioModel>(new UsuarioModel(0L, 
				"Maria da Silva", "maria_silva@email.com.br", "13465278", "Rua Eunice Leoni", "Bom Retiro", "12345", "Paulínia", "13142-146", "19991234567"));

		ResponseEntity<UsuarioModel> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, UsuarioModel.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(5)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<UsuarioModel> usuarioCreate = usuarioService.cadastrarUsuario(new UsuarioModel(0L, 
			"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123", "Rua meride", "Jardim Retiront", "123456", "Algustápole", "10142-146", "19990034567"));

		UsuarioModel usuarioUpdate = new UsuarioModel(usuarioCreate.get().getId(), 
			"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "Rua meride", "Jardim Retiront", "123456", "Algustápole", "10142-146", "19990034567");
		
		HttpEntity<UsuarioModel> requisicao = new HttpEntity<UsuarioModel>(usuarioUpdate);

		ResponseEntity<UsuarioModel> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, UsuarioModel.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getEmail(), resposta.getBody().getEmail());
	}
	
	@Test
	@Order(6)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new UsuarioModel(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "Rua nosskkkk", "Jardim Ret", "1234456", "Algus", "11142-146", "19996634567"));
		
		usuarioService.cadastrarUsuario(new UsuarioModel(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "Rua nossad", "Jardim Retiront", "123456", "Algustápole", "10142-146", "19990034567"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
}

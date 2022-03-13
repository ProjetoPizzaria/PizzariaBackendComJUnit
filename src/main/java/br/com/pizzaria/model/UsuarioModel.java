package br.com.pizzaria.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tb_usuarios")
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Valor de 'nome' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 2, max = 255, message = "Valor de 'nome' deve possuir entre 2-255 caracteres")
	private String nome;

	@ApiModelProperty(example = "email@email.com.br")
	@NotNull(message = "Valor de 'email' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 5, max = 255, message = "Valor de 'email' deve possuir entre 5-255 caracteres")
	@Email(message = "O atributo Usuário deve ser um email válido!")
	private String email;

	@NotBlank(message = "Valor de 'senha' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 5, message = "Valor de 'senha' deve possuir no mínimo 5 caracteres")
	private String senha;

	private String tipo;

	@NotBlank(message = "Valor de 'endereco' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 4, max = 500, message = "Valor de 'endereco' deve possuir entre 4-500 caracteres")
	private String endereco;

	@NotBlank(message = "Valor de 'bairro' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 2, max = 50, message = "Valor de 'bairro' deve possuir entre 2-50 caracteres")
	private String bairro;

	@NotBlank(message = "Valor de 'numero' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 1, max = 10, message = "Valor de 'numero' deve possuir entre 1-10 caracteres")
	private String numero;

	@NotBlank(message = "Valor de 'cidade' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 2, max = 50, message = "Valor de 'cidade' deve possuir entre 2-50 caracteres")
	private String cidade;

	@NotBlank(message = "Valor de 'cep' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 8, max = 9, message = "Valor de 'cep' deve possuir entre 8-9 caracteres")
	private String cep;

	@NotBlank(message = "Valor de 'telefone' nÃ£o pode ser nulo e/ou em branco")
	@Size(min = 11, max = 11, message = "Valor de 'telefone' deve possuir entre 11-11 caracteres")
	private String telefone;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<ProdutoModel> produtos;

	public UsuarioModel(long id, String nome, String email, String senha, String endereco, String bairro, String numero,
			String cidade, String cep, String telefone) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.endereco = endereco;
		this.bairro = bairro;
		this.numero = numero;
		this.cidade = cidade;
		this.cep = cep;
		this.telefone = telefone;
	}

	public UsuarioModel() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<ProdutoModel> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoModel> produtos) {
		this.produtos = produtos;
	}

}

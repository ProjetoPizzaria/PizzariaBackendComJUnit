package br.com.pizzaria.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produtos")
public class ProdutoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "O nome Ã© obrigatÃ³rio.")
	@Size(min = 1, max = 255, message = "O nome deve ter no mÃ­nimo 1 e no mÃ¡ximo 255 caracteres.")
	private String nome;

	@NotBlank(message = "A imagem é obrigatória.")
	private String imagem;

	@NotBlank(message = "A descriÃ§Ã£o Ã© obrigatÃ³ria.")
	@Size(min = 1, max = 510, message = "A descriÃ§Ã£o deve ter no mÃ­nimo 1 e no mÃ¡ximo 510 caracteres.")
	private String descricao;

	private String tamanho;

	@NotNull
	private int quantidade;

	private String comentario;

	private double preco;

	private double frete;

	@ManyToOne
	@JsonIgnoreProperties("categoria")
	@JoinColumn(name = "fk_id_categoria")
	private CategoriaModel categoria;

	@ManyToOne
	@JsonIgnoreProperties("usuario")
	@JoinColumn(name = "fk_id_usuario")
	private UsuarioModel usuario;

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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getFrete() {
		return frete;
	}

	public void setFrete(double frete) {
		this.frete = frete;
	}

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

}

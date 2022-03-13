package br.com.pizzaria.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pizzaria.model.UsuarioLoginModel;
import br.com.pizzaria.model.UsuarioModel;
import br.com.pizzaria.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/*
	 * public UsuarioModel cadastrarUsuario(UsuarioModel usuarioModel) {
	 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 * 
	 * String senhaEncoder = encoder.encode(usuarioModel.getSenha());
	 * usuarioModel.setSenha(senhaEncoder);
	 * 
	 * return usuarioRepository.save(usuarioModel); }
	 * 
	 * public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuarioModel) {
	 * 
	 * if (usuarioRepository.findById(usuarioModel.getId()).isPresent()) {
	 * 
	 * Optional<UsuarioModel> buscaUsuario =
	 * usuarioRepository.findByEmail(usuarioModel.getEmail());
	 * 
	 * if (buscaUsuario.isPresent()) { if (buscaUsuario.get().getId() !=
	 * usuarioModel.getId()) return Optional.empty(); }
	 * 
	 * usuarioModel.setSenha(criptografarSenha(usuarioModel.getSenha()));
	 * 
	 * return Optional.of(usuarioRepository.save(usuarioModel)); }
	 * 
	 * return Optional.empty(); }
	 * 
	 * public Optional<UsuarioLoginModel> logarUsuario(Optional<UsuarioLoginModel>
	 * user) { BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 * Optional<UsuarioModel> usuario =
	 * usuarioRepository.findByEmail(user.get().getEmail());
	 * 
	 * if (usuario.isPresent()) { if (encoder.matches(user.get().getSenha(),
	 * usuario.get().getSenha())) { String auth = user.get().getNome() + ":" +
	 * user.get().getSenha(); byte[] encodedAuth =
	 * Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII"))); String
	 * authHeader = "Basic " + new String(encodedAuth);
	 * 
	 * user.get().setToken(authHeader); user.get().setId(usuario.get().getId());
	 * user.get().setNome(usuario.get().getNome());
	 * user.get().setEmail(usuario.get().getEmail());
	 * user.get().setTipo(usuario.get().getTipo());
	 * user.get().setEndereco(usuario.get().getEndereco());
	 * user.get().setNumero(usuario.get().getNumero());
	 * user.get().setBairro(usuario.get().getBairro());
	 * user.get().setCidade(usuario.get().getCidade());
	 * user.get().setCep(usuario.get().getCep());
	 * user.get().setTelefone(usuario.get().getTelefone());
	 * 
	 * return user; } } return null; }
	 * 
	 * private String criptografarSenha(String senha) {
	 * 
	 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 * 
	 * return encoder.encode(senha);
	 * 
	 * }
	 */

	public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuario) {

		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));

	}

	public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuario) {

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<UsuarioModel> buscaUsuario = usuarioRepository.findByEmail(usuario.getEmail());

			if (buscaUsuario.isPresent()) {
				if (buscaUsuario.get().getId() != usuario.getId())
					return Optional.empty();
			}

			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.of(usuarioRepository.save(usuario));
		}

		return Optional.empty();
	}

	public Optional<UsuarioLoginModel> logarUsuario(Optional<UsuarioLoginModel> usuarioLogin) {

		Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(usuarioLogin.get().getEmail());

		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				String token = gerarBasicToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getSenha());

				usuarioLogin.get().setToken(token);
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setEmail(usuario.get().getEmail());
				usuarioLogin.get().setTipo(usuario.get().getTipo());
				usuarioLogin.get().setEndereco(usuario.get().getEndereco());
				usuarioLogin.get().setNumero(usuario.get().getNumero());
				usuarioLogin.get().setBairro(usuario.get().getBairro());
				usuarioLogin.get().setCidade(usuario.get().getCidade());
				usuarioLogin.get().setCep(usuario.get().getCep());
				usuarioLogin.get().setTelefone(usuario.get().getTelefone());

				return usuarioLogin;

			}
		}

		return Optional.empty();

	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String gerarBasicToken(String email, String password) {

		String tokenBase = email + ":" + password;
		byte[] tokenBase64 = Base64.encodeBase64(tokenBase.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

}

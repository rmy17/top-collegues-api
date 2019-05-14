package dev.topcolleguesapi.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dev.topcolleguesapi.entities.CollegueConnecte;
import dev.topcolleguesapi.entities.CollegueInscription;
import dev.topcolleguesapi.entities.CollegueParticipants;
import dev.topcolleguesapi.services.CollegueService;

@RestController
public class AuthentificationCtrl {

	@Autowired
	private CollegueService collegueService;

	@Value("${jwt.expires_in}")
	private Integer EXPIRES_IN;

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Value("${jwt.secret}")
	private String SECRET;

	@PostMapping(value = "/inscription")
	public ResponseEntity authenticate(@RequestBody CollegueInscription collegueInscription,
			HttpServletResponse response) throws URISyntaxException {
		// encapsulation des informations de connexion
		RestTemplate rt = new RestTemplate();
		ResponseEntity<CollegueConnecte> result = rt.postForEntity("https://remvia-collegues-api.herokuapp.com/auth",
				collegueInscription, CollegueConnecte.class);
		String jetonJWT = result.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
		Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
		// DEFINIE LE COOKIE ET PERMET DE LE TRANSMETTRE
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(EXPIRES_IN * 1000);
		authCookie.setPath("/");
		response.addCookie(authCookie);

		RequestEntity<?> requestEntity = RequestEntity.get(new URI("https://remvia-collegues-api.herokuapp.com/me"))
				.header("Cookie", result.getHeaders().getFirst("Set-Cookie")).build();

		ResponseEntity<CollegueConnecte> rep2 = rt.exchange(requestEntity, CollegueConnecte.class);

		if (!collegueInscription.getPhotoUrl().isEmpty()) {
			rep2.getBody().setPhotoUrl(collegueInscription.getPhotoUrl());
		}

		CollegueParticipants collegueParticipants = new CollegueParticipants();
		collegueParticipants.setEmail(rep2.getBody().getEmail());
		collegueParticipants.setNom(rep2.getBody().getNom());
		collegueParticipants.setPrenom(rep2.getBody().getPrenom());
		collegueParticipants.setPhotoUrl(rep2.getBody().getPhotoUrl());
		collegueParticipants.setRoles(rep2.getBody().getRoles());
		collegueService.ajouterCollegueParticipant(collegueParticipants);
		return ResponseEntity.status(HttpStatus.OK).body(rep2.getBody());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity mauvaiseInfosConnexion(HttpClientErrorException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}

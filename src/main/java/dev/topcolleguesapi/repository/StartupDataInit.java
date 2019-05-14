package dev.topcolleguesapi.repository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.topcolleguesapi.entities.CollegueParticipants;
import dev.topcolleguesapi.services.CollegueService;

@Component
public class StartupDataInit {

	@Autowired
	CollegueService collegueService;

	@Autowired
	private CollegueParticipantRepo collegueParticipantRepo;

	// TODO
	@Autowired
	private PasswordEncoder passwordEncoder;

	// La méthode init va être invoquée au démarrage de l'application.
	@EventListener(ContextRefreshedEvent.class)
	public void init() {

		CollegueParticipants collegue0 = new CollegueParticipants();
		collegue0.setNom("Dupond");
		collegue0.setPrenom("Jean");
		collegue0.setEmail("blabla@email.com");
		collegue0.setPhotoUrl("https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819__340.png");
		collegue0.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
		collegueParticipantRepo.save(collegue0);
		CollegueParticipants collegue1 = new CollegueParticipants();
		collegue1.setNom("Dupond");
		collegue1.setPrenom("Jacque");
		collegue1.setEmail("fefklfbn@email.com");
		collegue1.setPhotoUrl("https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819__340.png");
		collegue1.setRoles(Arrays.asList("ROLE_USER"));
		collegueParticipantRepo.save(collegue1);

	}
}
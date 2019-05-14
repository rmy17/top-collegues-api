package dev.topcolleguesapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.topcolleguesapi.entities.CollegueParticipants;
import dev.topcolleguesapi.exception.CollegueNonTrouveException;
import dev.topcolleguesapi.repository.CollegueParticipantRepo;

@Service
public class CollegueService {

	@Autowired
	private CollegueParticipantRepo collegueParticipantRepo;

	public void ajouterCollegueParticipant(CollegueParticipants collegueParticipants) {
		collegueParticipantRepo.save(collegueParticipants);
	}

	public List<CollegueParticipants> recupColleParticipants() {
		List<CollegueParticipants> listParticipants = collegueParticipantRepo.findAll();
		if (!listParticipants.isEmpty()) {
			return listParticipants;
		} else {
			throw new CollegueNonTrouveException();
		}
	}

	@Transactional
	public void modifierPoints(String email, Boolean bool) {
		Optional<CollegueParticipants> collegueParticipants = collegueParticipantRepo.findById(email);
		if (collegueParticipants.isPresent()) {
			if (bool == true) {
				collegueParticipants.get().setPoints(collegueParticipants.get().getPoints() + 10);
			} else {
				collegueParticipants.get().setPoints(collegueParticipants.get().getPoints() - 5);
			}
		} else {
			throw new CollegueNonTrouveException();
		}
	}

	public CollegueParticipants recupCollegueActif(String email) {
		CollegueParticipants CollegueTrouve = this.collegueParticipantRepo.findByEmail(email);

		return CollegueTrouve;
	}
}

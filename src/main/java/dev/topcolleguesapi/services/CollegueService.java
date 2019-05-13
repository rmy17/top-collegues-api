package dev.topcolleguesapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.topcolleguesapi.entities.CollegueParticipants;
import dev.topcolleguesapi.exception.CollegueNonTrouveException;
import dev.topcolleguesapi.repository.CollegueParticipantRepo;

@Service
public class CollegueService {

	CollegueParticipants collegueParticipants = new CollegueParticipants();

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

	public void mofdifierPoints(Boolean bool) {
		if (bool == true) {
			collegueParticipants.setPoints(collegueParticipants.getPoints() + 10);
		} else {
			collegueParticipants.setPoints(collegueParticipants.getPoints() - 5);
		}
	}
}

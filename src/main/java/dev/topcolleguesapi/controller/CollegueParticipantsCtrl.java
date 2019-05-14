package dev.topcolleguesapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.topcolleguesapi.entities.CollegueParticipants;
import dev.topcolleguesapi.entities.Vote;
import dev.topcolleguesapi.services.CollegueService;

@RestController
public class CollegueParticipantsCtrl {

	@Autowired
	private CollegueService collegueService;

	@GetMapping(value = "/votes")
	public List<CollegueParticipants> recupParticipants() {
		return collegueService.recupColleParticipants();
	}

	@PatchMapping(value = "/vote")
	public void vote(@RequestBody Vote vote) {
		collegueService.modifierPoints(vote.getEmail(), vote.isBool());
	}

	@GetMapping("/me")
	public CollegueParticipants recupCollegueConnecte() {
		CollegueParticipants colConnect = new CollegueParticipants();
		colConnect = collegueService
				.recupCollegueActif(SecurityContextHolder.getContext().getAuthentication().getName());
		return colConnect;
	}

}

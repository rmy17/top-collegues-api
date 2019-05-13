package dev.topcolleguesapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.topcolleguesapi.entities.CollegueParticipants;
import dev.topcolleguesapi.services.CollegueService;

@RestController
public class CollegueParticipantsCtrl {

	@Autowired
	private CollegueService collegueService;

	@GetMapping(value = "/votes")
	public List<CollegueParticipants> recupParticipants() {
		return collegueService.recupColleParticipants();
	}

	@PostMapping(value = "/vote")
	public void votePositif(@RequestBody Boolean bool) {
		collegueService.mofdifierPoints(bool);
	}
}

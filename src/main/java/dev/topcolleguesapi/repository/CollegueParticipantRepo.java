package dev.topcolleguesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.topcolleguesapi.entities.CollegueParticipants;

public interface CollegueParticipantRepo extends JpaRepository<CollegueParticipants, String> {

	CollegueParticipants findByEmail(String email);
}

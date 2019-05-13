package dev.topcolleguesapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.topcolleguesapi.entities.CollegueParticipants;

public interface CollegueParticipantRepo extends JpaRepository<CollegueParticipants, String> {

	Optional<CollegueParticipants> findByEmail(String email);
}

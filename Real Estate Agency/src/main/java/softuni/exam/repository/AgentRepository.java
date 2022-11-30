package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.Offer.AgentName;
import softuni.exam.models.entity.Agent;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByFirstNameOrEmail(String firstName, String email);
    Optional<Agent> findFirstByFirstName(String firstName);
}

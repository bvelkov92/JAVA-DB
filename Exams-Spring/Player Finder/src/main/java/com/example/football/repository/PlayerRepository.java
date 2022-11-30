package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {


    Optional<Player> findByEmail(String email);

    @Query("SELECT p FROM Player p" +
            " WHERE p.birthDate BETWEEN :after AND :before ORDER BY " +
            " p.stats.shooting DESC, p.stats.passing DESC, p.stats.endurance DESC, p.lastName")
    List<Player> findBestPlayers(LocalDate after, LocalDate before);



}

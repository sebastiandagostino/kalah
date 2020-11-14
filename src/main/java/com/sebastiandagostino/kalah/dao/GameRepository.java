package com.sebastiandagostino.kalah.dao;

import com.sebastiandagostino.kalah.dto.GameDTO;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameDTO, Long> {
}


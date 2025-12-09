package ru.miaat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.miaat.entity.RawData;

@Repository
public interface RawDataDAO extends JpaRepository<RawData, Long> {
}

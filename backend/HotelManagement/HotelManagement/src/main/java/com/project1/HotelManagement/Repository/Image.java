package com.project1.HotelManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "image")
public interface Image extends JpaRepository<Image, Integer> {
}

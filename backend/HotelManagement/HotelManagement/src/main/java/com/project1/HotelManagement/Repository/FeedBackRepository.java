package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.FeedBack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "feedback")
public interface FeedBackRepository extends CrudRepository<FeedBack, Integer> {
}

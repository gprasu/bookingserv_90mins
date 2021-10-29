package com.paypal.bfs.test.bookingserv.impl.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.bookingserv.jpa.entity.BookingEntity;

@Repository
public interface BookingRepo extends JpaRepository<BookingEntity, Long>{
	
    public List<BookingEntity> findAllByOrderByIdAsc();

}

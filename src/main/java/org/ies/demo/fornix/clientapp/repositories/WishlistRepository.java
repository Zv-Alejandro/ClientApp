package org.ies.demo.fornix.clientapp.repositories;

import org.ies.demo.fornix.clientapp.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByClientId(Integer clientId);
    boolean existsByClientIdAndGameId(Integer clientId, Integer gameId);
}
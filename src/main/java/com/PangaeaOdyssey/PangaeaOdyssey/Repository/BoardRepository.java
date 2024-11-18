package com.PangaeaOdyssey.PangaeaOdyssey.Repository;

import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    List<Board> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword%")
    List<Board> searchByTitle(@Param("keyword") String keyword);

    @Query("SELECT b FROM Board b WHERE b.content LIKE %:keyword%")
    List<Board> searchByContent(@Param("keyword") String keyword);
}
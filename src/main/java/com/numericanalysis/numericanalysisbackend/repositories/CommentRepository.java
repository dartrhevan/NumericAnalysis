package com.numericanalysis.numericanalysisbackend.repositories;

import com.numericanalysis.numericanalysisbackend.model.Comment;
import com.numericanalysis.numericanalysisbackend.model.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT C FROM comment C WHERE C.origin = :origin")
    Collection<Comment> findByOrigin(@Param("origin") Origin origin);
}